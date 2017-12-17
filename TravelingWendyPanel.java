/* */
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.*;
import com.mxgraph.util.*;


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
    
    /* Create vertex stylesheets */
    mxStylesheet stylesheet = graph.getStylesheet();
    Hashtable<String, Object> style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CYLINDER);
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
    style.put(mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.BLUE));
    style.put(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_NORTH); 
    style.put(mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.WHITE));
    stylesheet.putCellStyle("BUILDING", style);
    
    Hashtable<String, Object> style2 = new Hashtable<String, Object>();
    style2.put(mxConstants.STYLE_OPACITY, 50);
    style2.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    stylesheet.putCellStyle("INTERSECTION", style2);
    
    graph.getModel().beginUpdate();
    
    /* Plot all vertices */    
    Vector<Object> vertexObjects = new Vector<Object>(wendyGraph.vertices.size());
    int[] pixelCoors = new int[2];
    for (Node vertex : wendyGraph.vertices){
      pixelCoors = wendyGraph.getPixelCoordinates( Math.abs(vertex.getLat()), 
                                                   Math.abs(vertex.getLon()),
                                                   960,
                                                   720);
      System.out.printf("Plotting: [%d, %d]\n",pixelCoors[0],pixelCoors[1]);      
      
      Object v;
      if (vertex.getisBuilding()){
        v = graph.insertVertex(parent, null, vertex.getName(), 
                         pixelCoors[0], pixelCoors[1], 
                         vertex.getName().length() * 10, 30,
                                      "BUILDING");
      } else { //intersection
        v = graph.insertVertex(parent, null, vertex.getName(), 
                         pixelCoors[0], pixelCoors[1], 
                         20, 20,
                               "INTERSECTION");
      }
      
      vertexObjects.add( v );
    }
    /* Draw all edges */
    for (LinkedList<Edge> edgeList : wendyGraph.edges){
      for (Edge e : edgeList){
        Node n1 = e.getNode1();
        int index1 = wendyGraph.vertices.indexOf(n1);
        Node n2 = e.getNode2();
        int index2 = wendyGraph.vertices.indexOf(n2);
        
        graph.insertEdge(parent, null, e.getLengthFormatted(), 
                         vertexObjects.get(index1), vertexObjects.get(index2));
        System.out.printf("Drawing edge. . .\n");
      }
    }
        
//    Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
//                                   30);
//    Object v2 = graph.insertVertex(parent, null, "World!",
//                                   640, 400, 80, 30);
//    graph.insertEdge(parent, null, "Edge", v1, v2);  
    graph.getModel().endUpdate();    
    
    final mxGraphComponent graphComponent = new mxGraphComponent(graph);
    graphComponent.setEnabled(false);

    /*graphComponent.setSize(480, 360);*/ 

    add(graphComponent, gc);  
    
    /* Clickable cells */
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){      
      public void mouseReleased(MouseEvent e){
        Object cell = graphComponent.getCellAt(e.getX(), e.getY());        
        if (cell != null){
          System.out.println("cell="+graph.getLabel(cell));
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", new Object[]{cell});
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