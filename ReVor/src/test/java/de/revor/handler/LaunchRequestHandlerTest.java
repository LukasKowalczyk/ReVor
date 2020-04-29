package de.revor.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;

class LaunchRequestHandlerTest {

    private LaunchRequestHandler launchRequestHandler = new LaunchRequestHandler();

    @Test
    public void canhandle() throws Exception {
	try {
	    launchRequestHandler.canHandle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	
	when(handlerInput.matches(any())).thenReturn(true);
	assertTrue(launchRequestHandler.canHandle(handlerInput));

	when(handlerInput.matches(any())).thenReturn(false);
	assertFalse(launchRequestHandler.canHandle(handlerInput));
	
    }

    @Test
    public void handle() throws Exception {
	try {
	    launchRequestHandler.handle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	RequestEnvelope requestEnvelope = mock(RequestEnvelope.class);
	
	when(handlerInput.getRequestEnvelope()).thenReturn(requestEnvelope);	
	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());
	
	Optional<Response> response = launchRequestHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Willkommen, du kannst mich nun fragen") > -1);

    }

}
