package de.revor.datatype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ReVorRezepte")
public class Rezept {
    private Integer id;
    private String titel;
    private String mahlzeit;
    private String anleitung;
    private String schweregrad;
    private List<Zutat> zutaten;

    @DynamoDBHashKey(attributeName = "ID")
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getTitel() {
	return titel;
    }

    public void setTitel(String titel) {
	this.titel = titel;
    }

    @DynamoDBAttribute(attributeName = "mahlzeit")
    public String getMahlzeit() {
	return mahlzeit;
    }

    public void setMahlzeit(String mahlzeit) {
	this.mahlzeit = mahlzeit;
    }

    @DynamoDBAttribute(attributeName = "anleitung")
    public String getAnleitung() {
	return anleitung;
    }

    public void setAnleitung(String anleitung) {
	this.anleitung = anleitung;
    }

    @DynamoDBAttribute(attributeName = "schweregrad")
    public String getSchweregrad() {
	return schweregrad;
    }

    public void setSchweregrad(String schweregrad) {
	this.schweregrad = schweregrad;
    }

    @DynamoDBAttribute(attributeName = "zutaten")
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
	rezept.setAnleitung(input.get("anleitung").toString());
	rezept.setTitel(input.get("titel").toString());
	rezept.setId((Integer) input.get("id"));
	rezept.setMahlzeit(input.get("mahlzeit").toString());
	rezept.setSchweregrad(input.get("schweregrad").toString());
	@SuppressWarnings("unchecked")
	List<Map<String, Object>> inputZutaten = (List<Map<String, Object>>) input.get("zutaten");
	ArrayList<Zutat> zutaten = new ArrayList<Zutat>();
	for (Map<String, Object> iZutat : inputZutaten) {
	    zutaten.add(Zutat.mappeFromMap(iZutat));
	}
	rezept.setZutaten(zutaten);
	return rezept;
    }

    public List<Zutat> getEinkaufsliste(int anzahlPortionen) {
	List<Zutat> einkaufsListe = zutaten;
	einkaufsListe.forEach(z -> z.setAnzahl(z.getAnzahl() * anzahlPortionen));
	return einkaufsListe;
    }
}
