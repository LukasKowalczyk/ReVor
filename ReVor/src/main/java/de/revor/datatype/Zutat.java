package de.revor.datatype;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Zutat {
    @Override
    public String toString() {
	return "Zutat [anzahl=" + anzahl + ", name=" + name + ", einheit=" + einheit + "]";
    }

    private int anzahl;
    private String name;
    private String einheit;

    @DynamoDBAttribute(attributeName = "anzahl")
    public int getAnzahl() {
	return anzahl;
    }

    public void setAnzahl(int anzahl) {
	this.anzahl = anzahl;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @DynamoDBAttribute(attributeName = "einheit")
    public String getEinheit() {
	return einheit;
    }

    public void setEinheit(String einheit) {
	this.einheit = einheit;
    }

    public static Zutat mappeFromMap(Map<String, Object> input) {
	Zutat zutat = new Zutat();
	zutat.setAnzahl(Integer.parseInt(input.get("anzahl").toString()));
	zutat.setName(input.get("name").toString());
	zutat.setEinheit(input.get("einheit").toString());
	return zutat;
    }

    public String getEinkaufsItemValue() {
	return name + " " + anzahl + " " + einheit ;
    }
}
