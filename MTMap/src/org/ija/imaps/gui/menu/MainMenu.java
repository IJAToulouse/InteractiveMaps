package org.ija.imaps.gui.menu;

import java.util.ArrayList;

import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.Filter;
import org.ija.tools.tts.SAPI5Player;

public class MainMenu extends AbstractMenu<Filter> {

	private SubMenu submenu;

	public MainMenu(int width, int height, int x, int y, SubMenu submenu) {

		super(width, height, x, y);

		// Get filters
		menuItem = new ArrayList<Filter>(ApplicationContext.getFilters());
		updateText(menuItem.get(currentIndex).getName());
		this.submenu = submenu;
	}

	@Override
	protected void menuDown() {
		playAndUpdateText(menuItem.get(currentIndex).getName());
		submenu.setTextVisible(menuItem.get(currentIndex).getExpandable()
				&& isItemSelected());
	}

	@Override
	protected void menuUp() {
		playAndUpdateText(menuItem.get(currentIndex).getName());
		submenu.setTextVisible(menuItem.get(currentIndex).getExpandable()
				&& isItemSelected());
	}

	@Override
	protected void menuGet() {
		play(menuItem.get(currentIndex).getName());
	}

	@Override
	protected void menuSelect() {
		play(menuItem.get(currentIndex).getName() + " " + "sélectionné");
		updateText(menuItem.get(currentIndex).getName());

		ApplicationContext.setCurrentFilter(menuItem.get(currentIndex));
		submenu.reset();
	}

	private void playAndUpdateText(String text) {
		updateText(text);
		play(text);
	}

	private void play(String text) {
		SAPI5Player.getInstance().play(text);
	}
}
