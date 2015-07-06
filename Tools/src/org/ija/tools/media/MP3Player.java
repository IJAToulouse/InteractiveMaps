package org.ija.tools.media;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.ija.tools.SoundPlayer;

public final class MP3Player implements SoundPlayer {

	private BasicPlayer player;
	private String rootDirectory;

	private MP3Player() {
		player = new BasicPlayer();
	}

	private static class MP3PlayerHolder {
		private final static MP3Player instance = new MP3Player();
	}

	public static MP3Player getInstance() {
		return MP3PlayerHolder.instance;
	}
	
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public void play(String mp3) {

		if (isBusy()) {
			try {
				player.stop();
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			long startTime = System.nanoTime();
			player.open(new File(rootDirectory,mp3));
			player.play();
			System.out.println((System.nanoTime() - startTime) / 1000000000.0f);
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
		MP3Player.getInstance().play("test");
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MP3Player.getInstance().play("test");
	}

}
