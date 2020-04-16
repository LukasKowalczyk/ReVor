package de.revor.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.model.services.listManagement.AlexaList;
import com.amazon.ask.model.services.listManagement.AlexaListItem;
import com.amazon.ask.model.services.listManagement.AlexaListMetadata;
import com.amazon.ask.model.services.listManagement.AlexaListsMetadata;
import com.amazon.ask.model.services.listManagement.CreateListItemRequest;
import com.amazon.ask.model.services.listManagement.ListItemState;
import com.amazon.ask.model.services.listManagement.ListManagementServiceClient;
import com.amazon.ask.model.services.listManagement.ListState;

import de.revor.datatype.Zutat;

public class EinkaufslisteService {
    private static final String ALEXA_SHOPPING_LIST_NAME = "Alexa shopping list";

    private static final Logger logger = LoggerFactory.getLogger(EinkaufslisteService.class);

    private static EinkaufslisteService einkaufslisteService;

    private String listId;

    private ListManagementServiceClient listManagementServiceClient;

    private EinkaufslisteService() {
	listId = null;
	listManagementServiceClient = null;
    }

    public void setListManagementServiceClient(ListManagementServiceClient listManagementServiceClient) {
	this.listManagementServiceClient = listManagementServiceClient;
    }

    public static EinkaufslisteService getImplementation() {
	return einkaufslisteService == null ? new EinkaufslisteService() : einkaufslisteService;
    }

    public void fuegeZurEinkaufslisteHinzu(List<Zutat> zutaten) {
	logger.debug("Insert in Alexa-Einkaufsliste");
	if (listManagementServiceClient == null) {
	    return;
	}
	listId = getShoppingListId(listManagementServiceClient);
	if (zutaten == null) {
	    return;
	}
	AlexaList al = listManagementServiceClient.getList(listId, ListState.ACTIVE.getValue().toString());
	zutaten.forEach(z -> {
	    // Update liste
	    AlexaListItem listItemZutat = findZutatInEinkaufsliste(z.getName(), z.getEinheit(), al);
	    if (listItemZutat != null) {
		z.setAnzahl(z.getAnzahl() + parseZutatOfItem(listItemZutat).getAnzahl());
		listManagementServiceClient.deleteListItem(listId, listItemZutat.getId());
	    }
	    listManagementServiceClient.createListItem(listId, CreateListItemRequest.builder()
		    .withStatus(ListItemState.ACTIVE).withValue(z.getEinkaufsItemValue()).build());
	});
    }

    private String getShoppingListId(ListManagementServiceClient listClient) {
	StringBuffer sb = new StringBuffer();
	if (listClient != null) {
	    AlexaListsMetadata listsMetadata = listClient.getListsMetadata();
	    if (listsMetadata != null) {
		List<AlexaListMetadata> lists = listsMetadata.getLists();
		if (lists != null) {
		    lists.forEach(m -> {
			if (m.getName().equals(ALEXA_SHOPPING_LIST_NAME)) {
			    sb.append(m.getListId());
			}
		    });
		}
	    }
	}
	return sb.length() > 0 ? null : sb.toString();
    }

    private Zutat parseZutatOfItem(AlexaListItem alexaListItem) {
	String[] parts = alexaListItem.getValue().split(" ");
	Zutat z = new Zutat();
	z.setName(parts[0]);
	z.setAnzahl(Integer.parseInt(parts[1]));
	z.setEinheit(parts[2]);
	return z;
    }

    private AlexaListItem findZutatInEinkaufsliste(String name, String einheit, AlexaList alexaList) {
	if (alexaList != null) {
	    for (AlexaListItem item : alexaList.getItems()) {
		String value = item.getValue();
		if (value.startsWith(name) && value.endsWith(einheit)) {
		    return item;
		}
	    }
	}
	return null;
    }
}
