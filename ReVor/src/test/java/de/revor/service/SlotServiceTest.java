package de.revor.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.amazon.ask.model.slu.entityresolution.Resolutions;
import com.amazon.ask.model.slu.entityresolution.Value;
import com.amazon.ask.model.slu.entityresolution.ValueWrapper;

import de.revor.datatype.SkillSlotNames;

class SlotServiceTest {

    @Test
    void setSlots() throws NoSuchFieldException, SecurityException {
	SlotService slotService = SlotService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(slotService, slotService.getClass().getDeclaredField("handlerUtilService"),
		handlerUtilService);
	boolean nullPointer = true;
	try {
	    slotService.setSlots(null);
	} catch (Exception e) {
	    nullPointer = false;
	}
	assertTrue(nullPointer);

	boolean erfolgLeer = true;
	try {
	    when(handlerUtilService.getSlots(any())).thenReturn(null);
	    slotService.setSlots(handlerInput);
	} catch (Exception e) {
	    erfolgLeer = false;
	}
	assertTrue(erfolgLeer);

	erfolgLeer = true;
	try {
	    when(handlerUtilService.getSlots(any())).thenReturn(new HashMap<>());
	    slotService.setSlots(handlerInput);
	} catch (Exception e) {
	    erfolgLeer = false;
	}
	assertTrue(erfolgLeer);

	boolean erfolgGefuellt = true;
	try {
	    when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltIntegerSolts());
	    slotService.setSlots(handlerInput);
	} catch (Exception e) {
	    erfolgGefuellt = false;
	}
	assertTrue(erfolgGefuellt);

