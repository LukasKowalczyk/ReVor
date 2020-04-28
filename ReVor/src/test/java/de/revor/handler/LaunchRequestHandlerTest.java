package de.revor.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Context;
import com.amazon.ask.model.PermissionStatus;
import com.amazon.ask.model.Permissions;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Scope;
import com.amazon.ask.model.User;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.amazon.ask.response.ResponseBuilder;

import de.revor.RezeptVorschlag;

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
	Context context = mock(Context.class);
	SystemState systemState = mock(SystemState.class);
	User user = mock(User.class);
	Permissions permissions = mock(Permissions.class);
	RequestEnvelope requestEnvelope = mock(RequestEnvelope.class);
	
	when(handlerInput.getRequestEnvelope()).thenReturn(requestEnvelope);	
	when(user.getPermissions()).thenReturn(permissions);
	when(requestEnvelope.getContext()).thenReturn(context);
	when(context.getSystem()).thenReturn(systemState);
	when(systemState.getUser()).thenReturn(user);
	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());
	
	when(user.getPermissions()).thenReturn(null);
	Optional<Response> response = launchRequestHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Um dir Rezept und Zutaten kommenzusallen") > -1);
	
	when(user.getPermissions()).thenReturn(permissions);
	when(permissions.getScopes()).thenReturn(new HashMap<String, Scope>());
	response = launchRequestHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Um dir Rezept und Zutaten kommenzusallen") > -1);
	
	when(user.getPermissions()).thenReturn(permissions);
	when(permissions.getScopes()).thenReturn(getGefuellteScopeDenieded());
	response = launchRequestHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Um dir Rezept und Zutaten kommenzusallen") > -1);
	
	
	when(user.getPermissions()).thenReturn(permissions);
	when(permissions.getScopes()).thenReturn(getGefuellteScope());
	response = launchRequestHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Willkommen, du kannst mich nun fragen") > -1);

    }

    private Map<String, Scope> getGefuellteScope() {
	HashMap<String, Scope> ausg = new HashMap<>();
	for (String permission : RezeptVorschlag.getBenoetigtePermissions()) {
	    ausg.put(permission, Scope.builder().withStatus(PermissionStatus.GRANTED).build());
	}
	return ausg;
    }

    private Map<String, Scope> getGefuellteScopeDenieded() {
	HashMap<String, Scope> ausg = new HashMap<>();
	for (String permission : RezeptVorschlag.getBenoetigtePermissions()) {
	    ausg.put(permission, Scope.builder().withStatus(PermissionStatus.DENIED).build());
	}
	return ausg;
    }

}
