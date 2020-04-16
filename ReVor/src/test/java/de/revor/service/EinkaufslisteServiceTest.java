package de.revor.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.amazon.ask.model.services.listManagement.ListState;

import de.revor.datatype.Zutat;

class EinkaufslisteServiceTest {

    @Test
    void fuegeZurEinkaufslisteHinzu() {
	EinkaufslisteService implementation = EinkaufslisteService.getImplementation();
	ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	boolean erfolg = true;
	try {
	    mockShoppingIDGefuellt(listManagementServiceClient);
	    mockGetListGefuellt(listManagementServiceClient);
	    implementation.setListManagementServiceClient(listManagementServiceClient);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    mockShoppingIDNull(listManagementServiceClient);
	    mockGetListGefuellt(listManagementServiceClient);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());

	    mockGetListLeer(listManagementServiceClient);
	    mockGetListGefuellt(listManagementServiceClient);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	    implementation.fuegeZurEinkaufslisteHinzu(generateNewZutaten());
	    implementation.fuegeZurEinkaufslisteHinzu(generateUpdateZutaten());
	    
	} catch (Exception e) {
	    e.printStackTrace();
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
	    ListManagementServiceClient listManagementServiceClient = mock(ListManagementServiceClient.class);
	    mockShoppingIDGefuellt(listManagementServiceClient);
	    EinkaufslisteService implementation = EinkaufslisteService.getImplementation();
	    implementation.setListManagementServiceClient(listManagementServiceClient);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());

	    mockShoppingIDNull(listManagementServiceClient);
	    implementation.setListManagementServiceClient(listManagementServiceClient);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());

	    mockShoppingIDLeer(listManagementServiceClient);
	    implementation.setListManagementServiceClient(listManagementServiceClient);
	    implementation.fuegeZurEinkaufslisteHinzu(null);
	    implementation.fuegeZurEinkaufslisteHinzu(new ArrayList<>());
	} catch (Exception e) {
	    e.printStackTrace();
	    erfolg = false;
	}
	assertTrue(erfolg);
    }

    private void mockShoppingIDGefuellt(ListManagementServiceClient listManagementServiceClient) {
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder()
		.addListsItem(AlexaListMetadata.builder().withName("Alexa shopping list").withListId("123").build())
		.build());
    }

    private void mockGetListGefuellt(ListManagementServiceClient listManagementServiceClient) {
	List<AlexaListItem> items = new ArrayList<AlexaListItem>();
	items.add(AlexaListItem.builder().withValue("test 100 g").build());
	when(listManagementServiceClient.getList("123", ListState.ACTIVE.getValue().toString())).thenReturn(AlexaList.builder().withItems(items).build());
    }

    private void mockGetListLeer(ListManagementServiceClient listManagementServiceClient) {
	List<AlexaListItem> items = new ArrayList<AlexaListItem>();
	when(listManagementServiceClient.getList("123", ListState.ACTIVE.getValue().toString())).thenReturn(AlexaList.builder().withItems(items).build());
    }

    private void mockShoppingIDNull(ListManagementServiceClient listManagementServiceClient) {
	when(listManagementServiceClient.getListsMetadata()).thenReturn(null);
    }

    private void mockShoppingIDLeer(ListManagementServiceClient listManagementServiceClient) {
	when(listManagementServiceClient.getListsMetadata()).thenReturn(AlexaListsMetadata.builder().build());
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
