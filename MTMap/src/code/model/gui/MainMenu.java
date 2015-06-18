package code.model.gui;

import java.util.ArrayList;

import org.ija.tools.tts.SAPI5Player;

import code.model.ApplicationContext;
import code.model.Filter;

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
		SAPI5Player.getInstance().stopAndSpeak(
				menuItem.get(currentIndex).getName());
	}

	@Override
	protected void menuUp() {
		SAPI5Player.getInstance().stopAndSpeak(
				menuItem.get(currentIndex).getName());
	}

	@Override
	protected void menuGet() {
		SAPI5Player.getInstance().stopAndSpeak(
				menuItem.get(currentIndex).getName());
	}

	@Override
	protected void menuSelect() {
		SAPI5Player.getInstance().stopAndSpeak(
				menuItem.get(currentIndex).getName() + " " + "sélectionné");

		ApplicationContext.setCurrentFilter(menuItem
				.get(currentIndex));
		
		submenu.reset();
	}
}
