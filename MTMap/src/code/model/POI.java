package code.model;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.mt4j.util.math.Vector3D;

import code.model.gui.GraphicalPOI;

@XmlRootElement(name = "poi")
public class POI {

	private String id;
	private String name;
	private boolean computed = false;
	private GraphicalPOI gpoi;
	private HashMap<String, POIAction> actions = new HashMap<String, POIAction>();

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
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
				
				if (action.length!=3) {
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

}
