package org.ija.imaps.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Config {

	@XmlElementWrapper
	@XmlElements({ @XmlElement(name = "filter", type = Filter.class) })
	private List<Filter> filters = null;

	@XmlElementWrapper
	@XmlElements({ @XmlElement(name = "poi", type = POI.class) })
	private List<POI> pois = null;

	public List<POI> getPois() {
		return pois;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	@Override
	public String toString() {
		return "Config [filters=" + filters + ", pois=" + pois + "]";
	}

}