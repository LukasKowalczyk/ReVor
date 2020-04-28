package de.revor.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import de.revor.datatype.SkillSessionAttributeNames;

class SessionAttributeServiceTest {

    @Test
    void setSessionAttributes() throws NoSuchFieldException, SecurityException {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(sessionAttributeService,
		sessionAttributeService.getClass().getDeclaredField("handlerUtilService"), handlerUtilService);
	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);

	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(null);
	    sessionAttributeService.setSessionAttributes(handlerInput);

	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesString());
	    sessionAttributeService.setSessionAttributes(handlerInput);

	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesIntegers());
	    sessionAttributeService.setSessionAttributes(handlerInput);

	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void isSessionAttributEmpty() throws NoSuchFieldException, SecurityException {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(sessionAttributeService,
		sessionAttributeService.getClass().getDeclaredField("handlerUtilService"), handlerUtilService);

	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertTrue(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }

	    sessionAttributeService.setSessionAttributes(handlerInput);
	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertTrue(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }

	    sessionAttributeService.setSessionAttributes(handlerInput);
	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesString());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertTrue(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }

	    sessionAttributeService.setSessionAttributes(handlerInput);
	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesIntegers());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertFalse(sessionAttributeService.isSessionAttributEmpty(skillSessionAttributeNames));
	    }
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void putSessionAttribut() throws NoSuchFieldException, SecurityException {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(sessionAttributeService,
		sessionAttributeService.getClass().getDeclaredField("handlerUtilService"), handlerUtilService);

	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, "value");
	    }

	    sessionAttributeService.setSessionAttributes(handlerInput);
	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, "value");
	    }

	    sessionAttributeService.setSessionAttributes(handlerInput);
	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesString());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, "value");
	    }

	    sessionAttributeService.setSessionAttributes(handlerInput);
	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesIntegers());
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		sessionAttributeService.putSessionAttribut(skillSessionAttributeNames, new Integer(0));
	    }
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void getSessionAttribut() throws NoSuchFieldException, SecurityException {
	SessionAttributeService sessionAttributeService = SessionAttributeService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(sessionAttributeService,
		sessionAttributeService.getClass().getDeclaredField("handlerUtilService"), handlerUtilService);

	boolean erfolg = true;
	try {
	    sessionAttributeService.setSessionAttributes(null);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertNull(sessionAttributeService.getSessionAttribut(skillSessionAttributeNames));
	    }

	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(null);
	    sessionAttributeService.setSessionAttributes(handlerInput);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertNull(sessionAttributeService.getSessionAttribut(skillSessionAttributeNames));
	    }

	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesString());
	    sessionAttributeService.setSessionAttributes(handlerInput);
	    for (SkillSessionAttributeNames skillSessionAttributeNames : SkillSessionAttributeNames.values()) {
		assertEquals("value", sessionAttributeService.<String>getSessionAttribut(skillSessionAttributeNames));
	    }
	    
	    
	    when(handlerUtilService.getSessionAttributes(any())).thenReturn(gefuellteSessionAttributesIntegers());
	    sessionAttributeService.setSessionAttributes(handlerInput);
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
