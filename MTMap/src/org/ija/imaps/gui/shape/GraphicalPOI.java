package org.ija.imaps.gui.shape;

import org.ija.imaps.listener.AbstractGestureListener;
import org.ija.imaps.listener.DoubleTapListener;
import org.ija.imaps.listener.LongTapListener;
import org.ija.imaps.listener.SingleTapListener;
import org.ija.imaps.model.Action;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.Gesture;
import org.ija.imaps.model.POI;
import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

public abstract class GraphicalPOI {

	final AbstractShape shape;

	public GraphicalPOI(AbstractShape shape, POI poi) {

		this.shape = shape;

		shape.setStrokeColor(MTColor.BLUE);
		shape.sendToFront();

		// Remove processor and listeners
		shape.unregisterAllInputProcessors();
		shape.removeAllGestureEventListeners();

		AbstractGestureListener dtlistener = new DoubleTapListener();
		AbstractGestureListener stlistener = new SingleTapListener();
		AbstractGestureListener ltlistener = new LongTapListener();

		// Processor
		shape.registerInputProcessor(new TapProcessor(ApplicationContext
				.getScene().getMTApplication()));
		shape.registerInputProcessor(new TapProcessor(ApplicationContext
				.getScene().getMTApplication(), 25, true, 350));
		shape.registerInputProcessor(new TapAndHoldProcessor(ApplicationContext
				.getScene().getMTApplication(), 2000));

		// For each actions
		for (Action action : poi.getActions()) {
			switch (action.getGesture()) {
			case SINGLE_TAP:
				stlistener.addFilteredAction(action.getFilter(), action);
				break;
			case DOUBLE_TAP:
				dtlistener.addFilteredAction(action.getFilter(), action);
				break;
			case LONG_TAP:
				ltlistener.addFilteredAction(action.getFilter(), action);
				break;
			default:
				break;
			}
		}

		shape.addGestureListener(TapProcessor.class, stlistener);
		shape.addGestureListener(TapProcessor.class, dtlistener);
		shape.addGestureListener(TapAndHoldProcessor.class, ltlistener);
	}

	public Vector3D getCenterPointGlobal() {
		return shape.getCenterPointGlobal();
	}

	public void sendToFront() {
		shape.sendToFront();
	}

	public MTComponent getShape() {
		return shape;
	}

}
