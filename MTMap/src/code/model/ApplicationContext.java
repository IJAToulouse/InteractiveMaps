package code.model;

import java.util.Arrays;
import java.util.List;

import org.ija.tools.SoundPlayer;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.components.MTComponent;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.math.Vector3D;

import code.algorithm.XYGuidanceAlgorithm;

public class ApplicationContext {

	private static List<SoundPlayer> players;
	private static Filter filter;
	private static POI currentPOI;
	private static List<Filter> filters;
	private static AbstractScene scene;
	private static List<POI> pois;
	private static Vector3D finger;
	private static XYGuidanceAlgorithm algo;

	public static boolean isInteractive() {
		return filter != null;
	}

	public static Filter getCurrentFilter() {
		return filter;
	}

	public static void setCurrentFilter(Filter filter) {
		ApplicationContext.filter = filter;
	}

	public static void registerSoundPlayers(SoundPlayer... players) {
		ApplicationContext.players = Arrays.asList(players);
	}

	public static void stopSoundPlayers() {
		for (SoundPlayer soundPlayer : players) {
			soundPlayer.stop();
		}
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

	public static List<POI> getPOIs() {
		return pois;
	}

	public static void setPOIs(List<POI> pois) {
		ApplicationContext.pois = pois;
	}

	public static void setGuidanceNewTarget(POI poi) {
		// TODO avoir si nécessaire
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
			SAPI5Player.getInstance().stopAndSpeak("Guidage terminé.");
		}
	}
}
