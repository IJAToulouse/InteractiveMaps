package org.ija.imaps.gui.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ija.imaps.model.ApplicationContext;
import org.ija.imaps.model.Filter;

public class MainMenuItem {

	public static final int MAIN = 0;
	public static final int SUBS = 1;

	private int currentMain;
	private int currentSub;
	private int state = 0;

	private Map<String, List<String>> mapMenus = new LinkedHashMap<String, List<String>>();
	private Map<Integer, String> mainMenus = new HashMap<Integer, String>();

	public MainMenuItem() {
		init();
	}	

	private void init() {
		mapMenus.clear();
		for (Filter filter : ApplicationContext.getFilters()) {
			add(filter.getName());
		}

		// compute index
		mainMenus.clear();
		int i = 0;
		for (String key : mapMenus.keySet()) {
			mainMenus.put(i++, key);
		}
		state = MAIN;
		currentMain = 0;
		currentSub = 0;
	}
	
	private void add(String value) {

		String[] split = StringUtils.split(value, "/");

		String main = split[0];
		String sub = null;

		if (split.length == 2) {
			sub = split[1];
		}

		if (!mapMenus.containsKey(main)) {
			mapMenus.put(main, new ArrayList<String>());
		}
		if (StringUtils.isNotEmpty(sub)) {
			mapMenus.get(main).add(sub);
		}
	}

	@Override
	public String toString() {
		String result = "state=" + state + " main=" + getCurrentMain();

		if (state == SUBS) {
			result += " sub=" + getCurrentSub();
		}

		return result;
	}

	protected boolean hasSubs(String main) {
		return !mapMenus.get(main).isEmpty();
	}

	protected String getCurrentMain() {
		return mainMenus.get(currentMain);
	}

	protected String getCurrentSub() {
		return mapMenus.get(mainMenus.get(currentMain)).get(currentSub);
	}

	protected String get() {
		return state == MAIN ? getCurrentMain() : getCurrentSub();
	}

	protected int getState() {
		return state;
	}

	protected void down() {
		switch (state) {
		case MAIN:
			currentMain++;
			currentMain = currentMain % mainMenus.keySet().size();
			break;
		case SUBS:
			currentSub++;
			currentSub = currentSub % mapMenus.get(getCurrentMain()).size();
			break;
		default:
			break;
		}
	}

	public void setState(int state) {
		this.state = state;
		if (state == SUBS) {
			currentSub = 0;
		}
	}

	public void up() {
		switch (state) {
		case MAIN:
			if (--currentMain < 0) {
				currentMain = mainMenus.keySet().size() - 1;
			}
			break;
		case SUBS:
			if (--currentSub < 0) {
				currentSub = mapMenus.get(getCurrentMain()).size() - 1;
			}
			break;
		default:
			break;
		}
	}

	public String getCurrent() {
		switch (state) {
		case MAIN:
			return (getCurrentMain());
		case SUBS:
			return (getCurrentMain() + "/" + getCurrentSub());
		}
		return null;
	}
}
