package org.ija.imaps.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Filter {

	@XmlAttribute	
	private String id;
	
	@XmlAttribute
	private String name;
	
	@XmlAttribute
	private boolean expandable;


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public boolean getExpandable() {
		return expandable;
	}

	@Override
	public String toString() {
		return "Filter [id=" + id + ", name=" + name + ", expandable="
				+ expandable + "]";
	}

}
