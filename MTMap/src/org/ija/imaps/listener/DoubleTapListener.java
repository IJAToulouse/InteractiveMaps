package org.ija.imaps.listener;

import java.util.HashMap;
import java.util.Map;

import org.ija.imaps.model.Action;
import org.ija.imaps.model.ApplicationContext;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;

public class DoubleTapListener implements IGestureEventListener {
	
	private Map<String,Action> actions = new HashMap<String, Action>();
	
	public void addFilteredAction(String filterId, Action action) {
		actions.put(filterId, action);
	}
	
	@Override
	public boolean processGestureEvent(MTGestureEvent ge) {

		TapEvent te = (TapEvent) ge;
		
		if (te.isDoubleTap()) {
			play();
		}
		
		return false;
	}

	public void play() {
		if (ApplicationContext.isInteractive()) {
			
			// Get action for current filter
			Action action = actions.get(ApplicationContext.getCurrentFilter().getId());
			
			// Try to find something to do
			if (action!=null) {
				ApplicationContext.stopSoundPlayers();
				action.launch();
			}
		}
	}


}
