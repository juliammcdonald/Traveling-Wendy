/* */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class TravelingWendyPanel extends JPanel /*implements ChangeListener*/ {
  /* Instance variables */
  JLabel selectLabel, mapLabel, resultLabel;
  WendyGraph wendyGraph;
  
  /* Constructor */  
  public TravelingWendyPanel(){
    
    wendyGraph = new WendyGraph( "wellesleycoord.txt" );
    /*Extract all longitudes*/
    
    
    /* [TODO] Dynamic label: set to select destination after click */
    selectLabel = new JLabel("Select origin building");
    
    resultLabel = new JLabel("The shortest path is: ");
    mapLabel = new JLabel("This is the map placeholder XD");
    
    setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    
    /* Map component*/    
    gc.weightx = 0.5;
    gc.weighty = 0.5;
    
    gc.gridx = 0;
    gc.gridy= 0;
    
    /* From ClickHandler.java */
    final mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
 
    graph.getModel().beginUpdate();
    
    int[] pixelCoors = new int[2];
    for (Node vertex : wendyGraph.vertices){
      pixelCoors = wendyGraph.getPixelCoordinates( Math.abs(vertex.getLat()), 
                                                   Math.abs(vertex.getLon()),
                                                   750,
                                                   520);
      System.out.printf("[%d, %d]\n",pixelCoors[0],pixelCoors[1]);
      graph.insertVertex(parent, null, vertex.getName(), pixelCoors[0], pixelCoors[1], 30, 30);
    }
        
//    Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
//                                   30);
//    Object v2 = graph.insertVertex(parent, null, "World!",
//                                   640, 400, 80, 30);
//    graph.insertEdge(parent, null, "Edge", v1, v2);  
    graph.getModel().endUpdate();    
    
    final mxGraphComponent graphComponent = new mxGraphComponent(graph);
    /*graphComponent.setSize(480, 360);*/ 

    add(graphComponent, gc);  
    
    /* Clickable cells */
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){      
      public void mouseReleased(MouseEvent e){
        Object cell = graphComponent.getCellAt(e.getX(), e.getY());        
        if (cell != null){
          System.out.println("cell="+graph.getLabel(cell));
        }
      }
    });
    
    /* Display labels */
    gc.weighty = 0.01;    
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridy = 1;
    add(selectLabel, gc);
    gc.gridy = 2;
    add(resultLabel, gc);
  }
  
  
}