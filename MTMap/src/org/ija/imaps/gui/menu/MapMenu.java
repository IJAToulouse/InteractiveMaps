package org.ija.imaps.gui.menu;

import org.ija.imaps.model.ApplicationContext;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.gestureAction.DefaultDragAction;
import org.mt4j.input.gestureAction.InertiaDragAction;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

public class MapMenu extends MTRectangle {

	private MainMenu mainMenu;
	private SubMenu subMenu;

	public MapMenu() {

		super(ApplicationContext.getScene().getMTApplication(),
				ApplicationContext.getScreenManager().getMenuWidthPx(),
				ApplicationContext.getScreenManager().getMapHeightPx());

		ApplicationContext.clearAllGestures(this);
		ApplicationContext.getMainContainer().addChild(this);
		setPositionRelativeToParent(new Vector3D(ApplicationContext
				.getScreenManager().getMapWidthPx()
				+ (ApplicationContext.getScreenManager().getMenuWidthPx() / 2),
				ApplicationContext.getScreenManager().getMapHeightPx() / 2, 0));
		setFillColor(MTColor.BLACK);
		setStrokeColor(MTColor.BLACK);
		registerInputProcessor(new DragProcessor(ApplicationContext.getScene()
				.getMTApplication()));
		addGestureListener(DragProcessor.class, new DefaultDragAction());
		addGestureListener(DragProcessor.class, new InertiaDragAction());
	}

	public void createSubMenus() {

		int width = ApplicationContext.getScreenManager().getMenuWidthPx();
		int height = ApplicationContext.getScreenManager().getMapHeightPx();

		if (subMenu != null) {
			this.removeChild(subMenu);
		}
		if (mainMenu != null) {
			this.removeChild(mainMenu);
		}

		subMenu = new SubMenu(width, height/2, width / 2, height * 3 / 4);
		mainMenu = new MainMenu(width, height/2, width / 2, height / 4, subMenu);

		this.addChild(subMenu);
		this.addChild(mainMenu);
	}
}
