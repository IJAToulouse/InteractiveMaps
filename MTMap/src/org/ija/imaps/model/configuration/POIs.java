package org.ija.imaps.model.configuration;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ija.imaps.model.POI;

@XmlRootElement(name="pois")
public class POIs {

	@XmlElement(name="poi")
	List<POI> pois;
	
	public void setPois(List<POI> pois) {
		this.pois = pois;
	}
	
	public List<POI> getPoiss() {
		return pois;
	}

	@Override
	public String toString() {
		return "POIs [pois=" + pois + "]";
	}
	
}
