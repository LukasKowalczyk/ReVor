package de.revor.handler;

import static com.amazon.ask.request.Predicates.intentName;
import static de.revor.datatype.SkillSessionAttributeNames.ANZAHL_PORTIONEN;
import static de.revor.datatype.SkillSessionAttributeNames.GEFUNDENE_REZEPTE;
import static de.revor.datatype.SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.services.directive.DirectiveServiceClient;
import com.amazon.ask.model.services.directive.Header;
import com.amazon.ask.model.services.directive.SendDirectiveRequest;
import com.amazon.ask.model.services.directive.SpeakDirective;

import de.revor.RezeptVorschlag;
import de.revor.datatype.Rezept;
import de.revor.datatype.Zutat;
import de.revor.service.EMailSendenService;
import de.revor.service.EinkaufslisteService;
import de.revor.service.SessionAttributeService;

public class RezeptAusgewaehltHandler implements RequestHandler {

    private static final String AMAZON_YES_INTENT = "AMAZON.YesIntent";

    private static final Logger logger = LoggerFactory.getLogger(RezeptAusgewaehltHandler.class);

    private EinkaufslisteService einkaufslisteService = EinkaufslisteService.getImplementation();

    private EMailSendenService eMailSendenService = EMailSendenService.getImpementation();

    private SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();

    @Override
    public boolean canHandle(HandlerInput input) {
	return input.matches(intentName(AMAZON_YES_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Es wurde ein Rezept ausgewählt");
	sessionAttributeService.setSessionAttributes(input.getAttributesManager().getSessionAttributes());
	Rezept rezept = getAusgewaehltesRezept();
	
	String waitSpeechText = "ich werde die Zutaten von "
		+ rezept.getTitel() + " auf deine Einkaufsliste speichern und dir das Rezept als email senden.";
	sendProgressiveResponse(input, waitSpeechText);
	
	einkaufslisteService.setListManagementServiceClient(input.getServiceClientFactory().getListManagementService());
	
	String userEmail = getUserEmail(input);
	String speechText = "";
	boolean shouldEndSession = false;
	
	List<Zutat> zutaten = getEinkaufsliste(rezept);
	
	sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE, null);
	sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE_INDEX, null);
	
	einkaufslisteService.fuegeZurEinkaufslisteHinzu(zutaten);
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(rezept, zutaten, userEmail);
	    speechText = "Die email ist versendet und deine einkaufsliste aktuallisiert. Aufwiedersehen und bis bald ";
	} catch (Exception e) {
	    logger.error("Die Email konnte nicht gesendet werden!", e);
	    speechText = "leider konnte ich dir keine Email senden. bitte überprüfe deine einstellungen in der alexa-app.";
	}

	shouldEndSession = true;
	return input.getResponseBuilder().withSpeech(speechText).withReprompt(speechText)
		.withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText).withShouldEndSession(shouldEndSession).build();
    }

    private List<Zutat> getEinkaufsliste(Rezept rezept) {
	Integer anzahlportionen = sessionAttributeService.<Integer>getSessionAttribut(ANZAHL_PORTIONEN);
	List<Zutat> zutaten = rezept.getEinkaufsliste(anzahlportionen);
	return zutaten;
    }

    private Rezept getAusgewaehltesRezept() {
	List<Rezept> rezepte = mappRezepteAusSessionAttribut();
	Integer index = sessionAttributeService.<Integer>getSessionAttribut(GEFUNDENE_REZEPTE_INDEX);
	Rezept rezept = rezepte.get(index);
	return rezept;
    }

    private String getUserEmail(HandlerInput input) {
	return input.getServiceClientFactory().getUpsService().getProfileEmail();
    }

    private List<Rezept> mappRezepteAusSessionAttribut() {
	ArrayList<Map<String, Object>> sessionAttributesRezepte = sessionAttributeService
		.<ArrayList<Map<String, Object>>>getSessionAttribut(GEFUNDENE_REZEPTE);
	ArrayList<Rezept> ausg = new ArrayList<>();
	sessionAttributesRezepte.forEach((v) -> ausg.add(Rezept.mappeFromMap(v)));
	return ausg;
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
