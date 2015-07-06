package org.ija.imaps.gui;

import java.util.Map;

import org.ija.imaps.listener.DoubleTapListener;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.ija.imaps.model.POIAction;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

public class GraphicalPOI extends MTRectangle {

	public GraphicalPOI(int svgwidth, int svgheight, int svgx, int svgy, POI poi) {
		super(ApplicationContext.getScene().getMTApplication(), new PImage(
				svgwidth, svgheight));

		// Adaptation des coordonnées
		// SVG -> coordonnées commençant en bas à gauche de l'élement
		// MT4j -> coordonnées commençant au centre de l'élément

		this.setPositionGlobal(new Vector3D(svgx + svgwidth / 2, svgy
				+ svgheight / 2, 0));

		this.setStrokeColor(MTColor.BLUE);
		this.sendToFront();

		// Remove processor and listeners
		this.unregisterAllInputProcessors();
		this.removeAllGestureEventListeners();

		// DoubleTap processor
		this.registerInputProcessor(new TapProcessor(ApplicationContext
				.getScene().getMTApplication(), 25, true, 350));

		DoubleTapListener listener = new DoubleTapListener();

		// For each actions
		for (Map.Entry<String, POIAction> entry : poi.getActions().entrySet()) {
			listener.addFilteredAction(entry.getKey(), entry.getValue());
		}
		this.addGestureListener(TapProcessor.class, listener);
	}

}
