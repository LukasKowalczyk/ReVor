package de.revor.handler;
import static com.amazon.ask.request.Predicates.intentName;
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
import de.revor.service.SessionAttributeService;

public class NaechstesRezeptHandler implements RequestHandler {
    
    private static final String AMAZON_NO_INTENT = "AMAZON.NoIntent";
    
    private static final Logger logger = LoggerFactory.getLogger(NaechstesRezeptHandler.class);
    
    private SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();

    @Override
    public boolean canHandle(HandlerInput input) {
	return input.matches(intentName(AMAZON_NO_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Nächstes Rezept vorschlagen");
	sessionAttributeService.setHandlerInput(input);
	String speechText = "";
	boolean shouldEndSession = false;
	List<Rezept> rezepte = mappRezepteAusSessionAttribut();
	Integer index = sessionAttributeService.<Integer>getSessionAttribut(GEFUNDENE_REZEPTE_INDEX);
	index++;
	if (rezepte != null) {
	    if (rezepte.size() <= index) {
		// wir haben nichts mehr session soll beendet werden!
		speechText = "Ich habe leider keine Rezepte mehr, versuch doch mal einen anderen schweregrad";
		sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE, null);
		index = null;
		shouldEndSession = true;
	    } else {
		speechText = "willst du vielleicht " + rezepte.get(index).getTitel() + " kochen?";
	    }
	}
	sessionAttributeService.putSessionAttribut(GEFUNDENE_REZEPTE_INDEX, index);

	return input.getResponseBuilder().withSpeech(speechText).withReprompt(speechText)
		.withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText).withShouldEndSession(shouldEndSession).build();
    }

    private List<Rezept> mappRezepteAusSessionAttribut() {
	ArrayList<Map<String, Object>> sessionAttributesRezepte = sessionAttributeService
		.<ArrayList<Map<String, Object>>>getSessionAttribut(GEFUNDENE_REZEPTE);
	ArrayList<Rezept> ausg = new ArrayList<>();
	sessionAttributesRezepte.forEach((v) -> ausg.add(Rezept.mappeFromMap(v)));
	return ausg;
    }

}
