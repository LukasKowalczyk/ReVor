package de.revor.datentype;

import static de.revor.datatype.Schweregrad.EGAL;
import static de.revor.datatype.Schweregrad.EINFACH;
import static de.revor.datatype.Schweregrad.MITTEL;
import static de.revor.datatype.Schweregrad.SCHWER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import de.revor.datatype.Schweregrad;

class SchweregradTest {

    @Test
    void getSchweregradOfWert_Schwer() {
	// Positiv Test
	assertEquals(SCHWER, Schweregrad.getSchweregradOfWert("schwer"));
	assertEquals(SCHWER, Schweregrad.getSchweregradOfWert("Schwer"));
	assertEquals(SCHWER, Schweregrad.getSchweregradOfWert("SCHWER"));
	// Negativ Test
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("schwr"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("Schwr"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("SCHWR"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("mittel"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("Mittel"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("MITTEL"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("einfach"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("Einfach"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("EINFACH"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("egal"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("Egal"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert("EGAL"));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert(""));
	assertNotEquals(SCHWER, Schweregrad.getSchweregradOfWert(null));
    }

    @Test
    void getSchweregradOfWert_Mittel() {
	// Positiv Test
	assertEquals(MITTEL, Schweregrad.getSchweregradOfWert("mittel"));
	assertEquals(MITTEL, Schweregrad.getSchweregradOfWert("Mittel"));
	assertEquals(MITTEL, Schweregrad.getSchweregradOfWert("MITTEL"));
	// Negativ Test
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("schwer"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("Schwer"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("SCHWER"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("mittl"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("Mittl"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("MITTL"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("einfach"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("Einfach"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("EINFACH"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("egal"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("Egal"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert("EGAL"));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert(""));
	assertNotEquals(MITTEL, Schweregrad.getSchweregradOfWert(null));
    }

    @Test
    void getSchweregradOfWert_Einfach() {
	// Positiv Test
	assertEquals(EINFACH, Schweregrad.getSchweregradOfWert("einfach"));
	assertEquals(EINFACH, Schweregrad.getSchweregradOfWert("Einfach"));
	assertEquals(EINFACH, Schweregrad.getSchweregradOfWert("EINFACH"));
	// Negativ Test
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("schwer"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("Schwer"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("SCHWER"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("mittel"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("Mittel"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("MITTEL"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("einfch"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("Einfch"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("EINFCH"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("egal"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("Egal"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert("EGAL"));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert(""));
	assertNotEquals(EINFACH, Schweregrad.getSchweregradOfWert(null));
    }

    @Test
    void getSchweregradOfWert_Egal() {
	// Positiv Test
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert("egal"));
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert("Egal"));
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert("EGAL"));
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert("egl"));
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert("Egl"));
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert("EGL"));
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert(""));
	assertEquals(EGAL, Schweregrad.getSchweregradOfWert(null));
	// Negativ Test
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("schwer"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("Schwer"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("SCHWER"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("mittel"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("Mittel"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("MITTEL"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("einfach"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("Einfach"));
	assertNotEquals(EGAL, Schweregrad.getSchweregradOfWert("EINFACH"));

    }

}
