package org.ija.imaps.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ija.imaps.algorithm.XYGuidanceAlgorithm;
import org.ija.imaps.screen.ScreenManager;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTSlider;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.math.Vector3D;

public class ApplicationContext {

	private static Filter filter;
	private static POI currentPOI;
	private static List<Filter> filters;
	private static AbstractScene scene;
	private static Map<String, POI> pois = new HashMap<String, POI>();
	private static Map<String, Filter> filterIndex = new HashMap<String, Filter>();
	private static Vector3D finger;
	private static XYGuidanceAlgorithm algo;
	private static Float svgWidht;
	private static Float svgHeight;
	private static ScreenManager screenManager;
	private static MTComponent mainContainer;
	private static MTSlider slider;

	public static Float getSvgWidht() {
		return svgWidht;
	}

	public static void setSvgWidht(Float svgWidht) {
		ApplicationContext.svgWidht = svgWidht;
	}

	public static Float getSvgHeight() {
		return svgHeight;
	}

	public static void setSvgHeight(Float svgHeight) {
		ApplicationContext.svgHeight = svgHeight;
	}

	public static boolean isInteractive() {
		return filter != null;
	}

	public static Filter getCurrentFilter() {
		return filter;
	}
	
	public static Filter getFilterFromIndex(String name) {
		return filterIndex.get(name);
	}

	public static void setCurrentFilter(String filter) {
		ApplicationContext.filter = filterIndex.get(filter);
	}
	
	public static AbstractScene getScene() {
		return scene;
	}

	public static void setScene(AbstractScene scene) {
		ApplicationContext.scene = scene;
	}

	public static void clearAllGestures(MTComponent comp) {
		comp.unregisterAllInputProcessors();
		comp.removeAllGestureEventListeners();
	}

	public static List<Filter> getFilters() {
		return filters;
	}

	public static void setFilters(List<Filter> filters) {
		ApplicationContext.filters = filters;
		filterIndex.clear();
		for (Filter filter : filters) {
			filterIndex.put(filter.getName(), filter);
		}
		ApplicationContext.filter = filters.get(0);
	}

	public static Collection<POI> getPOIs() {
		return pois.values();
	}

	public static void removeAllPOIs() {
		pois.clear();
	}

	public static void addPOI(String id, POI poi) {
		pois.put(id, poi);
	}

	public static POI getPOI(String id) {
		return pois.get(id);
	}

	public static void setGuidanceNewTarget(POI poi) {
		if (algo.isRunning()) {
			algo.stopRunning();
		}
		currentPOI = poi;
		algo.setTarget(poi.getGlobalPosition());
		finger = null;
		algo.startRunning();
	}

	public static POI getCurrentPOI() {
		return currentPOI;
	}

	public static void setCurrentFingerPosition(Vector3D position) {
		finger = position;
	}

	public static Vector3D getCurrentFingerPosition() {
		return finger;
	}

	public static boolean isGuidanceRunning() {
		return algo.isRunning();
	}

	public static void startGuidanceAlgorithm() {
		algo = new XYGuidanceAlgorithm();
		algo.start();
	}

	public static void stopGuidanceAlgorithm() {
		if (algo.isRunning()) {
			algo.stopRunning();
			finger = null;
			SAPI5Player.getInstance().play("Guidage termin�.");
		}
	}

	public static void setScreenManager(ScreenManager sm) {
		screenManager = sm;
	}

	public static ScreenManager getScreenManager() {
		return (screenManager);
	}

	public static void setMainContainer(MTComponent compo) {
		mainContainer = compo;
	}

	public static MTComponent getMainContainer() {
		return (mainContainer);
	}

	public static void setTTSSlider(MTSlider s) {
		slider = s;
	}
	
	public static MTSlider getTTSSlider() {
		return slider;
	}
}
