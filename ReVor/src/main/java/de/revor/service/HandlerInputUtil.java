package de.revor.service;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;

public class HandlerInputUtil {

    public static Map<String, Object> getSessionAttributes(HandlerInput handlerInput) {
	return handlerInput.getAttributesManager().getSessionAttributes();
    }

    public static Map<String, Slot> getSlots(HandlerInput handlerInput) {
	IntentRequest intentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
	return intentRequest.getIntent().getSlots();
    }

}
