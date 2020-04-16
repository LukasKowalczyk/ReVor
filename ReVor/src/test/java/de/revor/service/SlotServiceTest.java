package de.revor.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.amazon.ask.model.Slot;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.amazon.ask.model.slu.entityresolution.Resolutions;
import com.amazon.ask.model.slu.entityresolution.Value;
import com.amazon.ask.model.slu.entityresolution.ValueWrapper;

import de.revor.datatype.SkillSlotNames;

class SlotServiceTest {

    @Test
    void setSlots() {
	SlotService slotService = SlotService.getImplementation();
	boolean nullPointer = true;
	try {
	    slotService.setSlots(null);
	} catch (Exception e) {
	    nullPointer = false;
	}
	assertTrue(nullPointer);

	boolean erfolgLeer = true;
	try {
	    slotService.setSlots(new HashMap<String, Slot>());
	} catch (Exception e) {
	    erfolgLeer = false;
	}
	assertTrue(erfolgLeer);

	boolean erfolgGefuellt = true;
	try {
	    slotService.setSlots(generateGefuelltStringSolts());
	} catch (Exception e) {
	    erfolgGefuellt = false;
	}
	assertTrue(erfolgGefuellt);
    }

    @Test
    void getMappedName() {
	SlotService slotService = SlotService.getImplementation();
	// Null
	slotService.setSlots(null);
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
	// Leer
	slotService.setSlots(new HashMap<String, Slot>());
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
	// Gefuellt
	slotService.setSlots(generateGefuelltStringSolts());
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNotNull(slotService.getMappedName(skillSlotNames));
	}
	// Gefuellt Null Resolution
	slotService.setSlots(generateGefuelltStringSoltsNullResolutions());
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}

	// Gefuellt Leer Resolution
	slotService.setSlots(generateGefuelltStringSoltsLeerResolutions());
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}

	// Gefuellt Leer ResolutionValues
	slotService.setSlots(generateGefuelltStringSoltsLeerResolutionValues());
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
	
	// Gefuellt Leer ResolutionValues
	slotService.setSlots(generateGefuelltStringSoltsValueNull());
	assertNull(slotService.getMappedName(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getMappedName(skillSlotNames));
	}
    }

    @Test
    void getInteger() {
	SlotService slotService = SlotService.getImplementation();
	// Null
	slotService.setSlots(null);
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}
	// Leer
	slotService.setSlots(new HashMap<String, Slot>());
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}
	// Gefuellt
	slotService.setSlots(generateGefuelltStringSolts());
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}
	// Gefuellt Null Resolution
	slotService.setSlots(generateGefuelltStringSoltsNullResolutions());
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertNull(slotService.getInteger(skillSlotNames));
	}

	// Gefuellt
	slotService.setSlots(generateGefuelltIntegerSolts());
	assertNull(slotService.getInteger(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertEquals(0, slotService.getInteger(skillSlotNames));
	    assertEquals(new Integer(0), slotService.getInteger(skillSlotNames));
	}
    }
    @Test
    void isSlotEmpty() {
	SlotService slotService = SlotService.getImplementation();
	// Null
	slotService.setSlots(null);
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertTrue(slotService.isSlotEmpty(skillSlotNames));
	}
	
	// Leer
	slotService.setSlots(new HashMap<String, Slot>());
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertTrue(slotService.isSlotEmpty(skillSlotNames));
	}
	// Gefuellt
	slotService.setSlots(generateGefuelltStringSolts());
	assertTrue(slotService.isSlotEmpty(null));
	for (SkillSlotNames skillSlotNames : SkillSlotNames.values()) {
	    assertFalse(slotService.isSlotEmpty(skillSlotNames));
	}
	
	// Gefuellt Null
	slotService.setSlots(generateGefuelltStringSoltsValueNull());
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
	    ValueWrapper valuesItem = ValueWrapper.builder().withValue(Value.builder().withName(null).build())
		    .build();
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
