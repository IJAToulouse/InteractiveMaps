package code.model.gui;

import java.util.List;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;
import code.model.ApplicationContext;

public abstract class AbstractMenu<E> extends MTRectangle {

	protected int currentIndex;
	protected List<E> menuItem;

	public AbstractMenu(int width, int height, int x, int y) {

		super(ApplicationContext.getScene().getMTApplication(), new PImage(
				width, height));

		// Default values
		currentIndex = 0;

		ApplicationContext.clearAllGestures(this);
		ApplicationContext.getScene().getCanvas().addChild(this);

		setPositionGlobal(new Vector3D(x, y, 0));
		registerInputProcessor(new FlickProcessor(300, 5));
		setStrokeColor(MTColor.BLACK);
		addGestureListener(FlickProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				FlickEvent e = (FlickEvent) ge;
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					switch (e.getDirection()) {

					case NORTH:
					case NORTH_EAST:
					case NORTH_WEST:
						up();
						break;
					case SOUTH:
					case SOUTH_EAST:
					case SOUTH_WEST:
						down();
						break;

					default:
						break;
					}
				}
				return false;
			}
		});

		registerInputProcessor(new TapProcessor(ApplicationContext.getScene()
				.getMTApplication(), 25, true, 350));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				if (te.isTapped()) {
					get();
				} else if (te.isDoubleTap()) {
					select();
				}
				return false;
			}
		});
	}

	public void down() {
		currentIndex++;
		currentIndex = currentIndex % menuItem.size();
		menuDown();
	}

	public void up() {
		currentIndex--;
		if (currentIndex < 0) {
			currentIndex = menuItem.size() - 1;
		}
		menuUp();
	}

	public void get() {
		menuGet();
	}

	public void select() {
		menuSelect();
	}

	protected abstract void menuDown();

	protected abstract void menuUp();

	protected abstract void menuGet();

	protected abstract void menuSelect();
}
