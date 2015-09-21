package org.ija.tools.tts;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineList;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.SynthesizerProperties;
import javax.speech.synthesis.Voice;

import org.ija.tools.SoundPlayer;

public class SAPI5Player implements SoundPlayer {

	private Synthesizer synth = null;
	private static Float ttsSpeed = 190.0f;

	/** Constructeur privé */
	private SAPI5Player() {
		try {
			Voice voice = null;
			EngineList list = Central.availableSynthesizers(null);
			SynthesizerModeDesc desc = null;
			for (int i = 0; voice == null && i < list.size(); i++) {
				desc = (SynthesizerModeDesc) list.elementAt(i);
				if (desc.getModeName().equals("Microsoft SAPI5")) {
					voice = desc.getVoices()[0];
				}
			}

			if (voice == null) {
				System.out.println("Unable to find a SAPI5 voice - quitting");
				System.exit(0);
			}

			synth = Central.createSynthesizer(desc);
			synth.allocate();
			synth.resume();
			synth.waitEngineState(Synthesizer.ALLOCATED);

			SynthesizerProperties props = synth.getSynthesizerProperties();
			props.setSpeakingRate(ttsSpeed);
			props.setVoice(voice);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static void main(String args[]) throws AudioException,
			EngineStateError, InterruptedException {

		Voice voice = null;
		Voice voice2 = new Voice();
		EngineList list = Central.availableSynthesizers(null);
		SynthesizerModeDesc desc = null;
		for (int i = 0; voice == null && i < list.size(); i++) {
			desc = (SynthesizerModeDesc) list.elementAt(i);
			System.out.println(desc.getModeName());
			System.out.println(desc.getEngineName());
			for (Voice vo : desc.getVoices()) {
				System.out.println(vo);
			}
		}
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
}
