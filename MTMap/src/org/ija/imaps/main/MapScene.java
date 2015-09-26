package org.ija.imaps.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ija.imaps.gui.CustomFileChooser;
import org.ija.imaps.gui.menu.Menu;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.Config;
import org.ija.imaps.model.POI;
import org.ija.imaps.parser.SVGParser;
import org.ija.tools.media.MP3Player;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.AbstractMTApplication;
import org.mt4j.components.MTComponent;
import org.mt4j.components.css.style.CSSSelector;
import org.mt4j.components.css.util.CSSKeywords.CSSSelectorType;
import org.mt4j.components.css.util.CSSTemplates;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Direction;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.UnistrokeGesture;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4jx.components.visibleComponents.widgets.menus.MTHUD;
import org.mt4jx.components.visibleComponents.widgets.menus.MenuItem;
import org.xml.sax.SAXException;

import processing.core.PImage;

// Classe de la scène de la carte interprétée
public class MapScene extends AbstractScene {

	private Config conf;
	private MapContainer container;
	private Menu menu;
	private MTComponent backgroundImage;
	private MTHUD hud;

	public MapScene(AbstractMTApplication app, String name) {

		super(app, name);

		// Set current scene
		ApplicationContext.setScene(this);

		// Load properties from project.properties
		Properties properties = new Properties();
		try {
			FileInputStream fileStream = new FileInputStream(
					"project.properties");
			properties.load(fileStream);
			fileStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Set params
		SAPI5Player.setTTSSpeed(properties.getProperty("tts_speed"));

		// Register players
		ApplicationContext.registerSoundPlayers(MP3Player.getInstance(),
				SAPI5Player.getInstance());

		// On peint le fond de la scène en blanc
		setClearColor(new MTColor(255, 255, 255, 255));

		// Map container
		container = new MapContainer(1488, 1050, 744, 525);

		// Set current algorithm
		ApplicationContext.startGuidanceAlgorithm();

		// Menus
		app.getCssStyleManager().setGloballyEnabled(true);

		// Load a different CSS Style for each component
		app.getCssStyleManager().loadStylesAndOverrideSelector(
				CSSTemplates.BLUESTYLE,
				new CSSSelector("MTHUD", CSSSelectorType.CLASS));

		// Create Menu Items
		List<MenuItem> menus = new ArrayList<MenuItem>();
		menus.add(new MenuItem("Flasher", new GestureListener("flasher")));
		menus.add(new MenuItem("Rechercher", new GestureListener("rechercher")));
		menus.add(new MenuItem("Quitter", new GestureListener("quitter")));

		// Create Heads up display (on bottom of the screen)
		hud = new MTHUD(app, menus, 150, MTHUD.BOTTOM);
		this.getCanvas().addChild(hud);

		menu = new Menu();

		app.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					hud.setVisible(!hud.isVisible());
				}
			}
		});
	}

	private void addNewMap(File svgFile) {

		// Remove all previous element from container
		container.removeAllChildren();

		// MP3 root directory
		MP3Player.getInstance().setRootDirectory(svgFile.getParent());

		// Binding XML
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			conf = (Config) jaxbUnmarshaller.unmarshal(new File(svgFile
					.getPath().replace(".svg", ".xml")));
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Add filters and POI to context
		ApplicationContext.setFilters(conf.getFilters());
		ApplicationContext.setCurrentFilter(conf.getFilters().get(0));

		System.out.println("\nConfigurations :");
		System.out.println(conf.getFilters());
		System.out.println(conf.getPois());

		ApplicationContext.removeAllPOIs();
		for (POI poi : conf.getPois()) {
			ApplicationContext.addPOI(poi.getId(), poi);
		}

		// Set new background image
		PImage image = getMTApplication().loadImage(
				svgFile.getPath().replace(".svg", ".jpg"));

		// Remove background if already exist and create the new one
		if (backgroundImage != null) {
			getCanvas().removeChild(backgroundImage);
		}
		backgroundImage = new MTBackgroundImage(getMTApplication(), image,
				false);
		getCanvas().addChild(backgroundImage);

		// Parse svg file to find POIs
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			SVGParser svgParser = new SVGParser();
			parser.parse(svgFile, svgParser);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add POIs to container
		for (POI poi : ApplicationContext.getPOIs()) {
			poi.addToParent(container);
		}

		// Create menu and send container to front
		menu.create();
		container.sendToFront();
	}

	private File getFileFromChooser() {
		CustomFileChooser svgFileChooser = new CustomFileChooser(
				"Choisir la carte SVG");
		if (svgFileChooser.showOpenDialog(ApplicationContext.getScene()
				.getMTApplication()) == JFileChooser.APPROVE_OPTION) {
			return svgFileChooser.getSelectedFile();
		}
		return null;
	}

	private File getFileFromQrCode() {
		return new File(new CameraReader().decode());
	}

	private class GestureListener implements IGestureEventListener {
		String string;

		public GestureListener(String string) {
			super();
			this.string = string;
		}

		public boolean processGestureEvent(MTGestureEvent ge) {

			if (ge instanceof TapEvent) {
				TapEvent te = (TapEvent) ge;
				if (te.getTapID() == TapEvent.TAPPED) {
					if (string.equals("flasher")) {
						hud.setVisible(false);
						addNewMap(getFileFromQrCode());
					} else if (string.equals("rechercher")) {
						File file = getFileFromChooser();
						if (file != null) {
							hud.setVisible(false);
							addNewMap(file);
						}
					} else if (string.equals("quitter")) {
						System.exit(0);
					}
				}
			}
			return true;
		}
	}

	private class MapContainer extends MTRectangle {

		public MapContainer(int width, int height, int x, int y) {

			super(ApplicationContext.getScene().getMTApplication(), new PImage(
					width, height));

			ApplicationContext.clearAllGestures(this);
			ApplicationContext.getScene().getCanvas().addChild(this);

			setPositionGlobal(new Vector3D(x, y, 0));
			setStrokeColor(MTColor.BLACK);

			addInputListener(new IMTInputEventListener() {
				public boolean processInputEvent(MTInputEvent inEvt) {
					if (ApplicationContext.isGuidanceRunning()
							&& inEvt instanceof AbstractCursorInputEvt) {
						final AbstractCursorInputEvt posEvt = (AbstractCursorInputEvt) inEvt;
						final InputCursor m = posEvt.getCursor();

						if (posEvt.getId() == AbstractCursorInputEvt.INPUT_UPDATED) {
							// System.out.println(m.getPosition());
							ApplicationContext.setCurrentFingerPosition(m
									.getPosition());
						} else if (posEvt.getId() == AbstractCursorInputEvt.INPUT_ENDED) {
							ApplicationContext.setCurrentFingerPosition(null);
						}
					}
					return false;
				}
			});

			UnistrokeProcessor up = new UnistrokeProcessor(ApplicationContext
					.getScene().getMTApplication());
			up.addTemplate(UnistrokeGesture.DELETE, Direction.CLOCKWISE);
			// up.addTemplate(UnistrokeGesture.CIRCLE,
			// Direction.COUNTERCLOCKWISE);

			registerInputProcessor(up);
			addGestureListener(UnistrokeProcessor.class,
					new IGestureEventListener() {
						public boolean processGestureEvent(MTGestureEvent ge) {
							UnistrokeEvent ue = (UnistrokeEvent) ge;
							switch (ue.getId()) {
							case UnistrokeEvent.GESTURE_ENDED:
								UnistrokeGesture g = ue.getGesture();
								System.out.println("Recognized gesture: " + g);
								if (g.equals(UnistrokeGesture.DELETE)) {
									ApplicationContext.stopGuidanceAlgorithm();
								} else {
									if (g.equals(UnistrokeGesture.CIRCLE)) {
										hud.setVisible(true);
									}
								}
								break;
							default:
								break;
							}
							return false;
						}
					});

		}
	}
}
