package de.revor.service;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import de.revor.datatype.SkillSessionAttributeNames;

public class SessionAttributeService {
    
    private HandlerUtilService handlerUtilService = HandlerUtilService.getImpementation();
    
    private static SessionAttributeService sessionAttributeService;

    private Map<String, Object> sessionAttributes;

    private SessionAttributeService() {
	sessionAttributes = null;
    }

    public static SessionAttributeService getImplementation() {
	return sessionAttributeService == null ? new SessionAttributeService() : sessionAttributeService;
    }

    public void setSessionAttributes(HandlerInput handlerInput) {
	this.sessionAttributes = handlerUtilService.getSessionAttributes(handlerInput);
    }

    @SuppressWarnings("unchecked")
    public <T> T getSessionAttribut(SkillSessionAttributeNames reVorSessionAttributeNames) {
	if (sessionAttributes != null) {
	    return (T) sessionAttributes.get(reVorSessionAttributeNames.name());
	}
	return null;
    }

    public boolean isSessionAttributEmpty(SkillSessionAttributeNames reVorSessionAttributeNames) {
	if (sessionAttributes != null) {
	    return sessionAttributes.get(reVorSessionAttributeNames.name()) == null;
	}
	return true;
    }

    public void putSessionAttribut(SkillSessionAttributeNames reVorSessionAttributeNames, Object value) {
	if (sessionAttributes != null) {
	    sessionAttributes.put(reVorSessionAttributeNames.name(), value);
	}
    }
}
