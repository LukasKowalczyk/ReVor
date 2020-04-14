package de.revor.service;

import static de.revor.datatype.Schweregrad.EGAL;
import static de.revor.datatype.Mahlzeit.JETZT;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import de.revor.datatype.Mahlzeit;
import de.revor.datatype.Rezept;
import de.revor.datatype.Schweregrad;

public class RezeptSucheService {
    
    private static RezeptSucheService rezeptSucheService = null;
    
    private static final Logger logger = LoggerFactory.getLogger(RezeptSucheService.class);
    
    private DynamoDBMapper mapper;

    private RezeptSucheService() {
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	mapper = new DynamoDBMapper(client);
    }

    public static RezeptSucheService getImplementation() {
	return rezeptSucheService == null ? new RezeptSucheService() : rezeptSucheService;
    }

    public List<Rezept> findeRezepte(Mahlzeit mahlzeit, Schweregrad schweregrad) {
	logger.debug("Suche parameter mahlzeit=" + mahlzeit.getWert() + " und schweregrad=" + schweregrad.getWert());
	DynamoDBScanExpression scanExpression = null;
	if (mahlzeit == JETZT) {
	    mahlzeit = Mahlzeit.ermittleAktuelleMahlzeit();
	}
	if (schweregrad == EGAL) {
	    scanExpression = generateScanMahlzeit(mahlzeit);
	} else {
	    scanExpression = generateScanMahlzeitAndSchweregrad(mahlzeit, schweregrad);
	}
	logger.debug("Es wird mit mahlzeit=" + mahlzeit.getWert() + " und schweregrad=" + schweregrad.getWert()+ " gesucht.");
	List<Rezept> ergebnis = mapper.scan(Rezept.class, scanExpression);
	logger.debug("SuchErgebnisse");
	ergebnis.forEach(r -> logger.debug(r.toString()));
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
