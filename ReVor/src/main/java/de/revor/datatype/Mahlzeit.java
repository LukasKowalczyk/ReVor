package de.revor.datatype;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Mahlzeit {

    MITTAG("mittag"), ABEND("abend"), FRUEH("früh"), JETZT("jetzt");

    private static final Logger logger = LoggerFactory.getLogger(Mahlzeit.class);
    private String wert;

    private Mahlzeit(String wert) {
	this.wert = wert;
    }

    public static Mahlzeit getMahlzeitOfWert(String value) {
	for (Mahlzeit mahlzeit : values()) {
	    if (mahlzeit.getWert().equals(value)) {
		return mahlzeit;
	    }
	}
	return JETZT;
    }

    public String getWert() {
	return wert;
    }

    public static Mahlzeit ermittleAktuelleMahlzeit() {
	DateTimeFormatter df = DateTimeFormatter.ofPattern("HH");
	int tagesStunden = Integer.parseInt(LocalDateTime.now().format(df));
	logger.debug("TAGES_STUNDE="+tagesStunden);
	if (4 <= tagesStunden && tagesStunden < 12) {
	    return FRUEH;
	} else if (12 <= tagesStunden && tagesStunden < 16) {
	    return MITTAG;
	} else if (16 <= tagesStunden && tagesStunden < 4) {
	    return ABEND;
	}
	return MITTAG;
    }
}
