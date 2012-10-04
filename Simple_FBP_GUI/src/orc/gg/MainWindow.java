package orc.gg;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import orc.gg.FBP.Experiment;

/**
 * @author cheshire
 */
public class MainWindow extends JFrame {

	private JPanel contentPane;
	private mxGraph graph;
	ArrayList<Node> nodes = new ArrayList<Node>();
	
	int component_number = 3;
	
	final int PORT_DIAMETER = 20;

	final int PORT_RADIUS = PORT_DIAMETER / 2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	mxGraphComponent createMXGraph(){
		
		graph = new mxGraph() {
			
			// Ports are not used as terminals for edges, they are
			// only used to compute the graphical connection point
			public boolean isPort(Object cell)
			{
				mxGeometry geo = getCellGeometry(cell);
				
				return (geo != null) ? geo.isRelative() : false;
			}
			
			// Implements a tooltip that shows the actual
			// source and target of an edge
			public String getToolTipForCell(Object cell)
			{
				if (model.isEdge(cell))
				{
					return convertValueToString(model.getTerminal(cell, true)) + " -> " +
						convertValueToString(model.getTerminal(cell, false));
				}
				
				return super.getToolTipForCell(cell);
			}
			
			// Removes the folding icon and disables any folding
			public boolean isCellFoldable(Object cell, boolean collapse)
			{
				return false;
			}
			
			public boolean isCellMovable(Object cell)
			{
				return false;
			}
		};
		
		// Sets the default edge style
		Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
		style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.ElbowConnector);
		
		//Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try	{
			nodes.add(new Node(graph, "Generate", false, true, 20, 150, 80, 30));
			
			nodes.add(new Node(graph, "Summator", true, true, 190, 150, 80, 30));
			
			nodes.add(new Node(graph, "PrintResut", true, false, 330, 150, 80, 30));
			
			graph.insertEdge(graph.getDefaultParent(), null, "", nodes.get(0).port_out, nodes.get(1).port_in);
			graph.insertEdge(graph.getDefaultParent(), null, "", nodes.get(1).port_out, nodes.get(2).port_in);
		}finally{
			graph.getModel().endUpdate();
		}
		
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
		//Листенер мышки
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){	
			public void mouseReleased(MouseEvent e){
				
				if(e.getButton() == MouseEvent.BUTTON1){
					if(component_number != 5){
						graph.getModel().beginUpdate();
						try	{
							//Удаляем связь
							int n = nodes.size()-1;
							Object[] edges = graph.getEdgesBetween(nodes.get(n-1).node, nodes.get(n).node);
							
		                    for(Object edge: edges){
		                        graph.getModel().remove(edge);
		                    }
		                    //Смещаем последнюю вершину
		                    Object[] buf = {nodes.get(n).node};
		                    /*for(int i=0; i<buf.length; i++){
		                    	buf[i] = nodes.get(i+2).node;
		                    }*/
		                    graph.moveCells(buf, 0, 60);
		
		                    //Вставляем вершину
		                    int shift = (component_number-2)*60;
							nodes.add(n, new Node(graph, "Mul("+component_number+")", true, true, 190, 150+shift, 80, 30));
							//Соеденяем вершины (n изменилось)
							graph.insertEdge(graph.getDefaultParent(), null, "", nodes.get(n-1).port_out, nodes.get(n).port_in);
							graph.insertEdge(graph.getDefaultParent(), null, "", nodes.get(n).port_out, nodes.get(n+1).port_in);
							
							component_number++;
						}finally{
							graph.getModel().endUpdate();
						}
					}
				}
				//Запуск подсчета сети
				if(e.getButton() == MouseEvent.BUTTON3){
					try {
						new Experiment(nodes).go();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		return graphComponent;
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(createMXGraph());
		
		setContentPane(contentPane);
	}

}
