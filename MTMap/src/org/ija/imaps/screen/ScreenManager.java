package org.ija.imaps.screen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.util.Properties;

public class ScreenManager {

	public static final Integer A3_WIDTH = 420;
	public static final Integer A3_HEIGHT = 297;

	private int screenWidthPx;
	private int screenHeightPx;
	private double screenWidthMM;
	private double screenHeightMM;
	private double mapWidthMM;
	private double mapHeightMM;
	private int mapWidthPx;
	private int mapHeightPx;
	private int menuWidthPx;

	// Area
	private int referenceWidth;
	private int referenceHeight;

	public ScreenManager(Properties properties) {

		// Get screen name from properties
		String screenName = properties.getProperty("screen");

		// Extract screen area from properties
		screenWidthMM = Double.parseDouble(properties.getProperty(screenName
				+ ".width"));
		screenHeightMM = Double.parseDouble(properties.getProperty(screenName
				+ ".height"));

		// Get target from properties
		String target = properties.getProperty("map.format");

		// Extract screen area from properties
		mapWidthMM = Double.parseDouble(properties.getProperty(target
				+ ".width"));
		mapHeightMM = Double.parseDouble(properties.getProperty(target
				+ ".height"));

		// Get current screen resolution
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		screenWidthPx = gd.getDisplayMode().getWidth();
		screenHeightPx = gd.getDisplayMode().getHeight();

		// Compute map area
		mapWidthPx = (int) ((mapWidthMM * screenWidthPx) / screenWidthMM);
		mapHeightPx = (int) ((mapHeightMM * screenHeightPx) / screenHeightMM);

		// Get reference widht and height
		referenceWidth = Integer.parseInt(properties.getProperty("ref.width"));
		referenceHeight = Integer
				.parseInt(properties.getProperty("ref.height"));

		// Get menu width
		menuWidthPx = Integer
				.parseInt(properties.getProperty("map.menu.width"));

	}

	@Override
	public String toString() {
		return "ScreenManager [screenWidthPx=" + screenWidthPx
				+ ", screenHeightPx=" + screenHeightPx + ", screenWidthMM="
				+ screenWidthMM + ", screenHeightMM=" + screenHeightMM
				+ ", mapWidthMM=" + mapWidthMM + ", mapHeightMM=" + mapHeightMM
				+ ", mapWidthPx=" + mapWidthPx + ", mapHeightPx=" + mapHeightPx
				+ ", menuWidthPx=" + menuWidthPx + ", referenceWidth="
				+ referenceWidth + ", referenceHeight=" + referenceHeight + "]";
	}

	public int X2Screen(int x) {
		return (x * mapWidthPx) / referenceWidth;
	}

	public int Y2Screen(int y) {
		return (y * mapHeightPx) / referenceHeight;
	}

	public int getMapWidthPx() {
		return mapWidthPx;
	}

	public int getMapHeightPx() {
		return mapHeightPx;
	}

	public int getScreenWidthPx() {
		return screenWidthPx;
	}

	public int getScreenHeightPx() {
		return screenHeightPx;
	}

	public int getMenuWidthPx() {
		return menuWidthPx;
	}

	public void setMenuWidthPx(int menuWidthPx) {
		this.menuWidthPx = menuWidthPx;
	}

	public int getReferenceWidth() {
		return referenceWidth;
	}

	public int getReferenceHeight() {
		return referenceHeight;
	}

	public static void main(String[] args) {

		Properties properties = new Properties();
		try {
			FileInputStream fileStream = new FileInputStream(
					"project.properties");
			properties.load(fileStream);
			fileStream.close();
		} catch (Exception e) {
		}

		ScreenManager test = new ScreenManager(properties);
		System.out.println(test);
		System.out.println(test.X2Screen(10));
		System.out.println(test.X2Screen(100));
		System.out.println(test.X2Screen(943));
	}
}
