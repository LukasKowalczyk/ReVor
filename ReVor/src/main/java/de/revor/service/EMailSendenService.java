package de.revor.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import de.revor.datatype.Rezept;
import de.revor.datatype.Zutat;

public class EMailSendenService {
    private static final String FROM_EMAIL = "FROM_EMAIL";
    
    private static EMailSendenService eMailSendenService;
    
    private static final Logger logger = LoggerFactory.getLogger(EMailSendenService.class);
    
    private AmazonSimpleEmailService client;

    private EMailSendenService() {
	client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
    }

    public static EMailSendenService getImpementation() {
	return eMailSendenService == null ? new EMailSendenService() : eMailSendenService;
    }

    public void versendeRezeptUndEinkaufsliste(Rezept rezept, List<Zutat> zutatenliste, String userEmail) {
	logger.debug("Sende Email");
	logger.debug("To=" + userEmail);
	logger.debug("Anleitung:");
	logger.debug(rezept.getAnleitung());
	logger.debug("");
	String einkaufslisteBody = generateEinkaufsListe(zutatenliste);
	logger.debug(einkaufslisteBody);
	SendEmailRequest request = new SendEmailRequest()
		.withDestination(new Destination().withToAddresses(userEmail)).withMessage(new Message()
			.withBody(generateBody(rezept, einkaufslisteBody)).withSubject(generateSubject(rezept)))
		.withSource(System.getenv(FROM_EMAIL));
	client.sendEmail(request);
    }

    private Content generateSubject(Rezept rezept) {
	return new Content().withCharset("UTF-8").withData("Einkaufsliste und Rezept für " + rezept.getTitel());
    }

    private Body generateBody(Rezept rezept, String einkaufslisteBody) {
	return new Body().withHtml(new Content().withCharset("UTF-8")
		.withData("<html><body>" + einkaufslisteBody + "<hr>" + rezept.getAnleitung() + "<body></html>"));
    }

    private String generateEinkaufsListe(List<Zutat> einkaufsliste) {
	StringBuffer sb = new StringBuffer();
	for (Zutat zutat : einkaufsliste) {
	    sb.append(zutat.getEinkaufsItemValue()).append("<br>");
	}
	return sb.toString();
    }
}
