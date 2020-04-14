package de.revor.service;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;

import de.revor.datatype.SkillSlotNames;

public class SlotService {
    
    private static SlotService SlotService;
    
    private Map<String, Slot> slots;

    private SlotService() {
	slots = null;
    }

    public static SlotService getImplementation() {
	return SlotService == null ? new SlotService() : SlotService;
    }

    public void setHandlerInput(HandlerInput handlerInput) {
	IntentRequest intentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
	slots = intentRequest.getIntent().getSlots();
    }

    public String getMappedName(SkillSlotNames reVorSlotNames) {
	if (slots != null) {
	    return slots.get(reVorSlotNames.getSlotName()).getResolutions().getResolutionsPerAuthority().get(0)
		    .getValues().get(0).getValue().getName();
	}
	return null;
    }

    public Integer getInteger(SkillSlotNames reVorSlotNames) {
	if (slots != null) {
	    return Integer.valueOf(slots.get(reVorSlotNames.getSlotName()).getValue());
	}
	return null;
    }

    public boolean isSlotEmpty(SkillSlotNames reVorSlotNames) {
	if (slots != null) {
	    return !slots.containsKey(reVorSlotNames.getSlotName())
		    || slots.get(reVorSlotNames.getSlotName()).getValue() == null;
	}
	return true;
    }
}
