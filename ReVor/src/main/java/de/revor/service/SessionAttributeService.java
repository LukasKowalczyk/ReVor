package de.revor.service;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import de.revor.datatype.SkillSessionAttributeNames;

public class SessionAttributeService {
    
    private static SessionAttributeService sessionAttributeService;
    
    private HandlerInput handlerInput;

    private SessionAttributeService() {
	handlerInput = null;
    }

    public static SessionAttributeService getImplementation() {
	return sessionAttributeService == null ? new SessionAttributeService() : sessionAttributeService;
    }

    public void setHandlerInput(HandlerInput handlerInput) {
	this.handlerInput = handlerInput;
    }

    @SuppressWarnings("unchecked")
    public <T> T getSessionAttribut(SkillSessionAttributeNames reVorSessionAttributeNames) {
	if (handlerInput != null) {
	    Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
	    return (T) sessionAttributes.get(reVorSessionAttributeNames.name());
	}
	return null;
    }

    public boolean isSessionAttributEmpty(SkillSessionAttributeNames reVorSessionAttributeNames) {
	if (handlerInput != null) {
	    Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
	    return sessionAttributes.get(reVorSessionAttributeNames.name()) == null;
	}
	return true;
    }

    public void putSessionAttribut(SkillSessionAttributeNames reVorSessionAttributeNames, Object value) {
	if (handlerInput != null) {
	    Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
	    sessionAttributes.put(reVorSessionAttributeNames.name(), value);
	}
    }
}
