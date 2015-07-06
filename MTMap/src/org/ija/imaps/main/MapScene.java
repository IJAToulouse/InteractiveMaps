package org.ija.imaps.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ija.imaps.configuration.Config;
import org.ija.imaps.gui.GraphicalPOI;
import org.ija.imaps.gui.MapContainer;
import org.ija.imaps.gui.Menu;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.ija.imaps.parser.SVGParser;
import org.ija.imaps.parser.config.Rect;
import org.ija.tools.media.MP3Player;
import org.ija.tools.media.MusicPlayer;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.AbstractMTApplication;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.xml.sax.SAXException;

import processing.core.PImage;

/**
 * @author Alexis Paoleschi
 * @mail alexis.paoleschi@gmail.com
 */

// Classe de la scène de la carte interprétée
public class MapScene extends AbstractScene {

	private Config conf;

	// Parser de carte SVG
	private SVGParser svgParser;

	// Eléments SVG
	private HashMap<String, Rect> elements;
	
	private MapContainer container;

	private Menu menu;

	public MapScene(AbstractMTApplication app, String name, File svgFile)
			throws ParserConfigurationException, SAXException, IOException,
			JAXBException {
		super(app, name);

		// Set current scene
		ApplicationContext.setScene(this);

		// Register players
		ApplicationContext.registerSoundPlayers(MP3Player.getInstance(),
				SAPI5Player.getInstance());
		
		MP3Player.getInstance().setRootDirectory(svgFile.getParent());

		// Chargement des propriétés
		Properties properties = new Properties();
		FileInputStream fileStream = new FileInputStream("project.properties");
		try {
			properties.load(fileStream);
		} finally {
			fileStream.close();
		}

		// On peint le fond de la scène en blanc
		setClearColor(new MTColor(255, 255, 255, 255));

		// On crée un parser SAX en lui indiquant quel fichier on va parser et
		// avec quel handler il sera traité
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		svgParser = new SVGParser();
		try {
			parser.parse(svgFile, svgParser);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Binding XML
		JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		conf = (Config) jaxbUnmarshaller.unmarshal(new File(svgFile.getPath().replace(".svg", ".xml")));

		// Add filters and POI to context
		ApplicationContext.setFilters(conf.getFilterss().getFilterss());
		ApplicationContext.setCurrentFilter(conf.getFilterss().getFilterss()
				.get(0));

		List<POI> pois = new ArrayList<POI>();
		for (POI poi : conf.getPoiss().getPoiss()) {
			pois.add(poi);
		}
		ApplicationContext.setPOIs(pois);

		// On récupère les informations du parser SVG
		elements = svgParser.getElements();

		System.out.println("\nConfigurations :");
		System.out.println(conf.getFilterss());
		System.out.println(conf.getPoiss());

		// Ajout du fond d'écran correspondant à l'image SVG
		addBackground(svgFile.getPath().replace(".svg", ".jpg"));
		
		// Map container
		container = new MapContainer(1488, 1050, 744, 525);
		
		// Ajout de tous les composants à la scène
		createGraphicalPOIs();

		menu = new Menu();
		
		// Set current algorithm
		ApplicationContext.startGuidanceAlgorithm();
	}

	// Ajout des POIs
	public void createGraphicalPOIs() {

		// Pour tous les éléments du SGV
		for (String chaine : elements.keySet()) {

			Rect rect = elements.get(chaine);

			// Parcours de tous les POIs
			for (POI poi : conf.getPoiss().getPoiss()) {

				String id = poi.getId();
				if (rect.getId().length() >= id.length()) {
					// Condition permettant de déterminer les éléments
					// intéressants récupérés du fichier SVG
					// en fonction de leur présence dans le fichier XML
					if (rect.getId().equals(id)
							|| rect.getId().substring(0, id.length())
									.equals(id)) {

						// Création du POI et ajout à la scène
						GraphicalPOI gpoi = new GraphicalPOI(rect.getWidth().intValue(),
								rect.getHeight().intValue(), rect
										.getX().intValue(), rect.getY()
										.intValue(), poi);
						gpoi.sendToFront();
						container.addChild(gpoi);
						poi.setGraphicalPOI(gpoi);
					}
				}
			}
		}
	}

	// Méthode d'ajout de fond d'écran au canvas
	public void addBackground(String imgFile) {
		// On charge l'image de fond
		PImage image = getMTApplication().loadImage(imgFile);
		// On créé l'image de fond
		MTBackgroundImage backgroundImage = new MTBackgroundImage(
				getMTApplication(), image, false);
		// On applique l'image de fond
		getCanvas().addChild(backgroundImage);
	}

}
