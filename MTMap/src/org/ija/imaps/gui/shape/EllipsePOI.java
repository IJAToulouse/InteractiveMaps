package org.ija.imaps.gui.shape;

import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

public class EllipsePOI extends GraphicalPOI {

	public EllipsePOI(float cx, float cy, float rx, float ry, POI poi) {

		super(createEllipse(cx, cy, rx, ry), poi);
	}

	private static AbstractShape createEllipse(float cx, float cy, float rx,
			float ry) {
		return new MTEllipse(ApplicationContext.getScene().getMTApplication(),
				new Vector3D(cx, cy), rx, ry);
	}
}
