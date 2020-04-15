package de.revor.datatype;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Zutat {

    private static final String NAME_KEY_NAME = "name";

    private static final String ANZAHL_KEY_NAME = "anzahl";

    private static final String EINHEIT_KEY_NAME = "einheit";

    private int anzahl;

    private String name;

    private String einheit;

    @DynamoDBAttribute(attributeName = ANZAHL_KEY_NAME)
    public int getAnzahl() {
	return anzahl;
    }

    public void setAnzahl(int anzahl) {
	this.anzahl = anzahl;
    }

    @DynamoDBAttribute(attributeName = NAME_KEY_NAME)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @DynamoDBAttribute(attributeName = EINHEIT_KEY_NAME)
    public String getEinheit() {
	return einheit;
    }

    public void setEinheit(String einheit) {
	this.einheit = einheit;
    }

    /**
     * Es wird in der inputMap nach den Keys "anzahl", "name" und "einheit" gesucht.
     * <br>
     * Die werte werden dann aus der inputMap ins Object übernommen.
     * 
     * @param inputMap
     * @return  Es wird immer ein initialisiertes Object einer Zutat zurück gegeben 
     */
    public static Zutat mappeFromMap(Map<String, Object> inputMap) {
	Zutat zutat = new Zutat();
	if (inputMap != null) {
	    Object anzahl = inputMap.get(ANZAHL_KEY_NAME);
	    if (anzahl != null) {
		zutat.setAnzahl(Integer.parseInt(anzahl.toString()));
	    }
	    Object name = inputMap.get(NAME_KEY_NAME);
	    if (name != null) {
		zutat.setName(name.toString());
	    }
	    Object einheit = inputMap.get(EINHEIT_KEY_NAME);
	    if (einheit != null) {
		zutat.setEinheit(einheit.toString());
	    }
	}
	return zutat;
    }

    @Override
    public String toString() {
	return "Zutat [anzahl=" + anzahl + ", name=" + name + ", einheit=" + einheit + "]";
    }

    /**
     * @return Gibt einen aufbereitet String im pattern "{einheit} {anzahl}
     *         {einheit}" zurück.
     */
    public String getEinkaufsItemValue() {
	return name + " " + anzahl + " " + einheit;
    }
}
