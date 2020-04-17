package de.revor.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import de.revor.datatype.Mahlzeit;
import de.revor.datatype.Rezept;
import de.revor.datatype.Schweregrad;

class RezeptSucheServiceTest {

    @Test
    void setAmazonDynamoDB() {
	RezeptSucheService rezeptSucheService = RezeptSucheService.getImplementation();
	rezeptSucheService.setAmazonDynamoDB(mock(AmazonDynamoDB.class));
	boolean nullPointer = false;
	try {
	    rezeptSucheService.setAmazonDynamoDB(null);
	} catch (NullPointerException e) {
	    nullPointer = true;
	}
	assertTrue(nullPointer);

    }

    @Test
    void findeRezepte() {
	RezeptSucheService rezeptSucheService = RezeptSucheService.getImplementation();
	rezeptSucheService.setAmazonDynamoDB(mock(AmazonDynamoDB.class));

	boolean illegalArgumentException = false;
	try {
	    rezeptSucheService.findeRezepte(null, null);
	} catch (IllegalArgumentException e) {
	    illegalArgumentException = true;
	}
	assertTrue(illegalArgumentException);

	for (Mahlzeit mahlzeit : Mahlzeit.values()) {
	    boolean exception = false;
	    try {
		rezeptSucheService.findeRezepte(mahlzeit, null);
	    } catch (IllegalArgumentException e) {
		exception = true;
	    }
	    assertTrue(exception);
	}

	for (Schweregrad schweregrad : Schweregrad.values()) {
	    boolean exception = false;
	    try {
		rezeptSucheService.findeRezepte(null, schweregrad);
	    } catch (IllegalArgumentException e) {
		exception = true;
	    }
	    assertTrue(exception);
	}

	DynamoDBMapper dynamoDBMapper = mock(DynamoDBMapper.class);
	//TODO: hier muss die rückgabe etwas besser werden.
	when(dynamoDBMapper.scan(eq(Rezept.class), any(DynamoDBScanExpression.class))).thenReturn(null);
	Whitebox.setInternalState(rezeptSucheService, "dynamoDBMapper", dynamoDBMapper);
	for (Mahlzeit mahlzeit : Mahlzeit.values()) {
	    for (Schweregrad schweregrad : Schweregrad.values()) {
		boolean erfolg = true;
		try {
		    rezeptSucheService.findeRezepte(mahlzeit, schweregrad);
		} catch (Exception e) {
		    erfolg = false;
		}
		assertTrue(erfolg);
	    }

	}

	DynamoDBMapper dynamoDBMapperNull = mock(DynamoDBMapper.class);
	when(dynamoDBMapperNull.scan(eq(Rezept.class), any(DynamoDBScanExpression.class))).thenReturn(null);
	Whitebox.setInternalState(rezeptSucheService, "dynamoDBMapper", dynamoDBMapperNull);
	for (Mahlzeit mahlzeit : Mahlzeit.values()) {
	    for (Schweregrad schweregrad : Schweregrad.values()) {
		assertNull(rezeptSucheService.findeRezepte(mahlzeit, schweregrad));
	    }

	}
    }

}
