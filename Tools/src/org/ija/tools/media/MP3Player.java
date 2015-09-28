package org.ija.tools.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.ija.tools.SoundPlayer;

public class MP3Player implements SoundPlayer {

	private BasicPlayer player;
	private String mapDirectory;
	private String systemDirectory;

	private MP3Player() {
		player = new BasicPlayer();
	}

	private static class MP3PlayerHolder {
		private final static MP3Player instance = new MP3Player();
	}

	public static MP3Player getInstance() {
		return MP3PlayerHolder.instance;
	}

	public void setMapDirectory(String mapDirectory) {
		this.mapDirectory = mapDirectory;
	}

	public void setSystemDirectory(String systemDirectory) {
		this.systemDirectory = systemDirectory;
	}

	public void play(String mp3) {

		File mp3File = new File(mapDirectory, mp3);
		if (!mp3File.exists()) {
			mp3File = new File(systemDirectory, mp3);
		}

		if (isBusy()) {
			try {
				player.stop();
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			player.open(mp3File);
			player.play();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isBusy() {
		return (player != null && player.getStatus() == BasicPlayer.PLAYING);
	}

	@Override
	public void stop() {
		try {
			player.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pause() {
		try {
			player.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resume() {
		try {
			player.resume();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// long startTime = System.nanoTime();
		// MP3Player.getInstance().setSystemDirectory("C:\\Accessimap\\sounds\\");
		// MP3Player.getInstance().play("success.mp3");
		// System.out.println((System.nanoTime() - startTime) / 1000000000.0f);
		// startTime = System.nanoTime();
		// MP3Player.getInstance().play("success.mp3");
		// System.out.println((System.nanoTime() - startTime) / 1000000000.0f);
		// MP3Player.getInstance().play();

//		AdvancedPlayer player;
//		try {
//			long startTime = System.nanoTime();
//			player = new AdvancedPlayer(new FileInputStream(
//					"C:\\Accessimap\\sounds\\success.mp3"));
//			player.play();
//			System.out.println((System.nanoTime() - startTime) / 1000000000.0f);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JavaLayerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
