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
//Le parsing se fait de mani�re �v�nementielle, � chaque ouverture de balise, la m�thode "startElement" est appel�e
//et � chaque fermeture de balise, la m�thode "endElement" est appel�e
public class SVGParser extends DefaultHandler {
	//El�ments � retourner
	HashMap<String, Rect> elements;
	//Taille de l'image
	HashMap<String, Double> imgSize;
	//Buffer de l'�l�ment courant
	Rect currentRect;
	
	public SVGParser() {
		super();
		this.elements = new HashMap<String, Rect>();
		this.imgSize = new HashMap<String, Double>();
		this.currentRect = new Rect();
	}

	//M�thode appel�e lors de l'ouverture d'une balise
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
		
		//Traitement des balises "rect" => balise des �l�ments dont certains nous int�ressent
		if(qName == "rect") {
			//Parcours des attributs et stockage des informations dans le buffer
			for(int i=0; i<attributes.getLength(); i++) {
				//Traitement de l'attribut de l'ID de l'�l�ment
				if(attributes.getQName(i) == "id") {
					currentRect.setId(attributes.getValue(i));
				}
				
				//Traitement de l'attribut de la largeur de l'�l�ment
				if(attributes.getQName(i) == "width") {
					currentRect.setWidth(Double.parseDouble(attributes.getValue(i)));
				}

				//Traitement de l'attribut de la hauteur de l'�l�ment
				if(attributes.getQName(i) == "height") {
					currentRect.setHeight(Double.parseDouble(attributes.getValue(i)));
				}
				
				//Traitement de l'attribut de la coordonn�e X de l'�l�ment
				if(attributes.getQName(i) == "x") {
					currentRect.setX(Double.parseDouble(attributes.getValue(i)));
				}
				
				//Traitement de l'attribut de la coordonn�e Y de l'�l�ment
				if(attributes.getQName(i) == "y") {
					currentRect.setY(Double.parseDouble(attributes.getValue(i)));
				}
			}
		}
	}

	//M�thode appel�e lors de la fermeture d'une balise
	public void endElement(String uri, String localName, String qName) {
		//Traitement des balises "rect" => balise des �l�ments dont certains nous int�ressent
		if(qName == "rect") {
			//Cr�ation de l'�l�ment dans la HashMap � partir du buffer
			elements.put(qName + new String(String.valueOf(elements.size())), currentRect);
			//Remise � z�ro du buffer
			currentRect = new Rect();
		}
	}
	
	public void warning(SAXParseException e) {
		System.err.println("Attention : "+e.getMessage());
	}
	
	public void error(SAXParseException e) {
		System.err.println("Erreur : "+e.getMessage());
	}
	
	//On ne modifie pas la m�thode characters qui ne fait rien par d�faut car on n'a pas de texte entre les balises en SVG
	//On conserve l'impl�mentation par d�faut de fatalError qui propage l'erreur

	//Les diff�rents accesseurs nous permettront de r�cup�rer les donn�es
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
