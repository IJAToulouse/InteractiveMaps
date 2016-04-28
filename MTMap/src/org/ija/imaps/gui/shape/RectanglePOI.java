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

	static AbstractShape createRectangle(float svgwidth, float svgheight,
			float svgx, float svgy) {

		// Adaptation des coordonn�es
		// SVG -> coordonn�es commen�ant en bas � gauche de l'�lement
		// MT4j -> coordonn�es commen�ant au centre de l'�l�ment
		// + on change de rep�re pour s'adapter � l'�cran

		int width = (int) (svgwidth
				* ApplicationContext.getScreenManager().getMapWidthPx() / ApplicationContext
				.getScreenManager().getReferenceWidth());
		int height = (int) (svgheight
				* ApplicationContext.getScreenManager().getMapHeightPx() / ApplicationContext
				.getScreenManager().getReferenceHeight());
		int x = (int) (svgx
				* ApplicationContext.getScreenManager().getMapWidthPx() / ApplicationContext
				.getScreenManager().getReferenceWidth());
		int y = (int) (svgy
				* ApplicationContext.getScreenManager().getMapHeightPx() / ApplicationContext
				.getScreenManager().getReferenceHeight());

		AbstractShape rectangle = new MTRectangle(ApplicationContext.getScene()
				.getMTApplication(), width, height);
		rectangle.setPositionRelativeToParent(new Vector3D(x + width / 2, y
				+ height / 2));
		return rectangle;
	}

}
