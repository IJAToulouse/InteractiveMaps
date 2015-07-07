package org.ija.imaps.gui.menu;

import java.util.ArrayList;
import java.util.Collections;

import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.POI;
import org.ija.imaps.model.POIAction;
import org.ija.tools.tts.SAPI5Player;

public class SubMenu extends AbstractMenu<POIAction> {

	public SubMenu(int width, int height, int x, int y) {
		super(width, height, x, y);
	}

	public void down() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			currentIndex++;
			currentIndex = currentIndex % menuItem.size();
			menuDown();
		} else {
			SAPI5Player.getInstance().play(
					"Aucun sous-menu pour ce filtre");
		}
	}
	
	@Override
	protected void menuDown() {
		SAPI5Player.getInstance().play(
				menuItem.get(currentIndex).getData());
	}

	public void up() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			currentIndex--;
			if (currentIndex < 0) {
				currentIndex = menuItem.size() - 1;
			}
			menuUp();
		} else {
			SAPI5Player.getInstance().play(
					"Aucun sous-menu pour ce filtre");
		}
	}

	@Override
	protected void menuUp() {
		SAPI5Player.getInstance().play(
				menuItem.get(currentIndex).getData());
	}

	@Override
	protected void menuGet() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			SAPI5Player.getInstance().play(
					menuItem.get(currentIndex).getData());
		} else {
			SAPI5Player.getInstance().play(
					"Aucun sous-menu pour ce filtre");
		}
	}

	@Override
	protected void menuSelect() {
		if (ApplicationContext.getCurrentFilter().getExpandable()) {
			SAPI5Player.getInstance().play(
					"Glisser un doigt sur la carte pour trouvé"
							+ menuItem.get(currentIndex).getData());
			ApplicationContext.setGuidanceNewTarget(findPOI());
		} else {
			SAPI5Player.getInstance().play(
					"Aucun sous-menu pour ce filtre");
		}
	}

	private POI findPOI() {
		for (POI poi : ApplicationContext.getPOIs()) {
			if (poi.getActions().containsValue(menuItem.get(currentIndex))) {
				return poi;
			}
		}
		return null;
	}

	public void reset() {
		currentIndex = 0;

		// Get POIs for current filter
		menuItem = new ArrayList<POIAction>();
		if (ApplicationContext.getCurrentFilter().getExpandable()) {

			String filterId = ApplicationContext.getCurrentFilter().getId();

			for (POI poi : ApplicationContext.getPOIs()) {
				if (poi.getActions().containsKey(filterId)) {
					menuItem.add(poi.getActions().get(filterId));
				}
			}
			Collections.sort(menuItem);
			System.out.println(menuItem);
		}
	}

}
