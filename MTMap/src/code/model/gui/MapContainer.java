package code.model.gui;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Direction;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.UnistrokeGesture;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;
import code.model.ApplicationContext;

public class MapContainer extends MTRectangle {

	public MapContainer(int width, int height, int x, int y) {

		super(ApplicationContext.getScene().getMTApplication(), new PImage(
				width, height));
		
		ApplicationContext.clearAllGestures(this);
		ApplicationContext.getScene().getCanvas().addChild(this);
		
		setPositionGlobal(new Vector3D(x, y, 0));
		setStrokeColor(MTColor.BLACK);
		
		addInputListener(new IMTInputEventListener() {
			public boolean processInputEvent(MTInputEvent inEvt){
				if(ApplicationContext.isGuidanceRunning() && inEvt instanceof AbstractCursorInputEvt){
					final AbstractCursorInputEvt posEvt = (AbstractCursorInputEvt)inEvt;
					final InputCursor m = posEvt.getCursor();
					
					if (posEvt.getId() == AbstractCursorInputEvt.INPUT_UPDATED) {
						//System.out.println(m.getPosition());
						ApplicationContext.setCurrentFingerPosition(m.getPosition());
					} else if (posEvt.getId() == AbstractCursorInputEvt.INPUT_ENDED) {
						ApplicationContext.setCurrentFingerPosition(null);
					}
				}
				return false;
			}
		});
		
		
		UnistrokeProcessor up = new UnistrokeProcessor(ApplicationContext.getScene().getMTApplication());
		up.addTemplate(UnistrokeGesture.DELETE, Direction.CLOCKWISE);
		
		registerInputProcessor(up);
		addGestureListener(UnistrokeProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				UnistrokeEvent ue = (UnistrokeEvent)ge;
				switch (ue.getId()) {
				case UnistrokeEvent.GESTURE_ENDED:
					UnistrokeGesture g = ue.getGesture();
					System.out.println("Recognized gesture: " + g);
					if (g.equals(UnistrokeGesture.DELETE)) {
						ApplicationContext.stopGuidanceAlgorithm();
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		
	}
	
}
