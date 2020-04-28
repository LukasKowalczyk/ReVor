package de.revor.handler;

import static com.amazon.ask.request.Predicates.requestType;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.PermissionStatus;
import com.amazon.ask.model.Permissions;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Scope;

import de.revor.RezeptVorschlag;
import de.revor.datatype.SpeechText;

public class LaunchRequestHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(LaunchRequestHandler.class);

    public boolean canHandle(HandlerInput input) {
	return input.matches(requestType(LaunchRequest.class));
    }

    public Optional<Response> handle(HandlerInput input) {
	logger.debug("Willkommen Aufruf");
	List<String> list = RezeptVorschlag.getBenoetigtePermissions();
	boolean permissonsOk = pruefePermissions(input, list);
	if (!permissonsOk) {
	    return permissionErfragen(input, list);
	}
	String speechText = SpeechText.WILLKOMMEN.getSpeechText();
	return input.getResponseBuilder().withSpeech(speechText).withReprompt(speechText)
		.withSimpleCard(RezeptVorschlag.SKILL_TITEL, speechText).build();

    }

    private boolean pruefePermissions(HandlerInput input, List<String> list) {
	boolean permissonsOk = true;
	Permissions permissions = input.getRequestEnvelope().getContext().getSystem().getUser().getPermissions();
	if (null == permissions) {
	    permissonsOk = false;
	} else {
	    for (String permission : list) {
		Scope scope = permissions.getScopes().get(permission);
		if (scope == null || scope.getStatus() != PermissionStatus.GRANTED) {
		    permissonsOk = false;
		}
	    }
	}
	return permissonsOk;
    }

    private Optional<Response> permissionErfragen(HandlerInput input, List<String> list) {
	String speechText = SpeechText.PERMISSION_FEHLET.getSpeechText();
	return input.getResponseBuilder().withSpeech(speechText).withAskForPermissionsConsentCard(list).build();
    }
}