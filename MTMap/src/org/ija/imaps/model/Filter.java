package org.ija.imaps.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "filter")
public class Filter {

	private String id;
	private String name;
	private boolean expandable;

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@XmlAttribute
	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
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
