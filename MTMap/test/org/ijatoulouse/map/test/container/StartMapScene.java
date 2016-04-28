package org.ijatoulouse.map.test.container;

import org.mt4j.MTApplication;

public class StartMapScene extends MTApplication {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		initialize();
	}

	@Override
	public void startUp() {
		addScene(new MapScene(this, "Sample"));
	}
}