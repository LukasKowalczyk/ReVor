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
import com.amazon.ask.model.Response;

import de.revor.RezeptVorschlag;
import de.revor.datatype.Mahlzeit;
import de.revor.datatype.Rezept;
import de.revor.datatype.Schweregrad;
import de.revor.service.HandlerInputUtil;
import de.revor.service.RezeptSucheService;
import de.revor.service.SessionAttributeService;
import de.revor.service.SlotService;

public class StartHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(StartHandler.class);

    private static final String INSTANT_NAME = "START";

    private RezeptSucheService rezeptSuche = RezeptSucheService.getImplementation();

    private SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();

    private SlotService slotService = SlotService.getImplementation();

    public boolean canHandle(HandlerInput input) {
	return input.matches(intentName(INSTANT_NAME));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Suche starten");
	sessionAttributeService.setSessionAttributes(HandlerInputUtil.getSessionAttributes(input));
	slotService.setSlots(HandlerInputUtil.getSlots(input));
	String speechText = "";

	Mahlzeit mahlzeit = Mahlzeit.JETZT;
	if (!slotService.isSlotEmpty(MAHLZEIT)) {
	    mahlzeit = Mahlzeit.getMahlzeitOfWert(slotService.getMappedName(MAHLZEIT));
	    speechText += " die tageszeit " + mahlzeit.getWert() + ".";
	} else {
	    // Erfrage Tageszeit
	    speechText += " keine tageszeit.";
	}

	Schweregrad schweregrad = Schweregrad.EGAL;
	if (!slotService.isSlotEmpty(SCHWEREGRAD)) {
	    schweregrad = Schweregrad.getSchweregradOfWert(slotService.getMappedName(SCHWEREGRAD));
	    speechText += " der Schweregrad " + schweregrad.getWert() + ".";
	} else {
	    // Erfrage Schweregrad
	    speechText += " kein schweregrad.";
	}
	int anzahlportionen = 1;
	if (!slotService.isSlotEmpty(ANZAHLPORTIONEN)) {
	    anzahlportionen = slotService.getInteger(ANZAHLPORTIONEN);
	    speechText += " die anzahl portionen " + anzahlportionen + ".";
	} else {
	    // Erfrage Anzahl Portionen
	    speechText += " keine anzahl portionen.";
	}
	if (sessionAttributeService.isSessionAttributEmpty(GEFUNDENE_REZEPTE)) {
	    List<Rezept> rezepte = rezeptSuche.findeRezepte(mahlzeit, schweregrad);
	    if (rezepte.size() > 0) {
		speechText = "Willst du vielleicht " + rezepte.get(0).getTitel() + " kochen?";
	    } else {
		speechText = "Ich habe leider nichts gefunden mit deinen Angaben: " + speechText;
	    }
	    sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE, rezepte);
	    sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE_INDEX, 0);
	    sessionAttributeService.putSessionAttribut(ANZAHL_PORTIONEN, anzahlportionen);
	}
	return input.getResponseBuilder().withSpeech(speechText).withReprompt(speechText)
		.withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText).withShouldEndSession(false).build();
    }

}
