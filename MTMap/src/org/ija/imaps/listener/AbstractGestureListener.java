package org.ija.imaps.listener;

import java.util.HashMap;
import java.util.Map;

import org.ija.imaps.controller.ActionController;
import org.ija.imaps.model.Action;
import org.ija.imaps.model.ApplicationContext;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;

public abstract class AbstractGestureListener implements IGestureEventListener {

	private Map<String, Action> actions = new HashMap<String, Action>();

	public void addFilteredAction(String filterId, Action action) {
		actions.put(filterId, action);
	}

	@Override
	public abstract boolean processGestureEvent(MTGestureEvent ge);

	public void launch() {
		if (ApplicationContext.isInteractive()) {

			// Get action for current filter
			Action action = actions.get(ApplicationContext.getCurrentFilter()
					.getId());

			ActionController.launch(action);
		}
	}

}
