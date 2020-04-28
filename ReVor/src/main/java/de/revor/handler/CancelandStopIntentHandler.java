package de.revor.handler;

import java.util.Optional;
import static com.amazon.ask.request.Predicates.intentName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import de.revor.RezeptVorschlag;
import de.revor.datatype.SpeechText;

public class CancelandStopIntentHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(CancelandStopIntentHandler.class);
    
    private static final String AMAZON_CANCEL_INTENT = "AMAZON.CancelIntent";
    
    private static final String AMAZON_STOP_INTENT = "AMAZON.StopIntent";

    public boolean canHandle(HandlerInput input) {
	return input.matches(intentName(AMAZON_STOP_INTENT).or(intentName(AMAZON_CANCEL_INTENT)));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Es soll beendet werden");
	String speechText = SpeechText.VERABSCHIEDEN.getSpeechText();
	return input.getResponseBuilder().withSpeech(speechText).withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText)
		.build();
    }
}
