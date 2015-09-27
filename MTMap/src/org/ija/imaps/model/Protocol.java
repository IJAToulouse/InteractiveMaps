package org.ija.imaps.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Protocol {

	@XmlEnumValue("tts")
	TTS("tts"), @XmlEnumValue("mp3")
	MP3("mp3");
	private final String value;

	Protocol(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static Protocol fromValue(String v) {
		for (Protocol c : Protocol.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
