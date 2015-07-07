package org.ija.imaps.gui.shape;

import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

public class RectanglePOI extends GraphicalPOI {

	public RectanglePOI(float svgwidth, float svgheight, float svgx,
			float svgy, POI poi) {

		super(createRectangle(svgwidth, svgheight, svgx, svgy), poi);
	}

	static AbstractShape createRectangle(float svgwidth, float svgheight, float svgx,
			float svgy) {

		// Adaptation des coordonnées
		// SVG -> coordonnées commençant en bas à gauche de l'élement
		// MT4j -> coordonnées commençant au centre de l'élément

		AbstractShape rectangle = new MTRectangle(ApplicationContext.getScene()
				.getMTApplication(),
				new PImage((int) svgwidth, (int) svgheight));
		rectangle.setPositionGlobal(new Vector3D(svgx + svgwidth / 2, svgy
				+ svgheight / 2));
		return rectangle;
	}

}
