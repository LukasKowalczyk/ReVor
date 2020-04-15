package de.revor.datatype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ReVorRezepte")
public class Rezept {

    private static final String TITEL_KEY_NAME = "titel";

    private static final String ZUTATEN_KEY_NAME = "zutaten";

    private static final String SCHWEREGRAD_KEY_NAME = "schweregrad";

    private static final String ANLEITUNG_KEY_NAME = "anleitung";

    private static final String MAHLZEIT_KEY_NAME = "mahlzeit";

    private static final String ID_KEY_NAME = "ID";

    private Integer id;

    private String titel;

    private String mahlzeit;

    private String anleitung;

    private String schweregrad;

    private List<Zutat> zutaten;

    @DynamoDBHashKey(attributeName = ID_KEY_NAME)
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @DynamoDBHashKey(attributeName = TITEL_KEY_NAME)
    public String getTitel() {
	return titel;
    }

    public void setTitel(String titel) {
	this.titel = titel;
    }

    @DynamoDBAttribute(attributeName = MAHLZEIT_KEY_NAME)
    public String getMahlzeit() {
	return mahlzeit;
    }

    public void setMahlzeit(String mahlzeit) {
	this.mahlzeit = mahlzeit;
    }

    @DynamoDBAttribute(attributeName = ANLEITUNG_KEY_NAME)
    public String getAnleitung() {
	return anleitung;
    }

    public void setAnleitung(String anleitung) {
	this.anleitung = anleitung;
    }

    @DynamoDBAttribute(attributeName = SCHWEREGRAD_KEY_NAME)
    public String getSchweregrad() {
	return schweregrad;
    }

    public void setSchweregrad(String schweregrad) {
	this.schweregrad = schweregrad;
    }

    @DynamoDBAttribute(attributeName = ZUTATEN_KEY_NAME)
    public List<Zutat> getZutaten() {
	return zutaten;
    }

    public void setZutaten(List<Zutat> zutaten) {
	this.zutaten = zutaten;
    }

    @Override
    public String toString() {
	return "Rezept [id=" + id + ", titel=" + titel + ", mahlzeit=" + mahlzeit + ", anleitung=" + anleitung
		+ ", schweregrad=" + schweregrad + ", zutaten=" + zutaten + "]";
    }

    public static Rezept mappeFromMap(Map<String, Object> input) {
	Rezept rezept = new Rezept();
	if (input != null) {
	    Object anleitung = input.get(ANLEITUNG_KEY_NAME);
	    if (anleitung != null) {
		rezept.setAnleitung(anleitung.toString());
	    }

	    Object titel = input.get(TITEL_KEY_NAME);
	    if (titel != null) {
		rezept.setTitel(titel.toString());
	    }

	    Object id = input.get(ID_KEY_NAME);
	    if (id != null) {
		rezept.setId(Integer.parseInt(id.toString()));
	    }

	    Object mahlzeit = input.get(MAHLZEIT_KEY_NAME);
	    if (mahlzeit != null) {
		rezept.setMahlzeit(mahlzeit.toString());
	    }

	    Object schweregrad = input.get(SCHWEREGRAD_KEY_NAME);
	    if (schweregrad != null) {
		rezept.setSchweregrad(schweregrad.toString());
	    }

	    @SuppressWarnings("unchecked")
	    List<Map<String, Object>> inputZutaten = (List<Map<String, Object>>) input.get(ZUTATEN_KEY_NAME);
	    if (inputZutaten != null) {
		ArrayList<Zutat> zutaten = new ArrayList<Zutat>();
		for (Map<String, Object> iZutat : inputZutaten) {
		    zutaten.add(Zutat.mappeFromMap(iZutat));
		}
		rezept.setZutaten(zutaten);
	    }
	}
	return rezept;
    }

    public List<Zutat> getEinkaufsliste(int anzahlPortionen) {
	List<Zutat> einkaufsListe = zutaten;
	if (zutaten != null && anzahlPortionen > 0) {
	    einkaufsListe.forEach(z -> z.setAnzahl(z.getAnzahl() * anzahlPortionen));
	}
	return einkaufsListe;
    }
}
