package de.revor.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.services.listManagement.AlexaList;
import com.amazon.ask.model.services.listManagement.AlexaListItem;
import com.amazon.ask.model.services.listManagement.AlexaListMetadata;
import com.amazon.ask.model.services.listManagement.AlexaListsMetadata;
import com.amazon.ask.model.services.listManagement.ListManagementServiceClient;

import de.revor.datatype.Zutat;

class EinkaufslisteServiceTest {

    @Test
    void fuegeZurEinkaufslisteHinzu() throws NoSuchFieldException, SecurityException {
	EinkaufslisteService implementation = EinkaufslisteService.getImplementation();
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(implementation, implementation.getClass().getDeclaredField("handlerUtilService"),
		handlerUtilService);
	ListManagementServiceClient listManagmentServiceClient = mock(ListManagementServiceClient.class);
	AlexaListsMetadata alexaListsMetadata = mock(AlexaListsMetadata.class);
	when(handlerUtilService.getListManagementServiceClient(any())).thenReturn(listManagmentServiceClient);

	when(listManagmentServiceClient.getList(any(), any())).thenReturn(null);
	fuegZurEinkaufslisteHinzu(implementation);

	when(listManagmentServiceClient.getList(any(), any())).thenReturn(AlexaList.builder().build());
	fuegZurEinkaufslisteHinzu(implementation);

	when(listManagmentServiceClient.getList(any(), any())).thenReturn(gefuellteAlexaListMitFehler());
	fuegZurEinkaufslisteHinzu(implementation);
	
	when(listManagmentServiceClient.getList(any(), any())).thenReturn(gefuellteAlexaList());
	fuegZurEinkaufslisteHinzu(implementation);
	
	when(listManagmentServiceClient.getList(any(), any())).thenReturn(gefuellteAlexaList());
	when(listManagmentServiceClient.getListsMetadata()).thenReturn(null);
	fuegZurEinkaufslisteHinzu(implementation);
	
	when(listManagmentServiceClient.getList(any(), any())).thenReturn(gefuellteAlexaList());
	when(listManagmentServiceClient.getListsMetadata()).thenReturn(alexaListsMetadata);
	when(alexaListsMetadata.getLists()).thenReturn(null);
	fuegZurEinkaufslisteHinzu(implementation);
	
	when(listManagmentServiceClient.getList(any(), any())).thenReturn(gefuellteAlexaList());
	when(listManagmentServiceClient.getListsMetadata()).thenReturn(alexaListsMetadata);
	when(alexaListsMetadata.getLists()).thenReturn(getGefuellteListe());
	fuegZurEinkaufslisteHinzu(implementation);
    }

    private List<AlexaListMetadata> getGefuellteListe() {
	ArrayList<AlexaListMetadata> ausg = new ArrayList<>();
	ausg.add(AlexaListMetadata.builder().withName("test").build());
	ausg.add(AlexaListMetadata.builder().withName("Alexa shopping list").build());
	return ausg;
    }

    private AlexaList gefuellteAlexaList() {
	return AlexaList.builder().addItemsItem(AlexaListItem.builder().withValue("ballala").build()).addItemsItem(AlexaListItem.builder().withValue("test 100 g").build()).addItemsItem(AlexaListItem.builder().withValue("random 1 mg").build()).build();
    }
    
    private AlexaList gefuellteAlexaListMitFehler() {
	return AlexaList.builder().addItemsItem(AlexaListItem.builder().withValue("ballala").build()).addItemsItem(AlexaListItem.builder().withValue("test").build()).build();
    }
    
    private void fuegZurEinkaufslisteHinzu(EinkaufslisteService implementation) {
	boolean erfolg = true;
	try {
	    implementation.setListManagementServiceClient(null);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    implementation.setListManagementServiceClient(mock(HandlerInput.class));
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    implementation.setListManagementServiceClient(mock(HandlerInput.class));
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    implementation.setListManagementServiceClient(mock(HandlerInput.class));
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	} catch (Exception e) {
	    erfolg = false;
	}

	assertTrue(erfolg);
    }

    private ArrayList<Zutat> generateNewZutaten() {
	ArrayList<Zutat> arrayList = new ArrayList<Zutat>();
	Zutat zutat = new Zutat();
	zutat.setAnzahl(1);
	zutat.setEinheit("mg");
	zutat.setName("random");
	arrayList.add(zutat);
	return arrayList;
    }

    private ArrayList<Zutat> generateUpdateZutaten() {
	ArrayList<Zutat> arrayList = new ArrayList<Zutat>();
	Zutat zutat = new Zutat();
	zutat.setAnzahl(100);
	zutat.setEinheit("g");
	zutat.setName("test");
	arrayList.add(zutat);
	return arrayList;
    }

    @Test
    void shoppingID() {
	boolean erfolg = true;
	try {

	    EinkaufslisteService implementation = EinkaufslisteService.getImplementation();
	    HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	    FieldSetter.setField(implementation, implementation.getClass().getDeclaredField("handlerUtilService"),
		    handlerUtilService);

	    implementation.setListManagementServiceClient(mock(HandlerInput.class));
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());

	    implementation.setListManagementServiceClient(mock(HandlerInput.class));
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());

	    implementation.setListManagementServiceClient(mock(HandlerInput.class));
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void setHandlerInput() throws NoSuchFieldException, SecurityException {
	boolean nullPointer = false;
	EinkaufslisteService implementation = EinkaufslisteService.getImplementation();
	HandlerUtilService handlerUtilService = mock(HandlerUtilService.class);
	FieldSetter.setField(implementation, implementation.getClass().getDeclaredField("handlerUtilService"),
		handlerUtilService);
	try {
	    implementation.setListManagementServiceClient(null);
	} catch (Exception e) {
	    nullPointer = true;
	}
	assertFalse(nullPointer);
	boolean erfolg = true;
	try {
	    implementation.setListManagementServiceClient(mock(HandlerInput.class));
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    @Test
    void getImplementation() {
	assertNotNull(EinkaufslisteService.getImplementation());
    }
}
