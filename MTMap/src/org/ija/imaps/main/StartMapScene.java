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

/**
 * @author Alexis Paoleschi
 * @mail alexis.paoleschi@gmail.com
 */

// Classe de la MTApplication dans laquelle on ajoute la sc�ne cr��e au
// pr�alable et on la lance
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

		// La m�thode statique "initialize" configurera l'application
		// multi-touch � partir des param�tres donn�s dans le fichier
		// "Settings.txt"
		initialize();
	}

	@Override
	public void startUp() {
		// Choix de la carte SVG � afficher
		final CustomFileChooser svgFileChooser = new CustomFileChooser("svg",
				"Choisir la carte SVG");
		// On emp�che ici que l'utilisateur ne s�lectionne aucune carte ou qu'il
		// ferme la fen�tre de s�lection
		// Sans ce fichier, l'application n'a pas lieu d'�tre

		if (!(svgFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)) {
			System.exit(0);
		}

		try {
			addScene(new MapScene(this, "Map Scene", svgFileChooser
					.getSelectedFile()));
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