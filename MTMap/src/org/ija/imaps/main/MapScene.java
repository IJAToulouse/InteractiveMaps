package org.ija.imaps.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
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

import org.apache.batik.transcoder.TranscoderException;
import org.ija.imaps.camera.CameraReader;
import org.ija.imaps.controller.ActionController;
import org.ija.imaps.gui.CustomFileChooser;
import org.ija.imaps.gui.menu.GraphicalMapMenu;
import org.ija.imaps.gui.shape.RectanglePOI;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.Config;
import org.ija.imaps.model.Filter;
import org.ija.imaps.model.POI;
import org.ija.imaps.parser.SVGParser;
import org.ija.imaps.screen.ScreenManager;
import org.ija.imaps.svg.SvgTranscoder;
import org.ija.tools.media.MusicPlayer;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.AbstractMTApplication;
import org.mt4j.components.css.style.CSSSelector;
import org.mt4j.components.css.util.CSSKeywords.CSSSelectorType;
import org.mt4j.components.css.util.CSSTemplates;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTSlider;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.gestureAction.DefaultDragAction;
import org.mt4j.input.gestureAction.InertiaDragAction;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Direction;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.UnistrokeGesture;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4jx.components.visibleComponents.widgets.menus.MTHUD;
import org.mt4jx.components.visibleComponents.widgets.menus.MenuItem;
import org.xml.sax.SAXException;

// Classe de la scène de la carte interprétée
public class MapScene extends AbstractScene {

	private static final int FLASHER = 0;
	private static final int RECHERCHER = 1;
	private static final int GENERER = 2;
	private static final int QUITTER = 9;

	private Config conf;
	private MapContainer mapContainer;
	private GraphicalMapMenu mapMenu;
	private MTHUD hud;
	private MTSlider ttsSlider;
	private File svgFile;

	public MapScene(AbstractMTApplication app, String name) {

		super(app, name);

		// Set current scene
		ApplicationContext.setScene(this);

		// Set background color
		setClearColor(MTColor.WHITE);

		// Load properties from project.properties
		Properties properties = new Properties();
		try {
			FileInputStream fileStream = new FileInputStream(
					"project.properties");
			properties.load(fileStream);
			fileStream.close();
		} catch (Exception e) {
			System.out.println("Fichier project.properties non trouvé.");
			System.exit(1);
		}

		// Set params
		SAPI5Player.setTTSSpeed(properties.getProperty("tts.speed"));
		SAPI5Player.setLocale(properties.getProperty("tts.locale"));
		SAPI5Player.setVoiceName(properties.getProperty("tts.voice.name"));

		MusicPlayer.getInstance().setSystemDirectory(
				properties.getProperty("accessimap_home") + "sounds");
		ScreenManager sm = new ScreenManager(properties);
		System.out.println(sm);
		ApplicationContext.setScreenManager(sm);

		// Register players
		ActionController.registerSoundPlayers(MusicPlayer.getInstance(),
				SAPI5Player.getInstance());

		// Create main container
		MTRectangle mainContainer = new MTRectangle(app, sm.getScreenWidthPx(),
				sm.getScreenHeightPx());
		mainContainer.setFillColor(MTColor.WHITE);
		ApplicationContext.clearAllGestures(mainContainer);
		this.getCanvas().addChild(mainContainer);
		ApplicationContext.setMainContainer(mainContainer);

		// Map container
		// container = new MapContainer(1488, 1050, 744, 525);
		mapContainer = new MapContainer();

		// Set current algorithm
		ApplicationContext.startGuidanceAlgorithm();

		// Application menu
		createApplicationMenu();

		// MapMenu
		mapMenu = new GraphicalMapMenu();

	}

