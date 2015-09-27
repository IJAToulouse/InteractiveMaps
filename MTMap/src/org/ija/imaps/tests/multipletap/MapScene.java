package org.ija.imaps.tests.multipletap;

import org.mt4j.AbstractMTApplication;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

// Classe de la scène de la carte interprétée
public class MapScene extends AbstractScene {

	public MapScene(AbstractMTApplication app, String name) {

		super(app, name);

		// On peint le fond de la scène en blanc
		setClearColor(new MTColor(255, 255, 255, 255));
	
		final MTTextArea test = new MTTextArea(app);

		test.setFillColor(MTColor.BLACK);
		test.unregisterAllInputProcessors();
		test.removeAllGestureEventListeners();

//		test.registerInputProcessor(new TapProcessor(app));
//		test.addGestureListener(TapProcessor.class, new IGestureEventListener() {
//			public boolean processGestureEvent(MTGestureEvent ge) {
//				TapEvent te = (TapEvent)ge;
//				switch (te.getId()) {
//				case MTGestureEvent.GESTURE_STARTED:
//					System.out.println("TAP STARTED");
//					break;
//				case MTGestureEvent.GESTURE_UPDATED:
//					System.out.println("TAP UPDATED");
//					break;
//				case MTGestureEvent.GESTURE_ENDED:
//					if (te.isTapped()){
//						System.out.println("TAP ENDED");
//					}
//					break;
//				}
//				return false;
//			}
//		});
		
		test.registerInputProcessor(new TapProcessor(app, 25, true, 350));
		test.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
				System.out.println(te.getTapID());
				
				if (te.isTapped()){
					System.out.println("TAP");
				} else if (te.isDoubleTap()){
					System.out.println("DOUBLE TAP");
				}
				
//				switch (te.getId()) {
//				case MTGestureEvent.GESTURE_STARTED:
//					System.out.println("TAP STARTED");
//					break;
//				case MTGestureEvent.GESTURE_UPDATED:
//					System.out.println("TAP UPDATED");
//					break;
//				case MTGestureEvent.GESTURE_ENDED:
//					if (te.isTapped()){
//						System.out.println("TAP ENDED");
//					}
//					if (te.isDoubleTap()){
//						System.out.println("DOUBLE TAP ENDED");
//					}
//					break;
//				}
				return false;
			}
		});
		
		this.getCanvas().addChild(test);
		test.setAnchor(PositionAnchor.UPPER_LEFT);
		test.setPositionGlobal(new Vector3D(100,100,0));
	}
}
