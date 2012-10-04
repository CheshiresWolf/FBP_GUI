package orc.gg;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;

public class Node {
	mxCell node, port_in = null, port_out = null;
	public char type;
	/**
	 * Создание вершины с портами.
	 * @param graph - поверхность отрисовки.
	 * @param name - имя вершины.
	 * @param portIn - создавать ли входной порт.
	 * @param portOut - создавать ли выходной порт.
	 * @param x0 - х-координата начальной вершины.
	 * @param y0 - y-координата начальной вершины.
	 * @param x1 - х-координата конечной вершины.
	 * @param y1 - y-координата конечной вершины.
	 */
	Node(mxGraph graph, String name, boolean portIn, boolean portOut, int x0, int y0, int x1, int y1){
		Object parent = graph.getDefaultParent();
		this.type = name.charAt(0);
				
		node = (mxCell) graph.insertVertex(parent, null, name, x0, y0, x1, y1);
		node.setConnectable(false);

		if(portIn){
			mxGeometry geo_in = new mxGeometry(0, 0.5, 20, 20);
			geo_in.setOffset(new mxPoint(-10, -10));
			geo_in.setRelative(true);
	
			port_in = new mxCell(null, geo_in, "shape=ellipse;perimter=ellipsePerimeter");
			port_in.setVertex(true);
			
			graph.addCell(port_in, node);
		}
		
		if(portOut){
			mxGeometry geo_out = new mxGeometry(1, 0.5, 20, 20);
			geo_out.setOffset(new mxPoint(-10, -10));
			geo_out.setRelative(true);
	
			port_out = new mxCell(null, geo_out, "shape=ellipse;perimter=ellipsePerimeter");
			port_out.setVertex(true);
			
			graph.addCell(port_out, node);
		}
		
	}
	
}
