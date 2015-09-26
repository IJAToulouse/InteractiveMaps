package org.ija.imaps.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.ija.tools.media.MP3Player;
import org.ija.tools.tts.SAPI5Player;

@XmlRootElement
public class Action implements Comparable<Action> {
	
	@XmlAttribute
	String filter;
	
	@XmlAttribute
	String protocol;
	
	@XmlAttribute
	String value;

	public Action() {
	}

	public String getProtocol() {
		return protocol;
	}
	
	public String getFilter() {
		return filter;
	}

	public String getValue() {
		return value;
	}

	public void launch() {
		if (protocol.equalsIgnoreCase("TTS")) {
			SAPI5Player.getInstance().play(value);
		} else if (protocol.equalsIgnoreCase("MP3")) {
			MP3Player.getInstance().play(value);
		}
	}

	@Override
	public int compareTo(Action poiAction) {
		return value.toLowerCase().compareTo(poiAction.getValue().toLowerCase());
	}

	@Override
	public String toString() {
		return "Action [filter=" + filter + ", protocol=" + protocol
				+ ", value=" + value + "]";
	}
	
}
