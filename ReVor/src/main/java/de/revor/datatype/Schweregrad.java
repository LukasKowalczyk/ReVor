package de.revor.datatype;

public enum Schweregrad {

    SCHWER("schwer"), MITTEL("mittel"), EINFACH("einfach"), EGAL("egal");

    private String wert;

    private Schweregrad(String wert) {
	this.wert = wert;
    }

    public static Schweregrad getSchweregradOfWert(String value) {
	if (value != null) {
	    for (Schweregrad mahlzeit : values()) {
		if (mahlzeit.getWert().equals(value.toLowerCase())) {
		    return mahlzeit;
		}
	    }
	}
	return EGAL;
    }

    public String getWert() {
	return wert;
    }
}
