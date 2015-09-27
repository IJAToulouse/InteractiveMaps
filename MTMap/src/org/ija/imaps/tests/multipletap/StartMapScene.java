package org.ija.imaps.tests.multipletap;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.mt4j.MTApplication;

public class StartMapScene extends MTApplication {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		initialize();
	}

	@Override
	public void startUp() {
		addScene(new MapScene(this, "Map Scene"));
	}
}