package org.ijatoulouse.map.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mt4j.AbstractMTApplication;
import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTPolygon;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

public class CreatePolygonApplication extends MTApplication {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		initialize();
	}

	@Override
	public void startUp() {
		addScene(new CreatePolygonScene(this, "Map Scene"));
	}
	
	class CreatePolygonScene extends AbstractScene {
		
		public CreatePolygonScene(AbstractMTApplication app, String name) {
			super(app, name);
			setClearColor(new MTColor(255, 255, 255, 255));
			addPolygon();
			addPolygonFromSvgPath("M150 0 L75 200 L225 200 Z");
		}

		private void addPolygon() {
			
			Vertex vertex1 = new Vertex(new Vector3D(10, 0));
			Vertex vertex2 = new Vertex(new Vector3D(10, 100));
			Vertex vertex3 = new Vertex(new Vector3D(100, 100));
			Vertex vertex4 = new Vertex(new Vector3D(150, 50));
			Vertex [] vertices = new Vertex[] {vertex1, vertex2, vertex3, vertex4, vertex1};
			MTPolygon polygon = new MTPolygon(CreatePolygonApplication.this, vertices);
			polygon.setStrokeColor(MTColor.BLACK);
			polygon.registerInputProcessor(new TapProcessor(CreatePolygonApplication.this, 25, true, 350));
			polygon.addGestureListener(TapProcessor.class, new IGestureEventListener() {
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent)ge;
					if (te.isDoubleTap()){
						System.out.println("Yes");
					}
					return false;
				}
			});
			getCanvas().addChild(polygon);
		}
		
		private void addPolygonFromSvgPath(String path) {
			
			List<Vertex> vertices = new ArrayList<Vertex>();
					
			Character sep = ' ';
			String[] values = StringUtils.split(path, sep);
			
			String current = values[0];
			int i = 0;
			while (!"Z".equals(current)) {
				//http://www.w3schools.com/svg/svg_path.asp
				String x = current.replaceAll("[MLHVCSQTAZ]", "");
				String y = values[++i];
				
				vertices.add(new Vertex(Float.valueOf(x),Float.valueOf(y)));
				
				current = values[++i];
			}
			vertices.add(vertices.get(0));
			
			MTPolygon polygon = new MTPolygon(CreatePolygonApplication.this, vertices.toArray(new Vertex[vertices.size()]));
			polygon.setStrokeColor(MTColor.BLACK);
			polygon.registerInputProcessor(new TapProcessor(CreatePolygonApplication.this, 25, true, 350));
			polygon.addGestureListener(TapProcessor.class, new IGestureEventListener() {
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent)ge;
					if (te.isDoubleTap()){
						System.out.println("Yes");
					}
					return false;
				}
			});
			getCanvas().addChild(polygon);
		}
	}	
}