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
		this.submenu = submenu;
	}

	@Override
	protected void menuDown() {
		SAPI5Player.getInstance().play(
				menuItem.get(currentIndex).getName());
	}

	@Override
	protected void menuUp() {
		SAPI5Player.getInstance().play(
				menuItem.get(currentIndex).getName());
	}

	@Override
	protected void menuGet() {
		SAPI5Player.getInstance().play(
				menuItem.get(currentIndex).getName());
	}

	@Override
	protected void menuSelect() {
		SAPI5Player.getInstance().play(
				menuItem.get(currentIndex).getName() + " " + "sélectionné");

		ApplicationContext.setCurrentFilter(menuItem
				.get(currentIndex));
		
		submenu.reset();
	}
}
