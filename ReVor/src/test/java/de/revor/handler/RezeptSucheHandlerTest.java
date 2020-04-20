package de.revor.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.services.ServiceClientFactory;
import com.amazon.ask.model.services.directive.DirectiveServiceClient;
import com.amazon.ask.response.ResponseBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import de.revor.datatype.Mahlzeit;
import de.revor.datatype.Rezept;
import de.revor.datatype.Schweregrad;
import de.revor.datatype.SkillSessionAttributeNames;
import de.revor.datatype.SkillSlotNames;
import de.revor.datatype.Zutat;
import de.revor.service.RezeptSucheService;
import de.revor.service.SessionAttributeService;
import de.revor.service.SlotService;

@PrepareForTest(AmazonDynamoDBClientBuilder.class)
class RezeptSucheHandlerTest {
    private RezeptSucheHandler rezeptSucheHandler = new RezeptSucheHandler();
    @Mock
    private AmazonDynamoDBClientBuilder dbClientBuilder;

    @Mock
    private AmazonDynamoDB dynamoDB;

    @Test
    public void canhandle() throws Exception {
	try {
	    rezeptSucheHandler.canHandle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	when(handlerInput.matches(any())).thenReturn(true);
	assertTrue(rezeptSucheHandler.canHandle(handlerInput));

	when(handlerInput.matches(any())).thenReturn(false);
	assertFalse(rezeptSucheHandler.canHandle(handlerInput));
    }

    @Test
    public void handle() throws Exception {
	try {
	    rezeptSucheHandler.handle(null);
	    assertTrue(false);
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	HandlerInput handlerInput = mock(HandlerInput.class);
	SessionAttributeService sessionAttributeService = mock(SessionAttributeService.class);
	AttributesManager attributesManager = mock(AttributesManager.class);
	SlotService slotService = mock(SlotService.class);
	IntentRequest intentRequest = mock(IntentRequest.class);
	Intent intent = mock(Intent.class);
	RezeptSucheService rezeptSuche = mock(RezeptSucheService.class);

	when(handlerInput.getResponseBuilder()).thenReturn(new ResponseBuilder());
	when(handlerInput.getAttributesManager()).thenReturn(attributesManager);
	doNothing().when(rezeptSuche).setAmazonDynamoDB(any());

	RequestEnvelope requestEnvelope = mock(RequestEnvelope.class);
	when(handlerInput.getRequestEnvelope()).thenReturn(requestEnvelope);
	when(requestEnvelope.getRequest()).thenReturn(intentRequest);
	when(intentRequest.getRequestId()).thenReturn("1234");
	when(intentRequest.getIntent()).thenReturn(intent);
	when(intent.getSlots()).thenReturn(new HashMap<String, Slot>());
	ServiceClientFactory serviceClientFactory = mock(ServiceClientFactory.class);
	when(handlerInput.getServiceClientFactory()).thenReturn(serviceClientFactory);
	DirectiveServiceClient directiveServiceClient = mock(DirectiveServiceClient.class);
	when(serviceClientFactory.getDirectiveService()).thenReturn(directiveServiceClient);

	when(sessionAttributeService.isSessionAttributEmpty(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
		.thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.MAHLZEIT))).thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(true);

	when(slotService.getMappedName(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(Schweregrad.EGAL.getWert());
	when(slotService.getMappedName(eq(SkillSlotNames.MAHLZEIT))).thenReturn(Mahlzeit.FRUEH.getWert());
	when(slotService.getInteger(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(0);

	when(rezeptSuche.findeRezepte(any(), any())).thenReturn(new ArrayList<Rezept>());

	FieldSetter.setField(rezeptSucheHandler,
		rezeptSucheHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	FieldSetter.setField(rezeptSucheHandler, rezeptSucheHandler.getClass().getDeclaredField("rezeptSuche"),
		rezeptSuche);
	Optional<Response> response = rezeptSucheHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Ich habe leider nichts gefunden mit deinen Angaben:") > -1);

	when(sessionAttributeService.isSessionAttributEmpty(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
		.thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.MAHLZEIT))).thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(true);

	when(slotService.getMappedName(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(Schweregrad.EGAL.getWert());
	when(slotService.getMappedName(eq(SkillSlotNames.MAHLZEIT))).thenReturn(Mahlzeit.FRUEH.getWert());
	when(slotService.getInteger(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(0);

	when(rezeptSuche.findeRezepte(any(), any())).thenReturn(generateRezepte());

	FieldSetter.setField(rezeptSucheHandler,
		rezeptSucheHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	FieldSetter.setField(rezeptSucheHandler, rezeptSucheHandler.getClass().getDeclaredField("rezeptSuche"),
		rezeptSuche);
	FieldSetter.setField(rezeptSucheHandler, rezeptSucheHandler.getClass().getDeclaredField("slotService"),
		slotService);
	response = rezeptSucheHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString().indexOf("Willst du vielleicht") > -1);

	when(sessionAttributeService.isSessionAttributEmpty(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
		.thenReturn(false);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.MAHLZEIT))).thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(true);

	when(slotService.getMappedName(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(Schweregrad.EGAL.getWert());
	when(slotService.getMappedName(eq(SkillSlotNames.MAHLZEIT))).thenReturn(Mahlzeit.FRUEH.getWert());
	when(slotService.getInteger(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(0);

	when(rezeptSuche.findeRezepte(any(), any())).thenReturn(generateRezepte());

	FieldSetter.setField(rezeptSucheHandler,
		rezeptSucheHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	FieldSetter.setField(rezeptSucheHandler, rezeptSucheHandler.getClass().getDeclaredField("rezeptSuche"),
		rezeptSuche);
	FieldSetter.setField(rezeptSucheHandler, rezeptSucheHandler.getClass().getDeclaredField("slotService"),
		slotService);
	response = rezeptSucheHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString()
		.indexOf("Ich habe leider nichts gefunden mit deinen Angaben:") > -1);

	when(sessionAttributeService.isSessionAttributEmpty(eq(SkillSessionAttributeNames.GEFUNDENE_REZEPTE)))
		.thenReturn(true);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(false);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.MAHLZEIT))).thenReturn(false);
	when(slotService.isSlotEmpty(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(false);

	when(slotService.getMappedName(eq(SkillSlotNames.SCHWEREGRAD))).thenReturn(Schweregrad.EGAL.getWert());
	when(slotService.getMappedName(eq(SkillSlotNames.MAHLZEIT))).thenReturn(Mahlzeit.FRUEH.getWert());
	when(slotService.getInteger(eq(SkillSlotNames.ANZAHLPORTIONEN))).thenReturn(0);

	when(rezeptSuche.findeRezepte(any(), any())).thenReturn(generateRezepte());

	FieldSetter.setField(rezeptSucheHandler,
		rezeptSucheHandler.getClass().getDeclaredField("sessionAttributeService"), sessionAttributeService);
	FieldSetter.setField(rezeptSucheHandler, rezeptSucheHandler.getClass().getDeclaredField("rezeptSuche"),
		rezeptSuche);
	FieldSetter.setField(rezeptSucheHandler, rezeptSucheHandler.getClass().getDeclaredField("slotService"),
		slotService);
	response = rezeptSucheHandler.handle(handlerInput);
	assertTrue(response.isPresent());
	assertTrue(response.get().getOutputSpeech().toString().indexOf("Willst du vielleicht") > -1);
    }

    private List<Rezept> generateRezepte() {
	ArrayList<Rezept> ausg = new ArrayList<>();
	Rezept r = new Rezept();
	r.setAnleitung("anleitung");
	r.setId(1);
	r.setMahlzeit("mittag");
	r.setSchweregrad("einfach");
	r.setTitel("titel");
	r.setZutaten(generateZutaten());
	ausg.add(r);
	return ausg;
    }

    private List<Zutat> generateZutaten() {
	ArrayList<Zutat> zutaten = new ArrayList<>();
	Zutat z = new Zutat();
	z.setAnzahl(1);
	z.setName("name");
	z.setEinheit("g");
	zutaten.add(z);
	zutaten.add(z);
	return zutaten;
    }
}
