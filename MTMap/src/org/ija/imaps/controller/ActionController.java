package org.ija.imaps.controller;

import java.util.Arrays;
import java.util.List;

import org.ija.imaps.model.Action;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.Protocol;
import org.ija.tools.SoundPlayer;
import org.ija.tools.media.MP3Player;
import org.ija.tools.tts.SAPI5Player;

public class ActionController {

	private static List<SoundPlayer> players;

	public static void registerSoundPlayers(SoundPlayer... players) {
		ActionController.players = Arrays.asList(players);
	}

	private static void stopSoundPlayers() {
		for (SoundPlayer soundPlayer : players) {
			soundPlayer.stop();
		}
	}

	public static void launch(Action action) {

		// Try to find something to do
		if (action != null) {
			stopSoundPlayers();

			if (action.getProtocol().equals(Protocol.TTS)) {
				SAPI5Player.getInstance().play(action.getValue());
			} else if (action.getProtocol().equals(Protocol.MP3)) {
				MP3Player.getInstance().play(action.getValue());
			}
		}
	}
}
