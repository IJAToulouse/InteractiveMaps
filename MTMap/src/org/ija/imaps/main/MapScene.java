package org.ija.imaps.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ija.imaps.gui.MapContainer;
import org.ija.imaps.gui.menu.Menu;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.ija.imaps.model.configuration.Config;
import org.ija.imaps.parser.SVGParser;
import org.ija.tools.media.MP3Player;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.AbstractMTApplication;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.xml.sax.SAXException;

import processing.core.PImage;

// Classe de la scène de la carte interprétée
public class MapScene extends AbstractScene {

	private Config conf;

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
		
		// Set params
		SAPI5Player.setTTSSpeed(properties.getProperty("tts_speed"));

		// On peint le fond de la scène en blanc
		setClearColor(new MTColor(255, 255, 255, 255));

		// Binding XML
		JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		conf = (Config) jaxbUnmarshaller.unmarshal(new File(svgFile.getPath()
				.replace(".svg", ".xml")));

		// Add filters and POI to context
		ApplicationContext.setFilters(conf.getFilters());
		ApplicationContext.setCurrentFilter(conf.getFilters().get(0));

		System.out.println("\nConfigurations :");
		System.out.println(conf.getFilters());
		System.out.println(conf.getPois());

		for (POI poi : conf.getPois()) {
			ApplicationContext.addPOI(poi.getId(), poi);
		}

		// Ajout du fond d'écran correspondant à l'image SVG
		addBackground(svgFile.getPath().replace(".svg", ".jpg"));

		// Map container
		container = new MapContainer(1488, 1050, 744, 525);

		// On crée un parser SAX en lui indiquant quel fichier on va parser et
		// avec quel handler il sera traité
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		SVGParser svgParser = new SVGParser();
		try {
			parser.parse(svgFile, svgParser);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Ajout de tous les composants à la scène
		for (POI poi : ApplicationContext.getPOIs()) {
			poi.sendToFront();
			poi.addToParent(container);
		}

		menu = new Menu();

		// Set current algorithm
		ApplicationContext.startGuidanceAlgorithm();
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
