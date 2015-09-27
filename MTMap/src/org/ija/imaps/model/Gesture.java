package org.ija.imaps.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Gesture {

	@XmlEnumValue("single_tap")
	SINGLE_TAP("single_tap"), @XmlEnumValue("double_tap")
	DOUBLE_TAP("double_tap"), @XmlEnumValue("long_tap")
	LONG_TAP("long_tap");
	private final String value;

	Gesture(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static Gesture fromValue(String v) {
		for (Gesture c : Gesture.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
