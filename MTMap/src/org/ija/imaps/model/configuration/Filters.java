package org.ija.imaps.model.configuration;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ija.imaps.model.Filter;

@XmlRootElement(name="filters")
public class Filters {

	@XmlElement(name="filter")
	List<Filter> filters;
	
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
	public List<Filter> getFilterss() {
		return filters;
	}

	@Override
	public String toString() {
		return "Filters [filters=" + filters + "]";
	}
	
}