	erfolgGefuellt = true;
	try {
	    when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSolts());
	    slotService.setSlots(handlerInput);
	} catch (Exception e) {
	    erfolgGefuellt = false;
	}
	assertTrue(erfolgGefuellt);
    }

    @Test
    void getMappedName() throws NoSuchFieldException, SecurityException {
	SlotService slotService = SlotService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(slotService, slotService.getClass().getDeclaredField("handlerUtilService"),
		handlerUtilService);
	// Null
	slotService.setSlots(null);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
	// Leer
	when(handlerUtilService.getSlots(any())).thenReturn(null);
	slotService.setSlots(handlerInput);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}

	when(handlerUtilService.getSlots(any())).thenReturn(new HashMap<>());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
	// Gefuellt
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSolts());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
	// Gefuellt Null Resolution
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSoltsNullResolutions());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}

	// Gefuellt Leer Resolution
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSoltsLeerResolutions());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}

	// Gefuellt Leer ResolutionValues
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSoltsLeerResolutionValues());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}

	// Gefuellt Leer ResolutionValues
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSoltsValueNull());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
    }

    @Test
    void getInteger() throws NoSuchFieldException, SecurityException {
	SlotService slotService = SlotService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(slotService, slotService.getClass().getDeclaredField("handlerUtilService"),
		handlerUtilService);

	// Null
	slotService.setSlots(null);
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}
	// Leer
	when(handlerUtilService.getSlots(any())).thenReturn(null);
	slotService.setSlots(handlerInput);
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}

	when(handlerUtilService.getSlots(any())).thenReturn(new HashMap<>());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}
	// Gefuellt
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSolts());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}
	// Gefuellt Null Resolution
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSoltsNullResolutions());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}

	// Gefuellt
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltIntegerSolts());
	slotService.setSlots(handlerInput);
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertEquals(0, slotService.getInteger(skillSlotNames));
	    assertEquals(new Integer(0), slotService.getInteger(skillSlotNames));
	}
    }

    @Test
    void isSlotEmpty() throws NoSuchFieldException, SecurityException {
	SlotService slotService = SlotService.getImplementation();
	HandlerInput handlerInput = mock(HandlerInput.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(slotService, slotService.getClass().getDeclaredField("handlerUtilService"),
		handlerUtilService);

	// Null
	slotService.setSlots(null);
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertTrue(slotService.isSlotEmpty(skillSlotNames));
	}

	// Leer
	when(handlerUtilService.getSlots(any())).thenReturn(null);
	slotService.setSlots(handlerInput);
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertTrue(slotService.isSlotEmpty(skillSlotNames));
	}

	when(handlerUtilService.getSlots(any())).thenReturn(new HashMap<>());
	slotService.setSlots(handlerInput);
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertTrue(slotService.isSlotEmpty(skillSlotNames));
	}

	// Gefuellt
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSolts());
	slotService.setSlots(handlerInput);
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertFalse(slotService.isSlotEmpty(skillSlotNames));
	}

	// Gefuellt Null
	when(handlerUtilService.getSlots(any())).thenReturn(generateGefuelltStringSoltsValueNull());
	slotService.setSlots(handlerInput);
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertTrue(slotService.isSlotEmpty(skillSlotNames));
	}
    }

    private Map<String, Slot> generateGefuelltStringSolts() {
	HashMap<String, Slot> slots = new HashMap<String, Slot>();
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    ValueWrapper valuesItem = ValueWrapper.builder().withValue(Value.builder().withName("real test").build())
		    .build();
	    Resolution resolution = Resolution.builder().addValuesItem(valuesItem).build();
	    Resolutions resolutions = Resolutions.builder().addResolutionsPerAuthorityItem(resolution).build();
	    Slot slot = Slot.builder().withName(skillSlotNames.getSlotName()).withValue("test")
		    .withResolutions(resolutions).build();
	    slots.put(skillSlotNames.getSlotName(), slot);
	}
	return slots;
    }

    private Map<String, Slot> generateGefuelltStringSoltsValueNull() {
	HashMap<String, Slot> slots = new HashMap<String, Slot>();
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    ValueWrapper valuesItem = ValueWrapper.builder().withValue(Value.builder().withName(null).build()).build();
	    Resolution resolution = Resolution.builder().addValuesItem(valuesItem).build();
	    Resolutions resolutions = Resolutions.builder().addResolutionsPerAuthorityItem(resolution).build();
	    Slot slot = Slot.builder().withName(skillSlotNames.getSlotName()).withValue(null)
		    .withResolutions(resolutions).build();
	    slots.put(skillSlotNames.getSlotName(), slot);
	}
	return slots;
    }

    private Map<String, Slot> generateGefuelltIntegerSolts() {
	HashMap<String, Slot> slots = new HashMap<String, Slot>();
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    Slot slot = Slot.builder().withName(skillSlotNames.getSlotName()).withValue("0").build();
	    slots.put(skillSlotNames.getSlotName(), slot);
	}
	return slots;
    }

    private Map<String, Slot> generateGefuelltStringSoltsNullResolutions() {
	HashMap<String, Slot> slots = new HashMap<String, Slot>();
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    Slot slot = Slot.builder().withName(skillSlotNames.getSlotName()).withValue("test").withResolutions(null)
		    .build();
	    slots.put(skillSlotNames.getSlotName(), slot);
	}
	return slots;
    }

    private Map<String, Slot> generateGefuelltStringSoltsLeerResolutions() {
	HashMap<String, Slot> slots = new HashMap<String, Slot>();
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    Slot slot = Slot.builder().withName(skillSlotNames.getSlotName()).withValue("test")
		    .withResolutions(Resolutions.builder().build()).build();
	    slots.put(skillSlotNames.getSlotName(), slot);
	}
	return slots;
    }

    private Map<String, Slot> generateGefuelltStringSoltsLeerResolutionValues() {
	HashMap<String, Slot> slots = new HashMap<String, Slot>();
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    ValueWrapper valuesItem = ValueWrapper.builder().build();
	    Resolution resolution = Resolution.builder().addValuesItem(valuesItem).build();
	    Resolutions resolutions = Resolutions.builder().addResolutionsPerAuthorityItem(resolution).build();
	    Slot slot = Slot.builder().withName(skillSlotNames.getSlotName()).withValue("test")
		    .withResolutions(resolutions).build();
	    slots.put(skillSlotNames.getSlotName(), slot);
	}
	return slots;
    }

    @Test
    void getImplementation() {
	assertNotNull(SlotService.getImplementation());
    }
}
