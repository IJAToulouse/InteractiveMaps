package org.ija.imaps.gui.shape;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTPolygon;
import org.mt4j.util.math.Vertex;

public class PolygonPOI extends GraphicalPOI {

	public PolygonPOI(String path, POI poi) {

		super(createPolygon(path), poi);
	}

	private static AbstractShape createPolygon(String path) {

		boolean relative = false;
		List<Vertex> vertices = new ArrayList<Vertex>();

		if (path.startsWith("m")) {
			relative = true;
		}

		Character sep = ' ';
		String[] points = StringUtils.split(
				path.toUpperCase().replaceAll("[MLHVCSQTAZ]", ""), sep);

		Character sepPoint = ',';

		for (String point : points) {
			String[] spoint = StringUtils.split(point, sepPoint);
			vertices.add(new Vertex(Float.valueOf(spoint[0]), Float
					.valueOf(spoint[1])));
		}

		vertices.add(vertices.get(0));

		if (relative) {
			for (int i = 1; i < vertices.size() - 1; i++) {
				vertices.set(i,
						new Vertex(vertices.get(i-1).x + vertices.get(i).x,
								vertices.get(i-1).y + vertices.get(i).y));
			}
		}

		return new MTPolygon(ApplicationContext.getScene().getMTApplication(),
				vertices.toArray(new Vertex[vertices.size()]));
	}
}
