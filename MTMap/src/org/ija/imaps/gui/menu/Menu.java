package org.ija.imaps.gui.menu;

public class Menu {

	private MainMenu mainMenu;
	private SubMenu subMenu;

	private static int MAIN_MENU_WIDTH = 192;
	private static int MAIN_MENU_HEIGHT = 525;

	private static int SUB_MENU_WIDTH = 192;
	private static int SUB_MENU_HEIGTH = 525;

	public Menu() {
		subMenu = new SubMenu(SUB_MENU_WIDTH, SUB_MENU_HEIGTH, 1584, 788);
		mainMenu = new MainMenu(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT, 1584, 262,
				subMenu);
	}
}
