package code.scene.listener;

import java.util.HashMap;
import java.util.Map;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;

import code.model.ApplicationContext;
import code.model.POIAction;

public class DoubleTapListener implements IGestureEventListener {
	
	private Map<String,POIAction> actions = new HashMap<String, POIAction>();
	
	public void addFilteredAction(String filterId, POIAction action) {
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
			POIAction action = actions.get(ApplicationContext.getCurrentFilter().getId());
			
			// Try to find something to do
			if (action!=null) {
				ApplicationContext.stopSoundPlayers();
				action.launch();
			}
		}
	}


}
