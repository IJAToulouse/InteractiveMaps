package org.ija.imaps.main;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.ija.imaps.gui.CustomFileChooser;
import org.mt4j.MTApplication;
import org.xml.sax.SAXException;

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

		final CustomFileChooser svgFileChooser = new CustomFileChooser(
				"Choisir la carte SVG");
		if (!(svgFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)) {
			System.exit(0);
		}

		try {
			addScene(new MapScene(this, "Map Scene",
					svgFileChooser.getSelectedFile()));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}