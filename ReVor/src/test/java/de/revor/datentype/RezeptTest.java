package de.revor.datentype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.revor.datatype.Rezept;
import de.revor.datatype.Zutat;

class RezeptTest {

    private static final String TITEL = "titel";

    private static final String ZUTATEN = "zutaten";

    private static final String SCHWEREGRAD = "schweregrad";

    private static final String ANLEITUNG = "anleitung";

    private static final String MAHLZEIT = "mahlzeit";

    private static final String ID = "ID";

    @Test
    void toStringTest() {
	Rezept rezept = new Rezept();
	assertNotNull(rezept.toString());
    }
    
    @Test
    void getEinkaufsliste() {
	Rezept rezept = new Rezept();
	assertEquals(rezept.getZutaten(), rezept.getEinkaufsliste(1));
	assertEquals(rezept.getZutaten(), rezept.getEinkaufsliste(0));
	assertEquals(null, rezept.getEinkaufsliste(0));

	Rezept gefuelltFelder = Rezept.mappeFromMap(gefuelltFelder());
	assertEquals(gefuelltFelder.getZutaten(), gefuelltFelder.getEinkaufsliste(1));
	assertEquals(gefuelltFelder.getZutaten(), gefuelltFelder.getEinkaufsliste(0));
	assertEquals(gefuelltFelder.getZutaten().get(0).getAnzahl() * 2,
		gefuelltFelder.getEinkaufsliste(2).get(0).getAnzahl());
	assertNotEquals(null, gefuelltFelder.getEinkaufsliste(0));

    }

    @Test
    void mappeFromMap_null_leer() {
	assertNotNull(Rezept.mappeFromMap(null));
	assertNotNull(Rezept.mappeFromMap(new HashMap<>()));
	assertNotNull(Rezept.mappeFromMap(nullFelder()));
	assertNotNull(Rezept.mappeFromMap(leerFelder()));
    }

    @Test
    void mappeFromMap_ID() {
	Rezept rezept = Rezept.mappeFromMap(nullFelder());
	assertNotNull(rezept);
	assertEquals(null, rezept.getId());
	//
	Rezept mapIdNull = Rezept.mappeFromMap(leerFelder());
	assertNotNull(mapIdNull);
	assertEquals(0, mapIdNull.getId());
	//
	boolean mapIdNullMitPunkt = false;
	try {
	    Rezept.mappeFromMap(mapIdNullMitPunkt());
	} catch (Exception e) {
	    mapIdNullMitPunkt = true;
	}
	assertTrue(mapIdNullMitPunkt);
	//
	boolean mapIdNullMitKomma = false;
	try {
	    Rezept.mappeFromMap(mapIdNullMitKomma());
	} catch (Exception e) {
	    mapIdNullMitKomma = true;
	}
	assertTrue(mapIdNullMitKomma);
    }

    @Test
    void mappeFromMap_Titel() {
	Rezept rezept = Rezept.mappeFromMap(nullFelder());
	assertNotNull(rezept);
	assertEquals(null, rezept.getTitel());
	//
	Rezept mapTitelLeer = Rezept.mappeFromMap(leerFelder());
	assertNotNull(mapTitelLeer);
	assertEquals("", mapTitelLeer.getTitel());
	//
	Rezept gefuelltFelder = Rezept.mappeFromMap(gefuelltFelder());
	assertNotNull(gefuelltFelder);
	assertEquals("titel", gefuelltFelder.getTitel());
	assertNotEquals("", gefuelltFelder.getTitel());
	assertNotEquals(null, gefuelltFelder.getTitel());
	//
    }

    @Test
    void mappeFromMap_Anleitung() {
	Rezept rezept = Rezept.mappeFromMap(nullFelder());
	assertNotNull(rezept);
	assertEquals(null, rezept.getAnleitung());
	//
	Rezept mapAnleitungLeer = Rezept.mappeFromMap(leerFelder());
	assertNotNull(mapAnleitungLeer);
	assertEquals("", mapAnleitungLeer.getAnleitung());
	//
	Rezept gefuelltFelder = Rezept.mappeFromMap(gefuelltFelder());
	assertNotNull(gefuelltFelder);
	assertEquals("anleitung", gefuelltFelder.getAnleitung());
	assertNotEquals("", gefuelltFelder.getAnleitung());
	assertNotEquals(null, gefuelltFelder.getAnleitung());
	//
    }

    @Test
    void mappeFromMap_Mahlzeit() {
	Rezept rezept = Rezept.mappeFromMap(nullFelder());
	assertNotNull(rezept);
	assertEquals(null, rezept.getMahlzeit());
	//
	Rezept mapMahlzeitLeer = Rezept.mappeFromMap(leerFelder());
	assertNotNull(mapMahlzeitLeer);
	assertEquals("", mapMahlzeitLeer.getMahlzeit());
	//
	Rezept gefuelltFelder = Rezept.mappeFromMap(gefuelltFelder());
	assertNotNull(gefuelltFelder);
	assertEquals("mittag", gefuelltFelder.getMahlzeit());
	assertNotEquals("", gefuelltFelder.getMahlzeit());
	assertNotEquals(null, gefuelltFelder.getMahlzeit());
	//
    }

