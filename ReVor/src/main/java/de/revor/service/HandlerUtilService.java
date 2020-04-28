package de.revor.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.services.ServiceClientFactory;
import com.amazon.ask.model.services.directive.DirectiveServiceClient;
import com.amazon.ask.model.services.directive.Header;
import com.amazon.ask.model.services.directive.SendDirectiveRequest;
import com.amazon.ask.model.services.directive.SpeakDirective;
import com.amazon.ask.model.services.listManagement.ListManagementServiceClient;
import com.amazon.ask.model.services.ups.UpsServiceClient;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.amazon.ask.model.slu.entityresolution.Resolutions;
import com.amazon.ask.model.slu.entityresolution.Value;
import com.amazon.ask.model.slu.entityresolution.ValueWrapper;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class HandlerUtilService {

    private static final Logger logger = LoggerFactory.getLogger(HandlerUtilService.class);

    private static HandlerUtilService handlerInputUtilService;

    private HandlerUtilService() {

    }

    public static HandlerUtilService getImpementation() {
	return handlerInputUtilService == null ? new HandlerUtilService() : handlerInputUtilService;
    }

    public Map<String, Object> getSessionAttributes(HandlerInput handlerInput) {
	if (handlerInput == null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	AttributesManager attributesManager = handlerInput.getAttributesManager();
	if (attributesManager == null) {
	    throw new IllegalArgumentException("attributesManager is null");
	}
	return attributesManager.getSessionAttributes();
    }

    public Map<String, Slot> getSlots(HandlerInput handlerInput) {
	if (handlerInput == null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	RequestEnvelope requestEnvelope = handlerInput.getRequestEnvelope();
	if (requestEnvelope == null) {
	    throw new IllegalArgumentException("requestEnvelope is null");
	}
	IntentRequest intentRequest = (IntentRequest) requestEnvelope.getRequest();
	if (intentRequest == null) {
	    throw new IllegalArgumentException("intentRequest is null");
	}
	Intent intent = intentRequest.getIntent();
	if (intent == null) {
	    throw new IllegalArgumentException("intent is null");
	}
	return intent.getSlots();
    }

    public ListManagementServiceClient getListManagementServiceClient(HandlerInput handlerInput) {
	if (handlerInput == null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	ServiceClientFactory serviceClientFactory = handlerInput.getServiceClientFactory();
	if (serviceClientFactory == null) {
	    throw new IllegalArgumentException("serviceClientFactory is null");
	}
	return serviceClientFactory.getListManagementService();
    }

    public AmazonDynamoDB getAmazonDynamoDB() {
	try {
	    return AmazonDynamoDBClientBuilder.standard().build();
	} catch (SdkClientException e) {
	    logger.error("DB-Fehler", e);
	    return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
	}
    }

    public void sendProgressiveResponse(HandlerInput handlerInput, String speechText) {
	if (handlerInput == null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	if (StringUtils.isBlank(speechText)) {
	    throw new IllegalArgumentException("speechText is blank");
	}
	RequestEnvelope requestEnvelope = handlerInput.getRequestEnvelope();
	if (requestEnvelope == null) {
	    throw new IllegalArgumentException("requestEnvelope is null");
	}
	Request request = requestEnvelope.getRequest();
	if (request == null) {
	    throw new IllegalArgumentException("request is null");
	}
	String requestId = request.getRequestId();
	if (StringUtils.isBlank(requestId)) {
	    throw new IllegalArgumentException("requestId is blank");
	}
	ServiceClientFactory serviceClientFactory = handlerInput.getServiceClientFactory();
	if (serviceClientFactory == null) {
	    throw new IllegalArgumentException("serviceClientFactory is null");
	}
	DirectiveServiceClient directiveServiceClient = serviceClientFactory.getDirectiveService();
	if (directiveServiceClient == null) {
	    throw new IllegalArgumentException("directiveServiceClient is null");
	}
	SendDirectiveRequest sendDirectiveRequest = SendDirectiveRequest.builder()
		.withDirective(SpeakDirective.builder().withSpeech(speechText).build())
		.withHeader(Header.builder().withRequestId(requestId).build()).build();
	directiveServiceClient.enqueue(sendDirectiveRequest);
    }

    public String getUserEmail(HandlerInput handlerInput) {
	if (handlerInput == null) {
	    throw new IllegalArgumentException("handlerInput is null");
	}
	ServiceClientFactory serviceClientFactory = handlerInput.getServiceClientFactory();
	if (serviceClientFactory == null) {
	    throw new IllegalArgumentException("serviceClientFactory is null");
	}
	UpsServiceClient upsService = serviceClientFactory.getUpsService();
	if (upsService == null) {
	    throw new IllegalArgumentException("upsService is null");
	}
	return upsService.getProfileEmail();
    }

    public String getMappedNameOfSlot(Slot slot) {
	if (slot == null) {
	    throw new IllegalArgumentException("slot is null");
	}
	Resolutions resolutions = slot.getResolutions();
	if (resolutions == null) {
	    throw new IllegalArgumentException("resolutions is null");
	}
	List<Resolution> resolutionsPerAuthority = resolutions.getResolutionsPerAuthority();
	if (resolutionsPerAuthority == null || resolutionsPerAuthority.size() <= 0) {
	    throw new IllegalArgumentException("resolutionsPerAuthority is empty");
	}
	Resolution resolution = resolutionsPerAuthority.get(0);
	if (resolution == null) {
	    throw new IllegalArgumentException("resolution is null");
	}
	List<ValueWrapper> values = resolution.getValues();
	if (values == null || values.size() <= 0) {
	    throw new IllegalArgumentException("values is null");
	}
	ValueWrapper valueWrapper = values.get(0);
	if (valueWrapper == null) {
	    throw new IllegalArgumentException("valueWrapper is null");
	}
	Value value = valueWrapper.getValue();
	if (value == null) {
	    throw new IllegalArgumentException("value is null");
	}
	return value.getName();
    }

}
