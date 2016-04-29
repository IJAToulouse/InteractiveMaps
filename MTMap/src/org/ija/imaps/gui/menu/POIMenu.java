package org.ija.imaps.gui.menu;

import java.util.ArrayList;
import java.util.Collections;

import org.ija.imaps.model.Action;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.ija.tools.tts.SAPI5Player;

public class POIMenu extends AbstractMenu<Action> {

	public POIMenu(int width, int height, int x, int y) {
		super(width, height, x, y);
		textVisible(false);
	}

	public void down() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			super.down();
		} else {
			SAPI5Player.getInstance().play("Aucun sous-menu pour ce filtre");
		}
	}

	public void up() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			super.up();
			menuUp();
		} else {
			SAPI5Player.getInstance().play("Aucun sous-menu pour ce filtre");
		}
	}

	@Override
	protected void menuDown() {
		SAPI5Player.getInstance().play(menuItem.get(currentIndex).getValue());
		updateText(menuItem.get(currentIndex).getValue());
	}

	@Override
	protected void menuUp() {
		SAPI5Player.getInstance().play(menuItem.get(currentIndex).getValue());
		updateText(menuItem.get(currentIndex).getValue());
	}

	@Override
	protected void menuGet() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			SAPI5Player.getInstance().play(
					menuItem.get(currentIndex).getValue());
		} else {
			SAPI5Player.getInstance().play("Aucun sous-menu pour ce filtre");
		}
	}

	@Override
	protected void menuSelect() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			SAPI5Player.getInstance().play(
					"Glisser un doigt sur la carte pour trouver"
							+ menuItem.get(currentIndex).getValue());
			updateText(menuItem.get(currentIndex).getValue());
			ApplicationContext.setGuidanceNewTarget(findPOI());
		} else {
			SAPI5Player.getInstance().play("Aucun sous-menu pour ce filtre");
		}
	}

	private POI findPOI() {
		for (POI poi : ApplicationContext.getPOIs()) {
			if (poi.getActions().contains(menuItem.get(currentIndex))) {
				return poi;
			}
		}
		return null;
	}

	public void reset() {
		currentIndex = 0;
		selectedIndex = -1;

		// Get POIs for current filter
		menuItem = new ArrayList<Action>();
		if (ApplicationContext.getCurrentFilter().getExpandable()) {

			String filterId = ApplicationContext.getCurrentFilter().getId();

			for (POI poi : ApplicationContext.getPOIs()) {
				Action temp = poi.getAction(filterId);
				if (temp != null) {
					menuItem.add(temp);
				}
			}
			Collections.sort(menuItem);
			updateText(menuItem.get(currentIndex).getValue());
			setTextVisible(true);
		}
	}

	public void setTextVisible(boolean bool) {
		textVisible(bool);
	}
}
