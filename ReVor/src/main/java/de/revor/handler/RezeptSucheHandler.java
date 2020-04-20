package de.revor.handler;

import static com.amazon.ask.request.Predicates.intentName;
import static de.revor.datatype.SkillSessionAttributeNames.ANZAHL_PORTIONEN;
import static de.revor.datatype.SkillSessionAttributeNames.GEFUNDENE_REZEPTE;
import static de.revor.datatype.SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX;
import static de.revor.datatype.SkillSlotNames.ANZAHLPORTIONEN;
import static de.revor.datatype.SkillSlotNames.MAHLZEIT;
import static de.revor.datatype.SkillSlotNames.SCHWEREGRAD;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.services.directive.DirectiveServiceClient;
import com.amazon.ask.model.services.directive.Header;
import com.amazon.ask.model.services.directive.SendDirectiveRequest;
import com.amazon.ask.model.services.directive.SpeakDirective;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import de.revor.RezeptVorschlag;
import de.revor.datatype.Mahlzeit;
import de.revor.datatype.Rezept;
import de.revor.datatype.Schweregrad;
import de.revor.service.RezeptSucheService;
import de.revor.service.SessionAttributeService;
import de.revor.service.SlotService;

public class RezeptSucheHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RezeptSucheHandler.class);

    private static final String INSTANT_NAME = "REZEPT_SUCHE";

    private RezeptSucheService rezeptSuche = RezeptSucheService.getImplementation();

    private SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();

    private SlotService slotService = SlotService.getImplementation();
    private String speechText = "";

    public boolean canHandle(HandlerInput input) {
	return input.matches(intentName(INSTANT_NAME));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Rezeptsuche starten");
	speechText = "";
	sendProgressiveResponse(input, "Ich suche nach Rezepten");
	sessionAttributeService.setSessionAttributes(input.getAttributesManager().getSessionAttributes());
	IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
	slotService.setSlots(intentRequest.getIntent().getSlots());
	try {
	    rezeptSuche.setAmazonDynamoDB(AmazonDynamoDBClientBuilder.standard().build());
	} catch (SdkClientException e) {
	    logger.error("DB-Fehler", e);
	    rezeptSuche.setAmazonDynamoDB(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_2).build());
	}

	Mahlzeit mahlzeit = ermittleMahlzeit();

	Schweregrad schweregrad = ermittleSchweregrad();
	
	int anzahlportionen = errmittleAnzahlPortionen();
	
	speechText = "Ich habe leider nichts gefunden mit deinen Angaben: " + speechText;
	if (sessionAttributeService.isSessionAttributEmpty(GEFUNDENE_REZEPTE)) {
	    List<Rezept> rezepte = rezeptSuche.findeRezepte(mahlzeit, schweregrad);
	    if (rezepte.size() > 0) {
		speechText = "Ich habe " + rezepte.get(0).getTitel() + " gefunden, willst du das vielleicht kochen?";
	    }
	    sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE, rezepte);
	    sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE_INDEX, 0);
	    sessionAttributeService.putSessionAttribut(ANZAHL_PORTIONEN, anzahlportionen);
	}
	return input.getResponseBuilder().withSpeech(speechText).withReprompt(speechText)
		.withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText).withShouldEndSession(false).build();
    }

    private int errmittleAnzahlPortionen() {
	int anzahlportionen = 1;
	if (!slotService.isSlotEmpty(ANZAHLPORTIONEN)) {
	    anzahlportionen = slotService.getInteger(ANZAHLPORTIONEN);
	    speechText += " die anzahl portionen " + anzahlportionen + ".";
	} else {
	    // Erfrage Anzahl Portionen
	    speechText += " keine anzahl portionen.";
	}
	return anzahlportionen;
    }

    private Schweregrad ermittleSchweregrad() {
	Schweregrad schweregrad = Schweregrad.EGAL;
	if (!slotService.isSlotEmpty(SCHWEREGRAD)) {
	    schweregrad = Schweregrad.getSchweregradOfWert(slotService.getMappedName(SCHWEREGRAD));
	    speechText += " der Schweregrad " + schweregrad.getWert() + ".";
	} else {
	    // Erfrage Schweregrad
	    speechText += " kein schweregrad.";
	}
	return schweregrad;
    }

    private Mahlzeit ermittleMahlzeit() {
	Mahlzeit mahlzeit = Mahlzeit.JETZT;
	if (!slotService.isSlotEmpty(MAHLZEIT)) {
	    mahlzeit = Mahlzeit.getMahlzeitOfWert(slotService.getMappedName(MAHLZEIT));
	    speechText += " die tageszeit " + mahlzeit.getWert() + ".";
	} else {
	    // Erfrage Tageszeit
	    speechText += " keine tageszeit.";
	}
	return mahlzeit;
    }

    private void sendProgressiveResponse(HandlerInput input, String speechText) {
	String requestId = input.getRequestEnvelope().getRequest().getRequestId();
	DirectiveServiceClient directiveServiceClient = input.getServiceClientFactory().getDirectiveService();
	SendDirectiveRequest sendDirectiveRequest = SendDirectiveRequest.builder()
		.withDirective(SpeakDirective.builder().withSpeech(speechText).build())
		.withHeader(Header.builder().withRequestId(requestId).build()).build();
	directiveServiceClient.enqueue(sendDirectiveRequest);
    }

}
