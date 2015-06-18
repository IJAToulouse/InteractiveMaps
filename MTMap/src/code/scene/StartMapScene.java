package code.scene;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.mt4j.MTApplication;
import org.xml.sax.SAXException;

import code.parsing.filechooser.CustomFileChooser;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe de la MTApplication dans laquelle on ajoute la scène créée au préalable et on la lance
public class StartMapScene extends MTApplication {
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		//La méthode statique "initialize" configurera l'application multi-touch à partir des paramètres donnés dans le fichier "Settings.txt" 
		initialize();
	}
	
	@Override
	public void startUp() {
		//Choix de la carte SVG à afficher
		final CustomFileChooser svgFileChooser = new CustomFileChooser("svg", "Choisir la carte SVG");
		//On empêche ici que l'utilisateur ne sélectionne aucune carte ou qu'il ferme la fenêtre de sélection
		//Sans ce fichier, l'application n'a pas lieu d'être
		while(svgFileChooser.getSelectedFile() == null)
			svgFileChooser.showOpenDialog(this);

//		//Choix du fichier de configuration XML à associer
//		final CustomFileChooser xmlFileChooser = new CustomFileChooser("xml", "Choisir un fichier de configuration à associer");
//		//On empêche ici que l'utilisateur ne sélectionne aucun fichier de configuration ou qu'il ferme la fenêtre de sélection
//		//Sans ce fichier, l'application n'a pas lieu d'être
//		while(xmlFileChooser.getSelectedFile() == null)
//			xmlFileChooser.showOpenDialog(this);
//		
//		//Choix du fichier de configuration XML à associer
//		final CustomFileChooser jpgFileChooser = new CustomFileChooser("jpg", "Choisir un fichier image de fond");
//		//On empêche ici que l'utilisateur ne sélectionne aucun fichier d'image de fond ou qu'il ferme la fenêtre de sélection
//		while(jpgFileChooser.getSelectedFile() == null)
//			jpgFileChooser.showOpenDialog(this);
			
		//On ajoute la scène MapScene dans l'application multi-touch
		try {
			addScene(new MapScene(
					this, 
					"Map Scene", 
					svgFileChooser.getSelectedFile().getPath())
			);
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