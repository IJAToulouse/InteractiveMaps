package org.ijatoulouse.map.test;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ija.imaps.model.ApplicationContext;
import org.mt4j.AbstractMTApplication;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// Classe de la scène de la carte interprétée
public class MapScene extends AbstractScene {
	
	Document svgDocument;

	public MapScene(AbstractMTApplication app, String name, String fileName)
			throws ParserConfigurationException, SAXException, IOException,
			JAXBException {
		super(app, name);

		// Set current scene
		ApplicationContext.setScene(this);

		// On peint le fond de la scène en blanc
		setClearColor(new MTColor(255, 255, 255, 255));

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		try {
			svgDocument = builder.parse(
		            new FileInputStream(fileName));
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "/svg/path[@id='yes']";
		
		try {
//			String value = xPath.compile(expression).evaluate(svgDocument);
//			System.out.println(value);
//			Node node = (Node) xPath.compile(expression).evaluate(svgDocument, XPathConstants.NODE);
//			System.out.println(node);
			
			//read a nodelist using xpath
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(svgDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
		        org.w3c.dom.Node node = nodeList.item(i);
		        Document newDocument = builder.newDocument();
		        newDocument.appendChild(newDocument.importNode(node, true));

		        // Use a Transformer for output
		        TransformerFactory tFactory =
		        TransformerFactory.newInstance();
		        Transformer transformer = null;
				try {
					transformer = tFactory.newTransformer();
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        DOMSource source = new DOMSource(newDocument);
		        StreamResult result = new StreamResult(System.out);
		        try {
					transformer.transform(source, result);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
