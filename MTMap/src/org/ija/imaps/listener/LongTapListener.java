package org.ija.imaps.listener;

import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.util.MTColor;

public class LongTapListener extends AbstractGestureListener {

	@Override
	public boolean processGestureEvent(MTGestureEvent ge) {

		TapAndHoldEvent th = (TapAndHoldEvent)ge;
		switch (th.getId()) {
		case TapAndHoldEvent.GESTURE_ENDED:
			if (th.isHoldComplete()){
				launch();	
			}
			break;
		default:
			break;
		}
		return false;
	}
}
