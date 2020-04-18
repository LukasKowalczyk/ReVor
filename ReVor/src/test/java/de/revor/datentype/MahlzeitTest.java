package de.revor.datentype;

import static de.revor.datatype.Mahlzeit.ABEND;
import static de.revor.datatype.Mahlzeit.FRUEH;
import static de.revor.datatype.Mahlzeit.JETZT;
import static de.revor.datatype.Mahlzeit.MITTAG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import de.revor.datatype.Mahlzeit;

class MahlzeitTest {

    @Test
    void getMahlzeitOfWert_Mittags() {
	// Positiv Test
	assertEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("mittag"));
	assertEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("Mittag"));
	assertEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("MITTAG"));
	// Negativ Test
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("früh"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("FRÜH"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("abend"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("ABEND"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("Mitag"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("MITTG"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("jetzt"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert("JETZT"));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert(""));
	assertNotEquals(MITTAG, Mahlzeit.getMahlzeitOfWert(null));
    }

    @Test
    void getMahlzeitOfWert_Abend() {
	// Positiv Test
	assertEquals(ABEND, Mahlzeit.getMahlzeitOfWert("abend"));
	assertEquals(ABEND, Mahlzeit.getMahlzeitOfWert("Abend"));
	assertEquals(ABEND, Mahlzeit.getMahlzeitOfWert("ABEND"));
	// Negativ Test
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("früh"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("Früh"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("FRÜH"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("abed"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("ABeD"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("Mittag"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("MITTAG"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("jetzt"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert("JETZT"));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert(""));
	assertNotEquals(ABEND, Mahlzeit.getMahlzeitOfWert(null));
    }

    @Test
    void getMahlzeitOfWert_Jetzt() {
	// Positiv Test
	assertEquals(JETZT, Mahlzeit.getMahlzeitOfWert(""));
	assertEquals(JETZT, Mahlzeit.getMahlzeitOfWert(null));
	assertEquals(JETZT, Mahlzeit.getMahlzeitOfWert("jetzt"));
	assertEquals(JETZT, Mahlzeit.getMahlzeitOfWert("Jetzt"));
	assertEquals(JETZT, Mahlzeit.getMahlzeitOfWert("JETZT"));
	assertEquals(JETZT, Mahlzeit.getMahlzeitOfWert("jtzt"));
	assertEquals(JETZT, Mahlzeit.getMahlzeitOfWert("JEZT"));
	// Negativ Test
	assertNotEquals(JETZT, Mahlzeit.getMahlzeitOfWert("früh"));
	assertNotEquals(JETZT, Mahlzeit.getMahlzeitOfWert("FRÜH"));
	assertNotEquals(JETZT, Mahlzeit.getMahlzeitOfWert("abend"));
	assertNotEquals(JETZT, Mahlzeit.getMahlzeitOfWert("ABEND"));
	assertNotEquals(JETZT, Mahlzeit.getMahlzeitOfWert("Mittag"));
	assertNotEquals(JETZT, Mahlzeit.getMahlzeitOfWert("MITTaG"));
    }

    @Test
    void getMahlzeitOfWert_Frueh() {
	// Positiv Test
	assertEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("früh"));
	assertEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("Früh"));
	assertEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("FRÜH"));
	// Negativ Test
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("jetzt"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("JETZT"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("abend"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("ABEND"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("Mittag"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("MITTaG"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("frueh"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("Frueh"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert("FRUEH"));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert(""));
	assertNotEquals(FRUEH, Mahlzeit.getMahlzeitOfWert(null));
    }

    @Test
    void ermittleAktuelleMahlzeit_Frueh() {
	LocalDateTime localDateTime = setzeStundeLocaldateTime("03");
	assertEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	// Alles zwischen 4 und 11 Uhr muss FRUEH sein
	for (int i = 4; i < 12; i++) {
	    String stunde = (i < 10 ? "0" : "") + i;
	    localDateTime = setzeStundeLocaldateTime(stunde);
	    assertEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	}
	localDateTime = setzeStundeLocaldateTime("12");
	assertNotEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
    }

    @Test
    void ermittleAktuelleMahlzeit_Mittag() {
	LocalDateTime localDateTime = setzeStundeLocaldateTime("12");
	assertEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	// Alles zwischen 12 und 15 Uhr muss MITTAG sein
	for (int i = 12; i < 16; i++) {
	    String stunde = (i < 10 ? "0" : "") + i;
	    localDateTime = setzeStundeLocaldateTime(stunde);
	    assertEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	}
	localDateTime = setzeStundeLocaldateTime("16");
	assertEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
    }

    @Test
    void ermittleAktuelleMahlzeit_Abend() {
	// Alles zwischen 0 und 3 Uhr muss MITTAG sein
	for (int i = 0; i < 4; i++) {
	    String stunde = (i < 10 ? "0" : "") + i;
	    LocalDateTime localDateTime = setzeStundeLocaldateTime(stunde);
	    assertEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	}
	// Alles zwischen 16 und 25 Uhr muss MITTAG sein
	for (int i = 16; i < 24; i++) {
	    String stunde = (i < 10 ? "0" : "") + i;
	    LocalDateTime localDateTime = setzeStundeLocaldateTime(stunde);
	    assertEquals(ABEND, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(FRUEH, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(MITTAG, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	    assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	}
    }

    @Test
    void ermittleAktuelleMahlzeit_NULL() {
	// Alles zwischen 0 und 24 Uhr darf nicht null sein
	for (int i = 0; i < 24; i++) {
	    String stunde = (i < 10 ? "0" : "") + i;
	    LocalDateTime localDateTime = setzeStundeLocaldateTime(stunde);
	    assertNotEquals(null, Mahlzeit.ermittleAktuelleMahlzeit(localDateTime));
	}
    }

    private LocalDateTime setzeStundeLocaldateTime(String stunde) {
	Clock clock = Clock.fixed(Instant.parse("2014-12-21T" + stunde + ":15:30.00Z"), ZoneId.of("UTC"));
	return LocalDateTime.now(clock);
    }
}
