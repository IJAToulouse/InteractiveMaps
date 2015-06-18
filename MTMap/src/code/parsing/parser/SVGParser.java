package code.parsing.parser;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import code.parsing.config.Rect;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe du parser des fichiers SVG
//Le parsing se fait de manière évènementielle, à chaque ouverture de balise, la méthode "startElement" est appelée
//et à chaque fermeture de balise, la méthode "endElement" est appelée
public class SVGParser extends DefaultHandler {
	//Eléments à retourner
	HashMap<String, Rect> elements;
	//Taille de l'image
	HashMap<String, Double> imgSize;
	//Buffer de l'élément courant
	Rect currentRect;
	
	public SVGParser() {
		super();
		this.elements = new HashMap<String, Rect>();
		this.imgSize = new HashMap<String, Double>();
		this.currentRect = new Rect();
	}

	//Méthode appelée lors de l'ouverture d'une balise
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		//Traitement des balises "svg" => balise donnant les informations principales sur l'image SVG
		if(qName == "svg") {
			//Parcours des attributs
			for(int n=0; n<attributes.getLength(); n++) {
				//Traitement de l'attribut de la largeur de l'image
				if(attributes.getQName(n) == "width") {
					imgSize.put("width", Double.parseDouble(attributes.getValue(n)));
				}
				
				//Traitement de l'attribut de la hauteur de l'image
				if(attributes.getQName(n) == "height") {
					imgSize.put("height", Double.parseDouble(attributes.getValue(n)));
				}
			}
		}
		
		//Traitement des balises "rect" => balise des éléments dont certains nous intéressent
		if(qName == "rect") {
			//Parcours des attributs et stockage des informations dans le buffer
			for(int i=0; i<attributes.getLength(); i++) {
				//Traitement de l'attribut de l'ID de l'élément
				if(attributes.getQName(i) == "id") {
					currentRect.setId(attributes.getValue(i));
				}
				
				//Traitement de l'attribut de la largeur de l'élément
				if(attributes.getQName(i) == "width") {
					currentRect.setWidth(Double.parseDouble(attributes.getValue(i)));
				}

				//Traitement de l'attribut de la hauteur de l'élément
				if(attributes.getQName(i) == "height") {
					currentRect.setHeight(Double.parseDouble(attributes.getValue(i)));
				}
				
				//Traitement de l'attribut de la coordonnée X de l'élément
				if(attributes.getQName(i) == "x") {
					currentRect.setX(Double.parseDouble(attributes.getValue(i)));
				}
				
				//Traitement de l'attribut de la coordonnée Y de l'élément
				if(attributes.getQName(i) == "y") {
					currentRect.setY(Double.parseDouble(attributes.getValue(i)));
				}
			}
		}
	}

	//Méthode appelée lors de la fermeture d'une balise
	public void endElement(String uri, String localName, String qName) {
		//Traitement des balises "rect" => balise des éléments dont certains nous intéressent
		if(qName == "rect") {
			//Création de l'élément dans la HashMap à partir du buffer
			elements.put(qName + new String(String.valueOf(elements.size())), currentRect);
			//Remise à zéro du buffer
			currentRect = new Rect();
		}
	}
	
	public void warning(SAXParseException e) {
		System.err.println("Attention : "+e.getMessage());
	}
	
	public void error(SAXParseException e) {
		System.err.println("Erreur : "+e.getMessage());
	}
	
	//On ne modifie pas la méthode characters qui ne fait rien par défaut car on n'a pas de texte entre les balises en SVG
	//On conserve l'implémentation par défaut de fatalError qui propage l'erreur

	//Les différents accesseurs nous permettront de récupérer les données
	public HashMap<String, Rect> getElements() {
		return elements;
	}

	public Rect getCurrentRect() {
		return currentRect;
	}

	public HashMap<String, Double> getImgSize() {
		return imgSize;
	}
}
