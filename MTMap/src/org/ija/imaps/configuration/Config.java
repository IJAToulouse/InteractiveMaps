package org.ija.imaps.configuration;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class Config {

	@XmlElement(name = "filters")
	private Filters filters = null;
	
	@XmlElement(name = "pois")
	private POIs pois = null;
	
	public void setPois(POIs pois) {
		this.pois = pois;
	}
	
	public POIs getPoiss() {
		return pois;
	}
	
	public void setFilters(Filters filters) {
		this.filters = filters;
	}
	
	public Filters getFilterss() {
		return filters;
	}


	@Override
	public String toString() {
		return "Config [filters=" + filters + ", pois=" + pois + "]";
	}

	public static void main(String[] args) {

		try {

			File file = new File("test.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Config config = (Config) jaxbUnmarshaller.unmarshal(file);
			System.out.println(config);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}