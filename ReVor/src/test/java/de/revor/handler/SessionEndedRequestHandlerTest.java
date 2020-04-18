package de.revor.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;

class SessionEndedRequestHandlerTest {

    private SessionEndedRequestHandler sessionEndedRequestHandler = new SessionEndedRequestHandler();

    @Test
    public void canhandle() throws Exception {
	try {
	    sessionEndedRequestHandler.canHandle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.matches(any())).thenReturn(true);
	assertTrue(sessionEndedRequestHandler.canHandle(handlerInput));

	when(handlerInput.matches(any())).thenReturn(false);
	assertFalse(sessionEndedRequestHandler.canHandle(handlerInput));
    }

    @Test
    public void handle() throws Exception {
	try {
	    sessionEndedRequestHandler.handle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());
	Optional<Response> handleResponse = sessionEndedRequestHandler.handle(handlerInput);
	assertTrue(handleResponse.isPresent());

    }


}
