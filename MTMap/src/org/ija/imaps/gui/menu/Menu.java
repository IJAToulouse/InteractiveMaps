package org.ija.imaps.gui.menu;

import org.ija.imaps.model.ApplicationContext;

public class Menu {

	private MainMenu mainMenu;
	private SubMenu subMenu;

	private static int MAIN_MENU_WIDTH = 192;
	private static int MAIN_MENU_HEIGHT = 525;

	private static int SUB_MENU_WIDTH = 192;
	private static int SUB_MENU_HEIGTH = 525;

	public Menu() {
	}
	
	public void create() {
		
		if (subMenu != null) {
			ApplicationContext.getScene().getCanvas().removeChild(subMenu);
		}
		if (mainMenu != null) {
			ApplicationContext.getScene().getCanvas().removeChild(mainMenu);
		}
		
		subMenu = new SubMenu(SUB_MENU_WIDTH, SUB_MENU_HEIGTH, 1584, 788);
		mainMenu = new MainMenu(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT, 1584, 262,
				subMenu);
		
		ApplicationContext.getScene().getCanvas().addChild(subMenu);
		ApplicationContext.getScene().getCanvas().addChild(mainMenu);
	}
}
