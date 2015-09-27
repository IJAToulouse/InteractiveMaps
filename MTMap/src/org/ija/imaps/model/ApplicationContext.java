package org.ija.imaps.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ija.imaps.algorithm.XYGuidanceAlgorithm;
import org.ija.tools.SoundPlayer;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.components.MTComponent;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.math.Vector3D;

public class ApplicationContext {

	private static Filter filter;
	private static POI currentPOI;
	private static List<Filter> filters;
	private static AbstractScene scene;
	private static Map<String, POI> pois = new HashMap<String, POI>();
	private static Vector3D finger;
	private static XYGuidanceAlgorithm algo;
	private static Float svgWidht;
	private static Float svgHeight;

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

	public static void setCurrentFilter(Filter filter) {
		ApplicationContext.filter = filter;
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
			SAPI5Player.getInstance().play("Guidage terminé.");
		}
	}
}
