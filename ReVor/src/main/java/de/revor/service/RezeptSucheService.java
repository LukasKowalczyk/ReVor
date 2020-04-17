package de.revor.service;

import static de.revor.datatype.Mahlzeit.JETZT;
import static de.revor.datatype.Schweregrad.EGAL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import de.revor.datatype.Mahlzeit;
import de.revor.datatype.Rezept;
import de.revor.datatype.Schweregrad;

public class RezeptSucheService {

    private static RezeptSucheService rezeptSucheService = null;

    private static final Logger logger = LoggerFactory.getLogger(RezeptSucheService.class);

    private DynamoDBMapper dynamoDBMapper;

    private RezeptSucheService() {
	dynamoDBMapper = null;
    }

    public static RezeptSucheService getImplementation() {
	return rezeptSucheService == null ? new RezeptSucheService() : rezeptSucheService;
    }

    public void setAmazonDynamoDB(AmazonDynamoDB amazonDynamoDB) {
	if (amazonDynamoDB == null) {
	    throw new NullPointerException();
	}
	dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    public List<Rezept> findeRezepte(Mahlzeit mahlzeit, Schweregrad schweregrad) {
	if (mahlzeit == null) {
	    throw new IllegalArgumentException("mahlzeit is null");
	}
	if (schweregrad == null) {
	    throw new IllegalArgumentException("schweregrad is null");
	}
	logger.debug("Suche parameter mahlzeit=" + mahlzeit.getWert() + " und schweregrad=" + schweregrad.getWert());
	DynamoDBScanExpression scanExpression = null;
	if (mahlzeit == JETZT) {
	    logger.debug("Es wird die aktuelle Mahlzeit ermittelt!");
	    mahlzeit = Mahlzeit.ermittleAktuelleMahlzeit();
	}
	if (schweregrad == EGAL) {
	    logger.debug("Es wird ohne Schweregradgesucht");
	    scanExpression = generateScanMahlzeit(mahlzeit);
	} else {
	    scanExpression = generateScanMahlzeitAndSchweregrad(mahlzeit, schweregrad);
	}
	logger.debug("Es wird mit mahlzeit=" + mahlzeit.getWert() + " und schweregrad=" + schweregrad.getWert()
		+ " gesucht.");
	List<Rezept> ergebnis = dynamoDBMapper.scan(Rezept.class, scanExpression);
	logger.debug("SuchErgebnisse");
	if (ergebnis != null) {
	    ergebnis.forEach(r -> logger.debug(r.toString()));
	}
	return ergebnis;
    }

    private DynamoDBScanExpression generateScanMahlzeitAndSchweregrad(Mahlzeit mahlzeit, Schweregrad schweregrad) {
	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	eav.put(":zeit", new AttributeValue().withS(mahlzeit.getWert()));
	eav.put(":grad", new AttributeValue().withS(schweregrad.getWert()));
	return new DynamoDBScanExpression().withFilterExpression("mahlzeit = :zeit and schweregrad = :grad")
		.withExpressionAttributeValues(eav);
    }

    private DynamoDBScanExpression generateScanMahlzeit(Mahlzeit mahlzeit) {
	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	eav.put(":zeit", new AttributeValue().withS(mahlzeit.getWert()));
	return new DynamoDBScanExpression().withFilterExpression("mahlzeit = :zeit").withExpressionAttributeValues(eav);
    }
}
