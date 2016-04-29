package org.ija.imaps.gui.menu;

import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.Filter;
import org.ija.tools.tts.SAPI5Player;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

public class MainMenu extends MTRectangle {

	private MainMenuItem menuItem;
	private POIMenu submenu;
	private String selected;
	private MTTextArea textArea;

	public MainMenu(int width, int height, int x, int y, POIMenu submenu) {

		super(ApplicationContext.getScene().getMTApplication(), width, height);

		ApplicationContext.clearAllGestures(this);

		setPositionRelativeToParent(new Vector3D(x, y, 0));
		registerInputProcessor(new FlickProcessor(300, 5));
		setStrokeColor(MTColor.BLACK);
		addGestureListener(FlickProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				FlickEvent e = (FlickEvent) ge;
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					switch (e.getDirection()) {

					case NORTH:
					case NORTH_EAST:
						up();
						break;
					case SOUTH:
					case SOUTH_EAST:
						down();
						break;

					case WEST:
						back();
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

		IFont fontArial = FontManager.getInstance().createFont(
				ApplicationContext.getScene().getMTApplication(), "arial.ttf",
				25, // Font size
				MTColor.WHITE); // Font color

		textArea = new MTTextArea(ApplicationContext.getScene()
				.getMTApplication(), 10, 10, width - 20, 70, fontArial);
		ApplicationContext.clearAllGestures(textArea);

		textArea.setNoStroke(false);
		textArea.setStrokeColor(MTColor.GREEN);
		textArea.setStrokeWeight(4);
		textArea.setNoFill(false);
		textArea.setFillColor(MTColor.BLACK);

		this.addChild(textArea);

		// Create and init menu item
		menuItem = new MainMenuItem();
		selected = menuItem.getCurrent();

		updateText(menuItem.get());
		this.submenu = submenu;
	}

	public boolean isItemSelected() {
		return (menuItem.getCurrent().equals(selected));
	}

	protected void down() {
		menuItem.down();
		playAndUpdateText(menuItem.get());

		Filter temp = ApplicationContext.getFilterFromIndex(menuItem
				.getCurrent());
		submenu.setTextVisible(temp != null && temp.getExpandable()
				&& isItemSelected());

	}

	protected void up() {
		menuItem.up();
		playAndUpdateText(menuItem.get());

		Filter temp = ApplicationContext.getFilterFromIndex(menuItem
				.getCurrent());
		submenu.setTextVisible(temp != null && temp.getExpandable()
				&& isItemSelected());

	}

	protected void get() {
		play(menuItem.get());
	}

	protected void back() {

		// Main item selected?
		if (menuItem.getState() == MainMenuItem.SUBS) {
			menuItem.setState(MainMenuItem.MAIN);
			updateText(menuItem.get());
			submenu.setTextVisible(false);
			directPlay("Menu principal");
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			play(menuItem.get());
		}
	}

	protected void select() {

		// Main item selected?
		if (menuItem.getState() == MainMenuItem.MAIN) {

			// Is there sub menu for current item?
			if (menuItem.hasSubs(menuItem.getCurrentMain())) {
				menuItem.setState(MainMenuItem.SUBS);
				updateText(menuItem.get());
				play(menuItem.get());
			}

			// No subs
			else {
				directPlay(menuItem.get() + " " + "sélectionné");
				selected = menuItem.getCurrent();
				updateText(menuItem.get());
				ApplicationContext.setCurrentFilter(selected);
				submenu.reset();
			}
		}

		// Sub item selected
		else {
			directPlay(menuItem.get() + " " + "sélectionné");
			selected = menuItem.getCurrent();
			updateText(menuItem.get());
			ApplicationContext.setCurrentFilter(selected);
			submenu.reset();
		}
	}

	private void playAndUpdateText(String text) {
		updateText(text);
		play(text);
	}
	
	private void directPlay(String text) {
		SAPI5Player.getInstance().play(text);
	}

	private void play(String text) {
		String value = text;
		if (menuItem.getState() == MainMenuItem.MAIN) {
			if (menuItem.hasSubs(menuItem.get())) {
				value = text + " : sous-menu disponible";
			}
		}
		directPlay(value);
	}

	public void updateText(String text) {

		if (isItemSelected()) {
			textArea.setNoStroke(false);
			textArea.setStrokeColor(MTColor.GREEN);
			textArea.setStrokeWeight(3);
		} else {
			textArea.setNoStroke(true);
		}
		textArea.setText(text);
	}

	public void textVisible(boolean bool) {
		textArea.setVisible(bool);
	}
}
