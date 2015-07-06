package code.model;

import org.ija.tools.media.MP3Player;
import org.ija.tools.tts.SAPI5Player;

public class POIAction implements Comparable<POIAction> {

	String protocol;
	String data;

	public POIAction(String protocol, String data) {
		this.protocol = protocol;
		this.data = data;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void launch() {

		if (protocol.equalsIgnoreCase("TTS")) {
			SAPI5Player.getInstance().play(data);
		} else if (protocol.equalsIgnoreCase("MP3")) {
			MP3Player.getInstance().play(data);
		}
	}

	@Override
	public int compareTo(POIAction poiAction) {
		return data.toLowerCase().compareTo(poiAction.getData().toLowerCase());
	}

	@Override
	public String toString() {
		return "POIAction [protocol=" + protocol + ", data=" + data + "]";
	}
	
}
