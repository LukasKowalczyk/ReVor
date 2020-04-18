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

class HelpIntentHandlerTest {

    private HelpIntentHandler helpIntentHandler = new HelpIntentHandler();

    @Test
    public void canhandle() throws Exception {
	try {
	    helpIntentHandler.canHandle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.matches(any())).thenReturn(true);
	assertTrue(helpIntentHandler.canHandle(handlerInput));

	when(handlerInput.matches(any())).thenReturn(false);
	assertFalse(helpIntentHandler.canHandle(handlerInput));
    }

    @Test
    public void handle() throws Exception {
	try {
	    helpIntentHandler.handle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());
	Optional<Response> handleResponse = helpIntentHandler.handle(handlerInput);
	assertTrue(handleResponse.isPresent());

    }


}
