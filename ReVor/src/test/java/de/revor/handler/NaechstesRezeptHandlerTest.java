package de.revor.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;

import de.revor.datatype.SkillSessionAttributeNames;
import de.revor.service.SessionAttributeService;

class NaechstesRezeptHandlerTest {

    private NaechstesRezeptHandler naechstesRezeptHandler = new NaechstesRezeptHandler();

    private static final String TITEL = "titel";

    private static final String ZUTATEN = "zutaten";

    private static final String SCHWEREGRAD = "schweregrad";

    private static final String ANLEITUNG = "anleitung";

    private static final String MAHLZEIT = "mahlzeit";

    private static final String ID = "ID";

    @Test
    public void canhandle() throws Exception {
	try {
	    naechstesRezeptHandler.canHandle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.matches(any())).thenReturn(true);
	assertTrue(naechstesRezeptHandler.canHandle(handlerInput));

	when(handlerInput.matches(any())).thenReturn(false);
	assertFalse(naechstesRezeptHandler.canHandle(handlerInput));
    }

    @Test
    public void handle() throws Exception {
	try {
	    naechstesRezeptHandler.handle(null);
	    assertTrue(false);
	} catch (IllegalArgumentException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	SessionAttributeService sessionAttributeService = mock(SessionAttributeService.class);
	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());

	FieldSetter.setField(naechstesRezeptHandler,
		naechstesRezeptHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	assertTrue(naechstesRezeptHandler.handle(handlerInput).isPresent());

	when(sessionAttributeService
		.<Integer>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX)))
			.thenReturn(new Integer(0));
	FieldSetter.setField(naechstesRezeptHandler,
		naechstesRezeptHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	Optional<Response> response = naechstesRezeptHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString().indexOf("Ich habe leider keine Rezepte mehr") > -1);

	when(sessionAttributeService
		.<Integer>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX)))
			.thenReturn(new Integer(0));
	when(sessionAttributeService
		.<ArrayList<Map<String, Object>>>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
			.thenReturn(generateRezeptGefuellteMap());
	FieldSetter.setField(naechstesRezeptHandler,
		naechstesRezeptHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	response = naechstesRezeptHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString().indexOf("Willst du vielleich") > -1);

	// Wir haben genug rezepte und bekommen ein weiteres vorgeschlagen
	when(sessionAttributeService
		.<Integer>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX)))
			.thenReturn(new Integer(0));
	when(sessionAttributeService
		.<ArrayList<Map<String, Object>>>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
			.thenReturn(generateRezeptGefuellteMap());
	FieldSetter.setField(naechstesRezeptHandler,
		naechstesRezeptHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	response = naechstesRezeptHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString().indexOf("Willst du vielleich") > -1);

	when(sessionAttributeService
		.<Integer>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX)))
			.thenReturn(new Integer(generateRezeptGefuellteMap().size() + 1));
	when(sessionAttributeService
		.<ArrayList<Map<String, Object>>>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
			.thenReturn(null);
	FieldSetter.setField(naechstesRezeptHandler,
		naechstesRezeptHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	response = naechstesRezeptHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString().indexOf("Ich habe leider keine Rezepte mehr") > -1);

	when(sessionAttributeService
		.<Integer>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX)))
			.thenReturn(new Integer(generateRezeptGefuellteMap().size() + 1));
	when(sessionAttributeService
		.<ArrayList<Map<String, Object>>>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
			.thenReturn(new ArrayList<Map<String, Object>>());
	FieldSetter.setField(naechstesRezeptHandler,
		naechstesRezeptHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	response = naechstesRezeptHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString().indexOf("Ich habe leider keine Rezepte mehr") > -1);

    }

    private ArrayList<Map<String, Object>> generateRezeptGefuellteMap() {
	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	hashMap.put(ID, "0");
	hashMap.put(MAHLZEIT, "mittag");
	hashMap.put(ANLEITUNG, "anleitung");
	hashMap.put(SCHWEREGRAD, "einfach");
	hashMap.put(ZUTATEN, gefuellteZutaten());
	hashMap.put(TITEL, "titel");
	ArrayList<Map<String, Object>> ausg = new ArrayList<>();
	ausg.add(hashMap);
	ausg.add(hashMap);
	return ausg;
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
