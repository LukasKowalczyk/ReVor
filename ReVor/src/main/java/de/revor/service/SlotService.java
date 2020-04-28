package de.revor.service;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;

import de.revor.datatype.SkillSlotNames;

public class SlotService {

    private HandlerUtilService handlerUtilService = HandlerUtilService.getImpementation();

    private static SlotService SlotService;

    private Map<String, Slot> slots;

    private SlotService() {
	slots = null;
    }

    public static SlotService getImplementation() {
	return SlotService == null ? new SlotService() : SlotService;
    }

    public void setSlots(HandlerInput input) {
	this.slots = handlerUtilService.getSlots(input);
    }

    public String getMappedName(SkillSlotNames reVorSlotNames) {
	if (slots != null && reVorSlotNames != null) {
	    Slot slot = slots.get(reVorSlotNames.getSlotName());
	    return handlerUtilService.getMappedNameOfSlot(slot);
	}
	return null;
    }

    public Integer getInteger(SkillSlotNames reVorSlotNames) {
	if (slots != null && reVorSlotNames != null) {
	    Slot slot = slots.get(reVorSlotNames.getSlotName());
	    if (slot != null) {
		try {
		    return Integer.valueOf(slot.getValue());
		} catch (NumberFormatException e) {
		    return null;
		}
	    }
	}
	return null;
    }

    public boolean isSlotEmpty(SkillSlotNames reVorSlotNames) {
	if (slots != null && reVorSlotNames != null) {
	    return !slots.containsKey(reVorSlotNames.getSlotName())
		    || slots.get(reVorSlotNames.getSlotName()).getValue() == null;
	}
	return true;
    }
}
