package de.revor.service;

import java.util.List;
import java.util.Map;

import com.amazon.ask.model.Slot;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.amazon.ask.model.slu.entityresolution.Resolutions;
import com.amazon.ask.model.slu.entityresolution.Value;
import com.amazon.ask.model.slu.entityresolution.ValueWrapper;

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

    public void setSlots(Map<String, Slot> slots) {
	this.slots = slots;
    }

    public String getMappedName(SkillSlotNames reVorSlotNames) {
	if (slots != null && reVorSlotNames != null) {
	    Slot slot = slots.get(reVorSlotNames.getSlotName());
	    if (slot != null) {
		Resolutions resolutions = slot.getResolutions();
		if (resolutions != null) {
		    List<Resolution> resolutionsPerAuthority = resolutions.getResolutionsPerAuthority();
		    if (resolutionsPerAuthority != null && resolutionsPerAuthority.size() > 0) {
			List<ValueWrapper> values = resolutionsPerAuthority.get(0).getValues();
			if (values != null && values.size() > 0) {
			    ValueWrapper valueWrapper = values.get(0);
			    if (valueWrapper != null) {
				Value value = valueWrapper.getValue();
				if (value != null) {
				    return value.getName();
				}
			    }
			}
		    }
		}
	    }
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
