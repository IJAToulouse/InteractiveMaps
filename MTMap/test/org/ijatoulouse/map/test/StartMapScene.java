package org.ijatoulouse.map.test;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.mt4j.MTApplication;
import org.xml.sax.SAXException;

import code.parsing.filechooser.CustomFileChooser;

public class StartMapScene extends MTApplication {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		initialize();
	}

	@Override
	public void startUp() {
		
		// Choix de la carte SVG à afficher
		final CustomFileChooser svgFileChooser = new CustomFileChooser("xml",
				"Choisir la carte SVG");
		
		while (svgFileChooser.getSelectedFile() == null)
			svgFileChooser.showOpenDialog(this);

		try {
			addScene(new MapScene(this, "Map Scene", svgFileChooser
					.getSelectedFile().getPath()));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}