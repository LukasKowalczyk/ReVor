package de.revor.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import de.revor.service.EMailSendenService;
import de.revor.service.EinkaufslisteService;
import de.revor.service.HandlerUtilService;
import de.revor.service.SessionAttributeService;

class RezeptAusgewaehltHandlerTest {

    private RezeptAusgewaehltHandler rezeptAusgewaehltHandler = new RezeptAusgewaehltHandler();

    private static final String TITEL = "titel";

    private static final String ZUTATEN = "zutaten";

    private static final String SCHWEREGRAD = "schweregrad";

    private static final String ANLEITUNG = "anleitung";

    private static final String MAHLZEIT = "mahlzeit";

    private static final String ID = "ID";

    @Test
    public void canhandle() throws Exception {
	try {
	    rezeptAusgewaehltHandler.canHandle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.matches(any())).thenReturn(true);
	assertTrue(rezeptAusgewaehltHandler.canHandle(handlerInput));

	when(handlerInput.matches(any())).thenReturn(false);
	assertFalse(rezeptAusgewaehltHandler.canHandle(handlerInput));
    }

    @Test
    public void handle() throws Exception {
	try {
	    rezeptAusgewaehltHandler.handle(null);
	    assertTrue(false);
	} catch (IllegalArgumentException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	SessionAttributeService sessionAttributeService = mock(SessionAttributeService.class);
	EinkaufslisteService einkaufslisteService = mock(EinkaufslisteService.class);
	EMailSendenService eMailSendenService = mock(EMailSendenService.class);
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);

	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());
	doNothing().when(sessionAttributeService).setSessionAttributes(any());

	when(handlerUtilService.getUserEmail(any())).thenReturn("");
	when(sessionAttributeService
		.<Integer>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE_INDEX)))
			.thenReturn(new Integer(0));
	when(sessionAttributeService.<Integer>getSessionAttribut(eq(SkillSessionAttributeNames.ANZAHL_PORTIONEN)))
		.thenReturn(new Integer(1));
	when(sessionAttributeService
		.<ArrayList<Map<String, Object>>>getSessionAttribut(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
			.thenReturn(generateRezeptGefuellteMap());
	FieldSetter.setField(rezeptAusgewaehltHandler,
		rezeptAusgewaehltHandler.getClass().getDeclaredField("sessionAttributeService"),
		sessionAttributeService);
	FieldSetter.setField(rezeptAusgewaehltHandler,
		rezeptAusgewaehltHandler.getClass().getDeclaredField("einkaufslisteService"), einkaufslisteService);
	FieldSetter.setField(rezeptAusgewaehltHandler,
		rezeptAusgewaehltHandler.getClass().getDeclaredField("eMailSendenService"), eMailSendenService);
	FieldSetter.setField(rezeptAusgewaehltHandler,
		rezeptAusgewaehltHandler.getClass().getDeclaredField("handlerUtilService"), handlerUtilService);
	Optional<Response> response = rezeptAusgewaehltHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Die email ist versendet und deine einkaufsliste") > -1);

	doThrow(new Exception()).when(eMailSendenService).versendeRezeptUndEinkaufsliste(any(), any(), any());
	response = rezeptAusgewaehltHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(
		response.get().getOutputSpeech().toString().indexOf("leider konnte ich dir keine Email senden") > -1);
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
	hashMap.put("anzahl", "1");
	hashMap.put("name", "zutat");
	hashMap.put("einheit", "g");
	return hashMap;
    }

}
