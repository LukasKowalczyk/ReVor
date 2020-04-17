package de.revor.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.revor.datatype.SkillSessionAttributeNames;

class SessionAttributeServiceTest {

    @Test
    void setSessionAttributes() {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);
	    sessionAttributeService.setSessionAttributes(new HashMap<String, Object>());
	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesString());
	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesIntegers());
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void isSessionAttributEmpty() {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertTrue(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }

	    sessionAttributeService.setSessionAttributes(new HashMap<String, Object>());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertTrue(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }

	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesString());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertFalse(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }
	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesIntegers());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertFalse(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void putSessionAttribut() {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, "value");
	    }

	    sessionAttributeService.setSessionAttributes(new HashMap<String, Object>());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, "value");
	    }

	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesString());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, "value");
	    }
	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesIntegers());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, new Integer(0));
	    }
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void getSessionAttribut() {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertNull(sessionAttributeService.getSessionAttribut(skillSessionAttributeNames));
	    }

	    sessionAttributeService.setSessionAttributes(new HashMap<String, Object>());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertNull(sessionAttributeService.getSessionAttribut(skillSessionAttributeNames));
	    }

	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesString());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertEquals("value", sessionAttributeService.getSessionAttribut(skillSessionAttributeNames));
	    }
	    sessionAttributeService.setSessionAttributes(gefuellteSessionAttributesIntegers());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertEquals(new Integer(0), sessionAttributeService.getSessionAttribut(skillSessionAttributeNames));
	    }
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    private Map<String, Object> gefuellteSessionAttributesString() {
	HashMap<String, Object> sessionAttributes = new HashMap<>();
	for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
	    sessionAttributes.put(skillSessionAttributeNames.name(), "value");
	}
	return sessionAttributes;
    }

    private Map<String, Object> gefuellteSessionAttributesIntegers() {
	HashMap<String, Object> sessionAttributes = new HashMap<>();
	for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
	    sessionAttributes.put(skillSessionAttributeNames.name(), new Integer(0));
	}
	return sessionAttributes;
    }
}
