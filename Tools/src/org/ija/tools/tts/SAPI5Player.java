package org.ija.tools.tts;

import java.util.Locale;

import javax.speech.Central;
import javax.speech.EngineList;
import javax.speech.EngineModeDesc;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.SynthesizerProperties;
import javax.speech.synthesis.Voice;

import org.ija.tools.SoundPlayer;

public class SAPI5Player implements SoundPlayer {

	private Synthesizer synth = null;
	private static Float ttsSpeed = 190.0f;
	private static Locale locale = Locale.FRANCE;
	private static String voiceName = "";

	/** Constructeur privé */
	private SAPI5Player() {
		try {
			SynthesizerModeDesc desc = getEngine();

			if (desc == null) {
				System.out.println("Unable to find a voice for " + locale + " "
						+ voiceName);
				System.exit(1);
			}

			synth = Central.createSynthesizer(desc);
			synth.allocate();
			synth.resume();
			synth.waitEngineState(Synthesizer.ALLOCATED);

			SynthesizerProperties props = synth.getSynthesizerProperties();
			props.setSpeakingRate(ttsSpeed);
			props.setVoice(desc.getVoices()[0]);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SynthesizerModeDesc getEngine() {
		Voice voice = null;
		EngineList list = Central.availableSynthesizers(null);
		SynthesizerModeDesc desc = null;
		for (int i = 0; voice == null && i < list.size(); i++) {
			desc = (SynthesizerModeDesc) list.elementAt(i);

			if (desc.getLocale().equals(locale)) {
				if ("".equals(voiceName)
						|| desc.getVoices()[0].getName().toLowerCase()
								.contains(voiceName.toLowerCase())) {
					return desc;
				}
			}
		}
		return null;
	}

	/** Holder */
	private static class SAPI5PlayerHolder {
		/** Instance unique non préinitialisée */
		private final static SAPI5Player instance = new SAPI5Player();
	}

	/** Point d'accès pour l'instance unique du singleton */
	public static SAPI5Player getInstance() {
		return SAPI5PlayerHolder.instance;
	}

	public void play(String text) {
		synth.cancelAll();
		synth.getSynthesizerProperties().setSpeakingRate(ttsSpeed);
		synth.speakPlainText(text, null);
	}

	public void speakIfReady(String text) {
		if (!synth.enumerateQueue().hasMoreElements()) {
			synth.getSynthesizerProperties().setSpeakingRate(ttsSpeed);
			synth.speakPlainText(text, null);
		}
	}

	@Override
	public void stop() {
		synth.cancelAll();
	}

	private void test(String string) {
		System.out.println(synth.enumerateQueue().hasMoreElements());

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	public static void setTTSSpeed(String speed) {
		if (speed != null) {
			ttsSpeed = Float.valueOf(speed);
		}
	}

	public static void main(String args[]) {

		SAPI5Player.setTTSSpeed("200.0f");
		SAPI5Player.setLocale("en-US");
		//SAPI5Player.setVoiceName("hortense");
		SAPI5Player.getInstance().play("Ceci est un test.");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SAPI5Player.setTTSSpeed("250.0f");
		//SAPI5Player.getInstance().play("Ceci est un test.");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);

	}

	public static void setLocale(String strLocale) {
		locale = Locale.forLanguageTag(strLocale);
	}

	public static void setVoiceName(String name) {
		voiceName = name;
	}

}
