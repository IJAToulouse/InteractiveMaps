package org.ija.imaps.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.mt4j.MTApplication;

public class StartMapScene extends MTApplication {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	@Override
	public void startUp() {
		addScene(new MapScene(this, "Map Scene"));
	}
}