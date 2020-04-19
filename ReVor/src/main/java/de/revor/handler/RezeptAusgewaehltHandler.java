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
	einkaufslisteService.setListManagementServiceClient(input.getServiceClientFactory().getListManagementService());
	String userEmail = getUserEmail(input);
	String speechText = "";
	boolean shouldEndSession = false;
	List<Rezept> rezepte = mappRezepteAusSessionAttribut();
	Integer index = sessionAttributeService.<Integer>getSessionAttribut(GEFUNDENE_REZEPTE_INDEX);
	Integer anzahlportionen = sessionAttributeService.<Integer>getSessionAttribut(ANZAHL_PORTIONEN);
	Rezept rezept = rezepte.get(index);
	speechText = "Du hast " + rezept.getTitel()
		+ " ausgewählt, ich werde die Zutaten auf deine Einkaufsliste speichern und dir das Rezept als email senden. Aufwiedersehen und bis bald";
	sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE, null);
	sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE_INDEX, null);
	List<Zutat> zutaten = rezept.getEinkaufsliste(anzahlportionen);
	einkaufslisteService.fuegeZurEinkaufslisteHinzu(zutaten);
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(rezept, zutaten, userEmail);
	} catch (Exception e) {
	    logger.error("Die Email konnte nicht gesendet werden!", e);
	    speechText = "leider konnte ich dir keine Email senden. bitte überprüfe deine einstellungen in der alexa-app.";
	}
	shouldEndSession = true;
	return input.getResponseBuilder().withSpeech(speechText).withReprompt(speechText)
		.withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText).withShouldEndSession(shouldEndSession).build();
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
}
