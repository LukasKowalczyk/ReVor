package de.revor.service;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.services.listManagement.ListManagementServiceClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class HandlerInputUtil {

    public static Map<String, Object> getSessionAttributes(HandlerInput handlerInput) {
	if(handlerInput==null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	return handlerInput.getAttributesManager().getSessionAttributes();
    }

    public static Map<String, Slot> getSlots(HandlerInput handlerInput) {
	if(handlerInput==null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	IntentRequest intentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
	return intentRequest.getIntent().getSlots();
    }
    public static ListManagementServiceClient getListManagementServiceClient(HandlerInput handlerInput) {
	if(handlerInput==null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	return handlerInput.getServiceClientFactory().getListManagementService();
    }

    public static AmazonDynamoDB getAmazonDynamoDB() {
	return  AmazonDynamoDBClientBuilder.standard().build();
    }

}
