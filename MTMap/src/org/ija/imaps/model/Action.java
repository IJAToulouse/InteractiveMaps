package org.ija.imaps.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.ija.imaps.controller.ActionController;

@XmlRootElement
public class Action implements Comparable<Action> {

	@XmlAttribute
	String filter;

	@XmlAttribute
	Protocol protocol;

	@XmlAttribute
	Gesture gesture;

	@XmlAttribute
	String value;

	public Action() {
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public String getFilter() {
		return filter;
	}

	public String getValue() {
		return value;
	}

	public Gesture getGesture() {
		return gesture;
	}

	@Override
	public int compareTo(Action poiAction) {
		return value.toLowerCase()
				.compareTo(poiAction.getValue().toLowerCase());
	}

	@Override
	public String toString() {
		return "Action [filter=" + filter + ", protocol=" + protocol
				+ ", gesture=" + gesture + ", value=" + value + "]";
	}

}
