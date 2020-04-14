package de.revor.handler;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import static com.amazon.ask.request.Predicates.requestType;

public class SessionEndedRequestHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(SessionEndedRequestHandler.class);

    public boolean canHandle(HandlerInput input) {
	return input.matches(requestType(SessionEndedRequest.class));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Session wird beendet");
	return input.getResponseBuilder().build();
    }
}
