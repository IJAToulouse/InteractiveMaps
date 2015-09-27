package org.ija.imaps.listener;

import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;

public class SingleTapListener extends AbstractGestureListener {

	@Override
	public boolean processGestureEvent(MTGestureEvent ge) {

		TapEvent te = (TapEvent) ge;
		switch (te.getId()) {
		case MTGestureEvent.GESTURE_ENDED:
			if (te.isTapped()) {
				launch();
			}
			break;
		default:
			break;
		}

		return false;
	}
}
