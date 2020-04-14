package de.revor.datatype;

public enum SkillSlotNames {
    MAHLZEIT("mahlzeit"), SCHWEREGRAD("schweregrad"), ANZAHLPORTIONEN("anzahlportionen");
    
    private String slotName;

    private SkillSlotNames(String slotName) {
	this.slotName=slotName;
    }

    public String getSlotName() {
        return slotName;
    }
}
