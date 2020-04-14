package de.revor.handler;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import static com.amazon.ask.request.Predicates.intentName;
import de.revor.RezeptVorschlag;

public class HelpIntentHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(HelpIntentHandler.class);

    private static final String AMAZON_HELP_INTENT = "AMAZON.HelpIntent";

    public boolean canHandle(HandlerInput input) {
	return input.matches(intentName(AMAZON_HELP_INTENT));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Anwender braucht hilfe");
	String speechText = "Frag einfach: \"was soll ich kochen?\" oder sage: \"stop\"";
	return input.getResponseBuilder().withSpeech(speechText).withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText)
		.withReprompt(speechText).build();
    }
}