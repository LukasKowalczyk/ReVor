package de.revor.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.amazon.ask.model.services.listManagement.AlexaList;
import com.amazon.ask.model.services.listManagement.AlexaListItem;
import com.amazon.ask.model.services.listManagement.AlexaListMetadata;
import com.amazon.ask.model.services.listManagement.AlexaListsMetadata;
import com.amazon.ask.model.services.listManagement.ListManagementServiceClient;

import de.revor.datatype.Zutat;

class EinkaufslisteServiceTest {

    @Test
    void fuegeZurEinkaufslisteHinzu() {
	EinkaufslisteService implementation = EinkaufslisteService.getImplementation();

	boolean erfolg = true;
	try {
	    implementation.setListManagementServiceClient(null);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    ListManagementServiceClient listManagementServiceClientFilled = mockGetListGefuellt();
	    implementation.setListManagementServiceClient(listManagementServiceClientFilled);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    ListManagementServiceClient listManagementServiceClientIDNull = mockGetListGefuelltIDNull();
	    implementation.setListManagementServiceClient(listManagementServiceClientIDNull);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    ListManagementServiceClient listManagementServiceClientLeer = mockGetListLeer();
	    implementation.setListManagementServiceClient(listManagementServiceClientLeer);
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
	    ListManagementServiceClient listManagementServiceClient = mockShoppingIDGefuellt();
	    EinkaufslisteService implementation = EinkaufslisteService.getImplementation();
	    implementation.setListManagementServiceClient(listManagementServiceClient);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());

	    ListManagementServiceClient listManagementServiceClientNull = mockShoppingIDNull();
	    implementation.setListManagementServiceClient(listManagementServiceClientNull);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());

	    ListManagementServiceClient listManagementServiceClientLeer = mockShoppingIDLeer();
	    implementation.setListManagementServiceClient(listManagementServiceClientLeer);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	} catch (Exception e) {
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    private ListManagementServiceClient mockGetListGefuellt() {
	ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder()
		.addListsItem(AlexaListMetadata.builder().withName("Alexa shopping list").withListId("123").build())
		.build());
	List<AlexaListItem> items = new ArrayList<AlexaListItem>();
	items.add(AlexaListItem.builder().withValue("test 100 g").build());
	when(listManagementServiceClient.getList(anyString(), anyString()))
		.thenReturn(AlexaList.builder().withItems(items).build());
	return listManagementServiceClient;
    }

    private ListManagementServiceClient mockGetListGefuelltIDNull() {
	ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder()
		.addListsItem(AlexaListMetadata.builder().withName("Alexa shopping list").withListId("123").build())
		.build());
	when(listManagementServiceClient.getListsMetadata()).thenReturn(null);
	List<AlexaListItem> items = new ArrayList<AlexaListItem>();
	items.add(AlexaListItem.builder().withValue("test 100 g").build());
	when(listManagementServiceClient.getList(anyString(), anyString()))
		.thenReturn(AlexaList.builder().withItems(items).build());
	return listManagementServiceClient;
    }

    private ListManagementServiceClient mockGetListLeer() {
	ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder()
		.addListsItem(AlexaListMetadata.builder().withName("Alexa shopping list").withListId("123").build())
		.build());
	List<AlexaListItem> items = new ArrayList<AlexaListItem>();
	when(listManagementServiceClient.getList(anyString(), anyString()))
		.thenReturn(AlexaList.builder().withItems(items).build());
	return listManagementServiceClient;
    }

    private ListManagementServiceClient mockShoppingIDNull() {
	ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder()
		.addListsItem(AlexaListMetadata.builder().withName("Alexa shopping list").withListId("123").build())
		.build());
	when(listManagementServiceClient.getListsMetadata()).thenReturn(null);
	return listManagementServiceClient;
    }

    private ListManagementServiceClient mockShoppingIDGefuellt() {
	ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder()
		.addListsItem(AlexaListMetadata.builder().withName("Alexa shopping list").withListId("123").build())
		.build());
	return listManagementServiceClient;
    }

    private ListManagementServiceClient mockShoppingIDLeer() {
	ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder()
		.addListsItem(AlexaListMetadata.builder().withName("Alexa shopping list").withListId("123").build())
		.build());
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder().build());
	return listManagementServiceClient;
    }

    @Test
    void setHandlerInput() {
	boolean nullPointer = false;
	try {
	    EinkaufslisteService.getImplementation().setListManagementServiceClient(null);
	} catch (Exception e) {
	    nullPointer = true;
	}
	assertFalse(nullPointer);
	boolean erfolg = true;
	try {
	    ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	    EinkaufslisteService.getImplementation().setListManagementServiceClient(listManagementServiceClient);
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
