package de.revor.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;

import de.revor.service.SessionAttributeService;

class NaechstesRezeptHandlerTest {

    private NaechstesRezeptHandler naechstesRezeptHandler = new NaechstesRezeptHandler();

    @Test
    public void canhandle() throws Exception {
	try {
	    naechstesRezeptHandler.canHandle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.matches(any())).thenReturn(true);
	assertTrue(naechstesRezeptHandler.canHandle(handlerInput));

	when(handlerInput.matches(any())).thenReturn(false);
	assertFalse(naechstesRezeptHandler.canHandle(handlerInput));
    }

    @Test
    public void handle() throws Exception {
	try {
	    naechstesRezeptHandler.handle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());
	SessionAttributeService sessionAttributeService= mock(SessionAttributeService.class);
	Whitebox.setInternalState(naechstesRezeptHandler, "sessionAttributeService", sessionAttributeService);
	Optional<Response> handleResponse = naechstesRezeptHandler.handle(handlerInput);
	assertTrue(handleResponse.isPresent());

    }


}
