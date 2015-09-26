package org.ija.imaps.gui.shape;

import java.util.Map;

import org.ija.imaps.listener.DoubleTapListener;
import org.ija.imaps.model.Action;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
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

		// DoubleTap processor
		shape.registerInputProcessor(new TapProcessor(ApplicationContext
				.getScene().getMTApplication(), 25, true, 350));

		DoubleTapListener listener = new DoubleTapListener();

		// For each actions
		for (Action action : poi.getActions()) {
			listener.addFilteredAction(action.getFilter(), action);
		}
		shape.addGestureListener(TapProcessor.class, listener);
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
