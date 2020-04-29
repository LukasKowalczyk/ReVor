package de.revor.handler;

import static com.amazon.ask.request.Predicates.intentName;
import static de.revor.datatype.SkillSessionAttributeNames.ANZAHL_PORTIONEN;
import static de.revor.datatype.SkillSessionAttributeNames.GEFUNDENE_REZEPTE;
import static de.revor.datatype.SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX;
import static de.revor.datatype.SkillSlotNames.ANZAHLPORTIONEN;
import static de.revor.datatype.SkillSlotNames.MAHLZEIT;
import static de.revor.datatype.SkillSlotNames.SCHWEREGRAD;
import static de.revor.datatype.SpeechText.KEIN_REZEPT_GEFUNDEN;
import static de.revor.datatype.SpeechText.REZEPT_GEFUNEN;
import static de.revor.datatype.SpeechText.REZEPT_SUCHE_PROGRESSIV;
import static de.revor.datatype.SpeechTextPlaceHolder.REZEPTTITEL;
import static de.revor.datatype.SpeechTextPlaceHolder.SUCHPARAMETER;

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
import de.revor.service.HandlerUtilService;
import de.revor.service.RezeptSucheService;
import de.revor.service.SessionAttributeService;
import de.revor.service.SlotService;

public class RezeptSucheHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RezeptSucheHandler.class);

    private static final String INSTANT_NAME = "REZEPT_SUCHE";

    private RezeptSucheService rezeptSuche = RezeptSucheService.getImplementation();

    private SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();

    private HandlerUtilService handlerInputUtilService = HandlerUtilService.getImpementation();

    private SlotService slotService = SlotService.getImplementation();

    private String parameter = "";

    public boolean canHandle(HandlerInput input) {
	return input.matches(intentName(INSTANT_NAME));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Rezeptsuche starten");
	String speechText = "";
	parameter = "";
	handlerInputUtilService.sendProgressiveResponse(input, REZEPT_SUCHE_PROGRESSIV.getSpeechText());

	sessionAttributeService.setSessionAttributes(input);
	slotService.setSlots(input);

	Mahlzeit mahlzeit = ermittleMahlzeit();

	Schweregrad schweregrad = ermittleSchweregrad();

	int anzahlportionen = errmittleAnzahlPortionen();

	speechText = KEIN_REZEPT_GEFUNDEN.getSpeechText(SUCHPARAMETER.toString(), parameter);
	if (sessionAttributeService.isSessionAttributEmpty(GEFUNDENE_REZEPTE)) {
	    List<Rezept> rezepte = rezeptSuche.findeRezepte(mahlzeit, schweregrad);
	    if (rezepte.size() > 0) {
		speechText = REZEPT_GEFUNEN.getSpeechText(REZEPTTITEL.toString(), rezepte.get(0).getTitel());
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
	    parameter += " die anzahl portionen " + anzahlportionen + ".";
	} else {
	    // Erfrage Anzahl Portionen
	    parameter += " keine anzahl portionen.";
	}
	return anzahlportionen;
    }

    private Schweregrad ermittleSchweregrad() {
	Schweregrad schweregrad = Schweregrad.EGAL;
	if (!slotService.isSlotEmpty(SCHWEREGRAD)) {
	    schweregrad = Schweregrad.getSchweregradOfWert(slotService.getMappedName(SCHWEREGRAD));
	    parameter += " der Schweregrad " + schweregrad.getWert() + ".";
	} else {
	    // Erfrage Schweregrad
	    parameter += " kein schweregrad.";
	}
	return schweregrad;
    }


    private Mahlzeit ermittleMahlzeit() {
	Mahlzeit mahlzeit = Mahlzeit.JETZT;
	if (!slotService.isSlotEmpty(MAHLZEIT)) {
	    mahlzeit = Mahlzeit.getMahlzeitOfWert(slotService.getMappedName(MAHLZEIT));
	    parameter += " die tageszeit " + mahlzeit.getWert() + ".";
	} else {
	    // Erfrage Tageszeit
	    parameter += " keine tageszeit.";
	}
	return mahlzeit;
    }

}