	private void createApplicationMenu() {
		// Menus
		this.getMTApplication().getCssStyleManager().setGloballyEnabled(true);

		// Load a different CSS Style for each component
		this.getMTApplication()
				.getCssStyleManager()
				.loadStylesAndOverrideSelector(CSSTemplates.BLUESTYLE,
						new CSSSelector("MTHUD", CSSSelectorType.CLASS));

		// Create Menu Items
		List<MenuItem> menus = new ArrayList<MenuItem>();
		menus.add(new MenuItem("Flasher", new GestureListener(FLASHER)));
		menus.add(new MenuItem("Rechercher", new GestureListener(RECHERCHER)));
		menus.add(new MenuItem("Générer", new GestureListener(GENERER)));
		menus.add(new MenuItem("Quitter", new GestureListener(QUITTER)));
		
		// Slider
		int width = ApplicationContext.getScreenManager().getMapWidthPx();
		int height = ApplicationContext.getScreenManager().getMapHeightPx();
        MTSlider slider = new MTSlider(this.getMTApplication(), 20, 20, 200, 38, 100.0f, 250.0f);
        slider.setValue(SAPI5Player.getInstance().getTTSSpeed());
        this.getCanvas().addChild(slider);
        slider.setStrokeColor(new MTColor(0,0,0));
        slider.setFillColor(new MTColor(220,220,220));
        slider.getKnob().setFillColor(new MTColor(70,70,70));
        slider.getKnob().setStrokeColor(new MTColor(70,70,70));
        slider.addPropertyChangeListener("value", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent p) {
				SAPI5Player.setTTSSpeed((Float)p.getNewValue());
			}
		});
        ApplicationContext.setTTSSlider(slider);

		// Create Heads up display (on bottom of the screen)
		hud = new MTHUD(this.getMTApplication(), menus, 150, MTHUD.BOTTOM);
		this.getCanvas().addChild(hud);

		this.getMTApplication().addKeyListener(new KeyListener() {

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
					ApplicationContext.getTTSSlider().setVisible(!ApplicationContext.getTTSSlider().isVisible());
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					ApplicationContext.getTTSSlider().setVisible(!ApplicationContext.getTTSSlider().isVisible());
				}
			}
		});
	}

	private void addNewMap(File svg) {

		svgFile = svg;

		// Remove all previous element from container
		mapContainer.removeAllChildren();

		// MP3 root directory
		MusicPlayer.getInstance().setMapDirectory(svgFile.getParent());

		// Find an xml file
		File[] xmlFiles = svgFile.getParentFile().listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".xml");
			}
		});

		if (xmlFiles == null || xmlFiles.length == 0) {
			System.out.println("Pas de fichier xml!");
			System.exit(1);
		}

		// Binding XML
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			conf = (Config) jaxbUnmarshaller.unmarshal(xmlFiles[0]);
		} catch (JAXBException e1) {
			System.out.println(e1);
			System.out.println("Fichier XML incorrect");
			System.exit(1);
		}
		
		// GD
		for (Filter filter : conf.getFilters()) {
			filter.getName();
		}

		// Add filters and POI to context
		ApplicationContext.setFilters(conf.getFilters());

		System.out.println("\nConfigurations :");
		System.out.println(conf.getFilters());
		System.out.println(conf.getPois());

		ApplicationContext.removeAllPOIs();
		for (POI poi : conf.getPois()) {
			ApplicationContext.addPOI(poi.getId(), poi);
		}
		managePOICoordinates();

		// Set background image if exist!
		File image = new File(svgFile.getPath().replace(".svg", ".png"));
		mapContainer.setTexture(null);
		if (image.exists()) {
			mapContainer.setTexture(getMTApplication().loadImage(
					image.getPath()));
		}

		// Add POIs to container
		for (POI poi : ApplicationContext.getPOIs()) {
			poi.addToParent(mapContainer);
		}

		// Create menu and send container to front
		mapMenu.createSubMenus();
		mapContainer.sendToFront();
		mapContainer.removeDragListener();
	}

	private void managePOICoordinates() {

		POI first = conf.getPois().get(0);

		// Automatic binding from DER editor?
		if (first.getX() == 0 && first.getY() == 0) {

			for (POI poi : conf.getPois()) {
				poi.setGraphicalPOI(new RectanglePOI(poi.getWidth(), poi
						.getHeight(), poi.getX(), poi.getY(), poi));
			}

		} else {

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
		}
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
		int action;

		public GestureListener(int action) {
			super();
			this.action = action;
		}

		public boolean processGestureEvent(MTGestureEvent ge) {

			if (ge instanceof TapEvent) {
				TapEvent te = (TapEvent) ge;
				if (te.getTapID() == TapEvent.TAPPED) {

					switch (action) {
					case FLASHER:
						hud.setVisible(false);
						addNewMap(getFileFromQrCode());
						MusicPlayer.getInstance().play("success.mp3");
						break;
					case RECHERCHER:
						File file = getFileFromChooser();
						if (file != null) {
							hud.setVisible(false);
							ApplicationContext.getTTSSlider().setVisible(false);
							long startTime = System.nanoTime();
							addNewMap(file);
							System.out
									.println((System.nanoTime() - startTime) / 1000000000.0f);
							startTime = System.nanoTime();
							MusicPlayer.getInstance().play("success.mp3");
							System.out
									.println((System.nanoTime() - startTime) / 1000000000.0f);
						}
						break;
					case GENERER:
						File source = getFileFromChooser();
						if (source != null) {
							boolean success = false;
							try {
								SvgTranscoder.transcode(source, "png");
							} catch (TranscoderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								SvgTranscoder.transcode(source, "pdf");
								success = true;
							} catch (TranscoderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (success) {
								SAPI5Player.getInstance().play("Conversion réussi.");
							} else {
								SAPI5Player.getInstance().play("La conversion a échoué.");
							}
						}
						break;
					case QUITTER:
						System.exit(0);
						break;

					default:
						break;
					}
				}
			}
			return true;
		}
	}

	private class MapContainer extends MTRectangle {

		public MapContainer() {

			super(ApplicationContext.getScene().getMTApplication(),
					ApplicationContext.getScreenManager().getMapWidthPx(),
					ApplicationContext.getScreenManager().getMapHeightPx());

			// super(ApplicationContext.getScene().getMTApplication(), new
			// PImage(
			// width, height));

			ApplicationContext.clearAllGestures(this);
			ApplicationContext.getMainContainer().addChild(this);

			// setPositionGlobal(new Vector3D(x, y, 0));
			setStrokeColor(MTColor.BLACK);

			registerInputProcessor(new DragProcessor(ApplicationContext
					.getScene().getMTApplication()));
			addGestureListener(DragProcessor.class, new DefaultDragAction());
			addGestureListener(DragProcessor.class, new InertiaDragAction());

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

		public void removeDragListener() {
			this.removeAllGestureEventListeners(DragProcessor.class);
		}
	}
}
