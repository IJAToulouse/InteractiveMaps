package org.ija.tools.media;

import jaco.mp3.player.MP3Player;

import java.io.File;

import org.ija.tools.SoundPlayer;

public final class MusicPlayer implements SoundPlayer {
	
	private MP3Player player;
	
	private MusicPlayer() {
		player = new MP3Player();
	}

	private static class MusicPlayerHolder {
		private final static MusicPlayer instance = new MusicPlayer();
	}

	public static MusicPlayer getInstance() {
		return MusicPlayerHolder.instance;
	}

	public void play(File mp3) {

		if (this.isBusy()) {
			player.stop();
		}
		player = new MP3Player(mp3);
		player.play();
	}

	public boolean isBusy() {
		return (player != null && !player.isStopped());
	}

	public void stop() {
		if (!player.isStopped()) {
			player.stop();
		}
	}

	public static void main(String[] args) {

		MusicPlayer test = new MusicPlayer();
		test.play(new File("route.mp3"));

		while (test.isBusy()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		test.play(new File("route.mp3"));

		while (test.isBusy()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}