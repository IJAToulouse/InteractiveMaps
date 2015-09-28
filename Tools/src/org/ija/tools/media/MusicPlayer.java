package org.ija.tools.media;

import jaco.mp3.player.MP3Player;

import java.io.File;

import org.ija.tools.SoundPlayer;

public final class MusicPlayer implements SoundPlayer {

	private MP3Player player;
	private String systemDirectory;
	private String mapDirectory;

	private MusicPlayer() {
		player = new MP3Player();
	}

	private static class MusicPlayerHolder {
		private final static MusicPlayer instance = new MusicPlayer();
	}

	public static MusicPlayer getInstance() {
		return MusicPlayerHolder.instance;
	}

	public void play(String mp3) {

		File mp3File = new File(mapDirectory, mp3);
		if (!mp3File.exists()) {
			mp3File = new File(systemDirectory, mp3);
		}

		if (this.isBusy()) {
			player.stop();
		}
		player = new MP3Player(mp3File);
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
		long startTime = System.nanoTime();
		test.play("C:\\Accessimap\\sounds\\success.mp3");
		System.out.println((System.nanoTime() - startTime) / 1000000000.0f);

		while (test.isBusy()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		startTime = System.nanoTime();
		test.play("C:\\Accessimap\\sounds\\success.mp3");
		System.out.println((System.nanoTime() - startTime) / 1000000000.0f);

		while (test.isBusy()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setMapDirectory(String mapDirectory) {
		this.mapDirectory = mapDirectory;
	}

	public void setSystemDirectory(String systemDirectory) {
		this.systemDirectory = systemDirectory;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
