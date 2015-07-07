package org.ija.imaps.parser;

import org.ija.imaps.gui.shape.EllipsePOI;
import org.ija.imaps.gui.shape.GraphicalPOI;
import org.ija.imaps.gui.shape.PolygonPOI;
import org.ija.imaps.gui.shape.RectanglePOI;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SVGParser extends DefaultHandler {

	public SVGParser() {
		super();
	}

	// Méthode appelée lors de l'ouverture d'une balise
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {

		String id = attributes.getValue("id");

		// Balise svg?
		if (qName == "svg") {
			ApplicationContext.setSvgWidht(Float.parseFloat(attributes
					.getValue("width")));
			ApplicationContext.setSvgHeight(Float.parseFloat(attributes
					.getValue("height")));
		}

		// Balises rect?
		else if (qName == "rect" && id != null) {

			POI poi = ApplicationContext.getPOI(id);
			if (poi == null) {
				System.out.println("Id " + id
						+ " non présent dans le fichier xml");
			} else {
				GraphicalPOI gpoi = createRectangle(attributes, poi);
				poi.setGraphicalPOI(gpoi);
			}
		}

		// Balises ellipse?
		else if (qName == "ellipse" && id != null) {
			POI poi = ApplicationContext.getPOI(id);
			if (poi == null) {
				System.out.println("Id " + id
						+ " non présent dans le fichier xml");
			} else {
				GraphicalPOI gpoi = createEllipse(attributes, poi);
				poi.setGraphicalPOI(gpoi);
			}
		}

		// Balises ellipse?
		else if (qName == "circle" && id != null) {
			POI poi = ApplicationContext.getPOI(id);
			if (poi == null) {
				System.out.println("Id " + id
						+ " non présent dans le fichier xml");
			} else {
				GraphicalPOI gpoi = createCircle(attributes, poi);
				poi.setGraphicalPOI(gpoi);
			}
		}

		// Balises rect?
		else if (qName == "path" && id != null) {
			POI poi = ApplicationContext.getPOI(id);
			if (poi == null) {
				System.out.println("Id " + id
						+ " non présent dans le fichier xml");
			} else {
				GraphicalPOI gpoi = createPolygon(attributes, poi);
				poi.setGraphicalPOI(gpoi);
			}
		}
	}

	public void warning(SAXParseException e) {
		System.err.println("Attention : " + e.getMessage());
	}

	public void error(SAXParseException e) {
		System.err.println("Erreur : " + e.getMessage());
	}

	private static GraphicalPOI createRectangle(Attributes attributes, POI poi) {
		return new RectanglePOI(Float.parseFloat(attributes.getValue("width")),
				Float.parseFloat(attributes.getValue("height")),
				Float.parseFloat(attributes.getValue("x")),
				Float.parseFloat(attributes.getValue("y")), poi);
	}

	private static GraphicalPOI createEllipse(Attributes attributes, POI poi) {
		return new EllipsePOI(Float.parseFloat(attributes.getValue("cx")),
				Float.parseFloat(attributes.getValue("cy")),
				Float.parseFloat(attributes.getValue("rx")),
				Float.parseFloat(attributes.getValue("ry")), poi);
	}

	private static GraphicalPOI createCircle(Attributes attributes, POI poi) {
		return new EllipsePOI(Float.parseFloat(attributes.getValue("cx")),
				Float.parseFloat(attributes.getValue("cy")),
				Float.parseFloat(attributes.getValue("r")),
				Float.parseFloat(attributes.getValue("r")), poi);
	}
	
	private static GraphicalPOI createPolygon(Attributes attributes, POI poi) {
		return new PolygonPOI(attributes.getValue("d"), poi);
	}
}
