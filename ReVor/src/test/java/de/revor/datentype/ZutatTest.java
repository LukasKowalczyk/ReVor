package de.revor.datentype;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import de.revor.datatype.Zutat;

class ZutatTest {

    private static final String EINHEIT = "einheit";

    private static final String NAME = "name";

    private static final String ANZAHL = "anzahl";

    @Test
    void mappeFromMap_null_leer() {
	assertNotNull(Zutat.mappeFromMap(null));
	assertNotNull(Zutat.mappeFromMap(new HashMap<>()));
	Zutat zutat = Zutat.mappeFromMap(nullFelder());
	assertNotNull(zutat);
    }

    @Test
    void mappeFromMap_Anzahl() {
	Zutat zutat = Zutat.mappeFromMap(nullFelder());
	assertNotNull(zutat);
	assertEquals(0, zutat.getAnzahl());
	//
	Zutat mapAnzahlNull = Zutat.mappeFromMap(leerFelder());
	assertNotNull(mapAnzahlNull);
	assertEquals(0, mapAnzahlNull.getAnzahl());
	//
	boolean mapAnzahlNullMitPunkt = false;
	try {
	    Zutat.mappeFromMap(mapAnzahlNullMitPunkt());
	} catch (Exception e) {
	    mapAnzahlNullMitPunkt = true;
	}
	assertTrue(mapAnzahlNullMitPunkt);
	//
	boolean mapAnzahlNullMitKomma = false;
	try {
	    Zutat.mappeFromMap(mapAnzahlNullMitKomma());
	} catch (Exception e) {
	    mapAnzahlNullMitKomma = true;
	}
	assertTrue(mapAnzahlNullMitKomma);
    }

    @Test
    void mappeFromMap_Einheit() {
	Zutat zutat = Zutat.mappeFromMap(nullFelder());
	assertNotNull(zutat);
	assertEquals(null, zutat.getEinheit());
	//
	Zutat mapEinheitLeer = Zutat.mappeFromMap(leerFelder());
	assertNotNull(mapEinheitLeer);
	assertEquals("", mapEinheitLeer.getEinheit());
	//
	Zutat gefuelltFelder = Zutat.mappeFromMap(gefuelltFelder());
	assertNotNull(gefuelltFelder);
	assertEquals("g", gefuelltFelder.getEinheit());
	assertNotEquals("", gefuelltFelder.getEinheit());
	assertNotEquals(null, gefuelltFelder.getEinheit());
	//
    }
    
    @Test
    void mappeFromMap_Name() {
	Zutat zutat = Zutat.mappeFromMap(nullFelder());
	assertNotNull(zutat);
	assertEquals(null, zutat.getName());
	//
	Zutat mapEinheitLeer = Zutat.mappeFromMap(leerFelder());
	assertNotNull(mapEinheitLeer);
	assertEquals("", mapEinheitLeer.getName());
	//
	Zutat gefuelltFelder = Zutat.mappeFromMap(gefuelltFelder());
	assertNotNull(gefuelltFelder);
	assertEquals("zutat", gefuelltFelder.getName());
	assertNotEquals("", gefuelltFelder.getName());
	assertNotEquals(null, gefuelltFelder.getName());
	//
    }
    private HashMap<String, Object> nullFelder() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ANZAHL, null);
	hashMap.put(NAME, null);
	hashMap.put(EINHEIT, null);
	return hashMap;
    }

    private HashMap<String, Object> leerFelder() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ANZAHL, "0");
	hashMap.put(NAME, "");
	hashMap.put(EINHEIT, "");
	return hashMap;
    }
    
    private HashMap<String, Object> gefuelltFelder() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ANZAHL, "0");
	hashMap.put(NAME, "zutat");
	hashMap.put(EINHEIT, "g");
	return hashMap;
    }

    private HashMap<String, Object> mapAnzahlNullMitPunkt() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ANZAHL, "0.0");
	hashMap.put(NAME, "");
	hashMap.put(EINHEIT, "");
	return hashMap;
    }

    private HashMap<String, Object> mapAnzahlNullMitKomma() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ANZAHL, "0,0");
	hashMap.put(NAME, "");
	hashMap.put(EINHEIT, "");
	return hashMap;
    }
}
