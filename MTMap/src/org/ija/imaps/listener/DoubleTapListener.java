package org.ija.imaps.listener;

import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;

public class DoubleTapListener extends AbstractGestureListener {
	
	@Override
	public boolean processGestureEvent(MTGestureEvent ge) {

		TapEvent te = (TapEvent) ge;
		
		if (te.isDoubleTap()) {
			launch();
		}
		
		return false;
	}
}
