package de.revor.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

import de.revor.datatype.Rezept;
import de.revor.datatype.Zutat;

class EMailSendenServiceTest {

    @Test
    void versendeRezeptUndEinkaufslisteRezeptGefuellt() {
	EMailSendenService eMailSendenService = EMailSendenService.getImpementation();
	boolean exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), null, null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), new ArrayList<Zutat>(), null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), new ArrayList<Zutat>(), "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), null, "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), new ArrayList<Zutat>(),
		    "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), null, "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), generateZutaten(), null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), generateZutaten(), "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);
	
	//Positive RCs
	for (int i = 100; i <= 200; i++) {
	    boolean erfolg = true;
	    Integer rc = i;
	    try {
		AmazonSimpleEmailService amazonSimpleEmailService = mock(AmazonSimpleEmailService.class);
		SendEmailResult sendEmailResult = mock(SendEmailResult.class);
		SdkHttpMetadata sdkHttpMetadata = mock(SdkHttpMetadata.class);
		when(sdkHttpMetadata.getHttpStatusCode()).thenReturn(rc);
		when(sendEmailResult.getSdkHttpMetadata()).thenReturn(sdkHttpMetadata);
		when(amazonSimpleEmailService.sendEmail(any(SendEmailRequest.class))).thenReturn(sendEmailResult);
		Whitebox.setInternalState(eMailSendenService, "amazonSimpleEmailService", amazonSimpleEmailService);
		eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), generateZutaten(), "test@test.com");
	    } catch (Exception e) {
		erfolg = false;
	    }
	    assertTrue(erfolg);
	}
	
	//Negative RCs
	for (int i = 300; i <= 500; i++) {
	 exception = false;
	    Integer rc = i;
	try {
	    AmazonSimpleEmailService amazonSimpleEmailService = mock(AmazonSimpleEmailService.class);
	    SendEmailResult sendEmailResult = mock(SendEmailResult.class);
	    SdkHttpMetadata sdkHttpMetadata = mock(SdkHttpMetadata.class);
	    when(sdkHttpMetadata.getHttpStatusCode()).thenReturn(rc);
	    when(sendEmailResult.getSdkHttpMetadata()).thenReturn(sdkHttpMetadata);
	    when(amazonSimpleEmailService.sendEmail(any(SendEmailRequest.class))).thenReturn(sendEmailResult);
	    Whitebox.setInternalState(eMailSendenService, "amazonSimpleEmailService", amazonSimpleEmailService);
	    eMailSendenService.versendeRezeptUndEinkaufsliste(generateRezept(), generateZutaten(), "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);
	}
    }



    @Test
    void versendeRezeptUndEinkaufslisteRezeptLeer() {
	EMailSendenService eMailSendenService = EMailSendenService.getImpementation();
	boolean exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), null, null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), new ArrayList<Zutat>(), null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), new ArrayList<Zutat>(), "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), null, "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), new ArrayList<Zutat>(), "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), null, "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), generateZutaten(), null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), generateZutaten(), "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(new Rezept(), generateZutaten(), "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);
    }

    @Test
    void versendeRezeptUndEinkaufslisteRezeptNull() {
	EMailSendenService eMailSendenService = EMailSendenService.getImpementation();
	boolean exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, null, null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, new ArrayList<Zutat>(), null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, new ArrayList<Zutat>(), "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, null, "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, new ArrayList<Zutat>(), "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, null, "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, generateZutaten(), null);
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, generateZutaten(), "");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);

	exception = false;
	try {
	    eMailSendenService.versendeRezeptUndEinkaufsliste(null, generateZutaten(), "test@test.com");
	} catch (Exception e) {
	    exception = true;
	}
	assertTrue(exception);
    }

    private Rezept generateRezept() {
	Rezept r = new Rezept();
	r.setAnleitung("anleitung");
	r.setId(1);
	r.setMahlzeit("mittag");
	r.setSchweregrad("einfach");
	r.setTitel("titel");
	r.setZutaten(generateZutaten());
	return r;
    }

    private List<Zutat> generateZutaten() {
	ArrayList<Zutat> zutaten = new ArrayList<>();
	Zutat z = new Zutat();
	z.setAnzahl(0);
	z.setEinheit("g");
	z.setName("test");
	zutaten.add(z);
	return zutaten;
    }

}
