package org.ijatoulouse.map.test.container;

import org.mt4j.AbstractMTApplication;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

// Classe de la scène de la carte interprétée
public class MapScene extends AbstractScene {

	public MapScene(AbstractMTApplication app, String name) {
		super(app, name);

		setClearColor(MTColor.BLUE);

		MTRectangle background = new MTRectangle(app, 1920, 1080);
		background.setFillColor(MTColor.WHITE);
		background.unregisterAllInputProcessors();
		background.removeAllGestureEventListeners();
		this.getCanvas().addChild(background);

		MTRectangle map = new MTRectangle(app, 1400, 900);
		map.setFillColor(MTColor.BLACK);
		background.addChild(map);

		MTRectangle poi = new MTRectangle(app, 30, 30);
		poi.setFillColor(MTColor.YELLOW);
		map.addChild(poi);
	}
}
