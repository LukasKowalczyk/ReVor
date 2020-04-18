package de.revor;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

import de.revor.handler.CancelandStopIntentHandler;
import de.revor.handler.HelpIntentHandler;
import de.revor.handler.LaunchRequestHandler;
import de.revor.handler.NaechstesRezeptHandler;
import de.revor.handler.RezeptAusgewaehltHandler;
import de.revor.handler.SessionEndedRequestHandler;
import de.revor.handler.RezeptSucheHandler;

public class RezeptVorschlag extends SkillStreamHandler {

	private static final String AMAZON_ID = "AMAZON_ID";

	public static final String SKILL_TITEL = "ReVor";

	@SuppressWarnings("unchecked")
	private static Skill getSkill() {
		return Skills.standard()
				.addRequestHandlers(new CancelandStopIntentHandler(), new RezeptAusgewaehltHandler(),
						new NaechstesRezeptHandler(), new RezeptSucheHandler(), new HelpIntentHandler(),
						new LaunchRequestHandler(), new SessionEndedRequestHandler())
				.withSkillId(System.getenv(AMAZON_ID)).build();
	}

	public RezeptVorschlag() {
		super(getSkill());
	}
}
