package de.revor.service;

import java.util.Map;

import de.revor.datatype.SkillSessionAttributeNames;

public class SessionAttributeService {

    private static SessionAttributeService sessionAttributeService;

    private Map<String, Object> sessionAttributes;

    private SessionAttributeService() {
	sessionAttributes = null;
    }

    public static SessionAttributeService getImplementation() {
	return sessionAttributeService == null ? new SessionAttributeService() : sessionAttributeService;
    }

    public void setSessionAttributes(Map<String, Object> sessionAttributes) {
	this.sessionAttributes = sessionAttributes;
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