    @Test
    void mappeFromMap_Schweregrad() {
	Rezept rezept = Rezept.mappeFromMap(nullFelder());
	assertNotNull(rezept);
	assertEquals(null, rezept.getSchweregrad());
	//
	Rezept mapSchweregradLeer = Rezept.mappeFromMap(leerFelder());
	assertNotNull(mapSchweregradLeer);
	assertEquals("", mapSchweregradLeer.getSchweregrad());
	//
	Rezept gefuelltFelder = Rezept.mappeFromMap(gefuelltFelder());
	assertNotNull(gefuelltFelder);
	assertEquals("einfach", gefuelltFelder.getSchweregrad());
	assertNotEquals("", gefuelltFelder.getSchweregrad());
	assertNotEquals(null, gefuelltFelder.getSchweregrad());
	//
    }

    @Test
    void mappeFromMap_Zutaten() {
	Rezept rezept = Rezept.mappeFromMap(nullFelder());
	assertNotNull(rezept);
	assertEquals(null, rezept.getZutaten());
	//
	Rezept mapZutatenLeer = Rezept.mappeFromMap(leerFelder());
	assertNotNull(mapZutatenLeer);
	assertEquals(new ArrayList<Map<String, Object>>(), mapZutatenLeer.getZutaten());
	//
	Rezept gefuelltFelder = Rezept.mappeFromMap(gefuelltFelder());
	assertNotNull(gefuelltFelder);
	assertEquals(gefuellteZutatenList().get(0).getAnzahl(), gefuelltFelder.getZutaten().get(0).getAnzahl());
	assertEquals(gefuellteZutatenList().get(0).getEinheit(), gefuelltFelder.getZutaten().get(0).getEinheit());
	assertEquals(gefuellteZutatenList().get(0).getName(), gefuelltFelder.getZutaten().get(0).getName());
	assertNotEquals(new ArrayList<Map<String, Object>>(), gefuelltFelder.getZutaten());
	assertNotEquals(null, gefuelltFelder.getZutaten());
	//
    }

    private List<Zutat> gefuellteZutatenList() {
	ArrayList<Zutat> list = new ArrayList<>();
	Zutat z = new Zutat();
	z.setAnzahl(0);
	z.setEinheit("g");
	z.setName("zutat");
	list.add(z);
	return list;
    }

    private Map<String, Object> gefuelltFelder() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ID, "0");
	hashMap.put(MAHLZEIT, "mittag");
	hashMap.put(ANLEITUNG, "anleitung");
	hashMap.put(SCHWEREGRAD, "einfach");
	hashMap.put(ZUTATEN, gefuellteZutaten());
	hashMap.put(TITEL, "titel");
	return hashMap;
    }

    private Map<String, Object> mapIdNullMitKomma() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ID, "0,0");
	hashMap.put(MAHLZEIT, "");
	hashMap.put(ANLEITUNG, "");
	hashMap.put(SCHWEREGRAD, "");
	hashMap.put(ZUTATEN, null);
	hashMap.put(TITEL, "");
	return hashMap;
    }

    private Map<String, Object> mapIdNullMitPunkt() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ID, "0.0");
	hashMap.put(MAHLZEIT, "");
	hashMap.put(ANLEITUNG, "");
	hashMap.put(SCHWEREGRAD, "");
	hashMap.put(ZUTATEN, null);
	hashMap.put(TITEL, "");
	return hashMap;
    }

    private HashMap<String, Object> nullFelder() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ID, null);
	hashMap.put(MAHLZEIT, null);
	hashMap.put(ANLEITUNG, null);
	hashMap.put(SCHWEREGRAD, null);
	hashMap.put(ZUTATEN, null);
	hashMap.put(TITEL, null);
	return hashMap;
    }

    private HashMap<String, Object> leerFelder() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ID, "0");
	hashMap.put(MAHLZEIT, "");
	hashMap.put(ANLEITUNG, "");
	hashMap.put(SCHWEREGRAD, "");
	hashMap.put(ZUTATEN, new ArrayList<Map<String, Object>>());
	hashMap.put(TITEL, "");
	return hashMap;
    }

    private List<Map<String, Object>> gefuellteZutaten() {
	ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	list.add(gefuelltZutat());
	return list;
    }

    private HashMap<String, Object> gefuelltZutat() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put("anzahl", "0");
	hashMap.put("name", "zutat");
	hashMap.put("einheit", "g");
	return hashMap;
    }

}
