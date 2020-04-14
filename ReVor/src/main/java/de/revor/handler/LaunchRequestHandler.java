package de.revor.handler;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.requestType;
import de.revor.RezeptVorschlag;

public class LaunchRequestHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(LaunchRequestHandler.class);

    public boolean canHandle(HandlerInput input) {
	return input.matches(requestType(LaunchRequest.class));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Willkommen Aufruf");
	String speechText = "Willkommen, du kannst mich nun fragen \"was soll ich kochen?\"";
	return input.getResponseBuilder().withSpeech(speechText).withReprompt(speechText)
		.withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText).build();
    }
}