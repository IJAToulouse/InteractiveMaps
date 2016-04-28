package org.ija.imaps.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.ija.imaps.gui.shape.GraphicalPOI;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.math.Vector3D;

@XmlRootElement
public class POI {

	@XmlAttribute
	private String id;

	@XmlAttribute
	private float x;

	@XmlAttribute
	private float y;

	@XmlAttribute
	private float width;

	@XmlAttribute
	private float height;

	@XmlAttribute
	private String name;

	@XmlElementWrapper
	@XmlElements({ @XmlElement(name = "action", type = Action.class) })
	private List<Action> actions = null;

	private GraphicalPOI gpoi;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Action> getActions() {
		return actions;
	}

	public Action getAction(String filterId) {
		Action result = null;
		for (Action action : actions) {
			if (filterId.equals(action.getFilter())) {
				result = action;
				break;
			}
		}
		return result;
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

	public void addToParent(MTRectangle container) {
		container.addChild(gpoi.getShape());
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "POI [id=" + id + ", name=" + name + ", actions=" + actions
				+ ", gpoi=" + gpoi + "]";
	}
}
