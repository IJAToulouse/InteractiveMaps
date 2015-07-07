package org.ija.imaps.model;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.ija.imaps.gui.MapContainer;
import org.ija.imaps.gui.shape.GraphicalPOI;
import org.mt4j.util.math.Vector3D;

@XmlRootElement
public class POI {

	@XmlAttribute
	private String id;
	
	@XmlAttribute
	private String name;
	
	private boolean computed = false;
	private GraphicalPOI gpoi;
	private HashMap<String, POIAction> actions = new HashMap<String, POIAction>();

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public HashMap<String, POIAction> getActions() {
		if (!computed) {
			Character allSep = ';';
			Character actionSep = '#';
			String[] sActions = StringUtils.split(name, allSep);

			// For each actions
			for (int i = 0; i < sActions.length; i++) {
				String[] action = StringUtils.split(sActions[i], actionSep);

				if (action.length != 3) {
					System.out.println("Erreur pour l'action " + actions);
				}
				actions.put(action[0], new POIAction(action[1], action[2]));
			}
			computed = true;
		}
		return actions;
	}

	@Override
	public String toString() {
		return "POI [id=" + id + ", name=" + name + "]";
	}

	public void setGraphicalPOI(GraphicalPOI gpoi) {
		this.gpoi = gpoi;
	}

	public Vector3D getGlobalPosition() {
		return gpoi.getCenterPointGlobal();
	}

	public void sendToFront() {
		gpoi.sendToFront();
	}

	public void addToParent(MapContainer container) {
		container.addChild(gpoi.getShape());
	}

}
