package de.revor.service;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;

public class HandlerInputUtil {

    public static Map<String, Object> getSessionAttributes(HandlerInput handlerInput) {
	if(handlerInput==null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	return handlerInput.getAttributesManager().getSessionAttributes();
    }

    public static Map<String, Slot> getSlots(HandlerInput handlerInput) {
	if(handlerInput==null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	IntentRequest intentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
	return intentRequest.getIntent().getSlots();
    }

}
