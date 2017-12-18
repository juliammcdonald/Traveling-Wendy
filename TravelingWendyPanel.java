/********************************************************************
* TravelingWendyPanel.java
* Constructs the TravelingWendyPanel and controls all of its activity and 
* interaction with WendyGraph
* 
* @author Xinhui Xu, Julia McDonald
* @date Dec 18, 2017
********************************************************************/
import java.util.*;
import java.util.concurrent.TimeUnit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/*----------jGraph library---------*/
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.*;
import com.mxgraph.util.*;

public class TravelingWendyPanel extends JPanel /*implements ChangeListener*/ {
  /*----------Instance variables----------*/
  private JLabel selectLabel, mapLabel;
  private JButton resetButton, toggleDistanceButton;
  private WendyGraph wendyGraph;
  private String[] selectedNodes;
  private Object[] coloredCells;
  private Vector<Object> vertexObjects;
  private boolean toggleDistance;
  
  //necessary for mxGraph function
  private mxGraph graph; private Object parent; private mxGraphComponent graphComponent;

  
  /*----------Constructor @Xinhui----------
   * @param graphViewportWidth - width of the graph component
   * @param graphViewportHeight - height of the graph component
   */  
  public TravelingWendyPanel(int graphViewportWidth, int graphViewportHeight){
    
    wendyGraph = new WendyGraph( "wellesleycoord.txt" );    
    selectedNodes = new String[2];    
    selectLabel = new JLabel("Select origin");
    selectLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
    mapLabel = new JLabel("This is the map placeholder");
    toggleDistance = true;

    /*------------Initialize GridBagLayout-------------*/
    setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
         
    gc.weightx = 0.5;
    gc.weighty = 0.5;
    
    gc.gridx = 0;
    gc.gridy= 0;    
        
    /*----------Create graph and graph stylesheets----------*/
    graph = new mxGraph();
    parent = graph.getDefaultParent();
    
    mxStylesheet stylesheet = graph.getStylesheet();
    Hashtable<String, Object> style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_OPACITY, 75);
    style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
    style.put(mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.BLUE));
    style.put(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_NORTH); 
    style.put(mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.WHITE));
    stylesheet.putCellStyle("BUILDING", style);
    
    Hashtable<String, Object> style2 = new Hashtable<String, Object>();
    style2.put(mxConstants.STYLE_OPACITY, 75);
    style2.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    //style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
    stylesheet.putCellStyle("INTERSECTION", style2);
    
    graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_STROKECOLOR, "#d6e5ff");
    graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_FONTCOLOR, "#d6e5ff");
    
    graph.getModel().beginUpdate();
    
    /*----------Plot all vertices----------*/    
    vertexObjects = new Vector<Object>(wendyGraph.vertices.size());
    int[] pixelCoors = new int[2];
    for (Node vertex : wendyGraph.vertices){
      pixelCoors = wendyGraph.getPixelCoordinates( Math.abs(vertex.getLat()), 
                                                  Math.abs(vertex.getLon()),
                                                  graphViewportWidth,
                                                  graphViewportHeight);
      System.out.printf("Plotting: [%d, %d]\n",pixelCoors[0],pixelCoors[1]);      
      
      Object v;
      if (vertex.getisBuilding()){
        v = graph.insertVertex(parent, null, vertex.getName(), 
                               pixelCoors[0], pixelCoors[1], 
                               vertex.getName().length() * 10 - 10, 20,
                               "BUILDING");
      } else { //intersection
        v = graph.insertVertex(parent, null, "", 
                               pixelCoors[0], pixelCoors[1], 
                               7, 7,
                               "INTERSECTION");
      }      
      vertexObjects.add( v );
    }
    /*----------Draw all edges----------*/
    for (LinkedList<Edge> edgeList : wendyGraph.edges){
      for (Edge e : edgeList){
        Node n1 = e.getNode1();
        int index1 = wendyGraph.vertices.indexOf(n1);
        Node n2 = e.getNode2();
        int index2 = wendyGraph.vertices.indexOf(n2);
        
        graph.insertEdge(parent, null, e.getLengthFormatted(0), 
                         vertexObjects.get(index1), vertexObjects.get(index2),
                         "endArrow=None;");
        
        System.out.printf("Drawing edge. . .\n");
      }
    }
    
    /*-------------Add graph component to panel--------------*/  
    graph.getModel().endUpdate();    
    
    graphComponent = new mxGraphComponent(graph);
    graphComponent.setEnabled(false);    
    add(graphComponent, gc);  
    
    /*----------Click handler, only active on cells----------*/  
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){      
      public void mouseReleased(MouseEvent e){
        Object cell = graphComponent.getCellAt(e.getX(), e.getY());
        
        if ((cell != null) || (selectedNodes[0] == null) || (selectedNodes[1] == null)) {
          System.out.println("cell="+graph.getLabel(cell));
          //System.out.printf("selectedNodes: %s %s\n", selectedNodes[0], selectedNodes[1]);
          
          /*----------Origin has not been selected-----------*/
          if ( selectedNodes[0] == null ){            
            selectedNodes[0] = graph.getLabel(cell);
            selectLabel.setText("Select destination");
            coloredCells = new Object[]{cell};
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", coloredCells);
            graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", coloredCells);
          
          /*--------Origin is already selected, but not destination----------*/
          } else if ( selectedNodes[1] == null ){ 
            selectedNodes[1] = graph.getLabel(cell);
            graphComponent.refresh();
            selectLabel.setText("If you see this, something went wrong. Try 'Reset' or reload the program.");
            
            /*----------Call Dijkstra method----------*/
            ArrayList<Node> shortestPath = wendyGraph.runDijkstra(selectedNodes[0], selectedNodes[1]);
            selectLabel.setText("The shortest path is: "+ shortestPath.toString());
            
            /*---------Color nodes and paths visited, store in Object[] coloredCells----------*/
            coloredCells = new Object[shortestPath.size()];
            int j = 0;
            for (Node n : shortestPath) {
              int i = wendyGraph.vertices.indexOf(n);
              coloredCells[j] = vertexObjects.get(i);
              j++;
            }            
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", coloredCells);
            graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", coloredCells);
            
            graph.getModel().beginUpdate();
            try {
              for (j = 0; j < (coloredCells.length - 1); j++){
                Object[] edge = graph.getEdgesBetween(coloredCells[j], coloredCells[j+1]);
                graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", edge);
                graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "4", edge);
              }
            } finally {
              graph.getModel().endUpdate();
              graphComponent.refresh();
            }
          }
        }
      }
    }); /*----------End clickhandler-----------*/    
    
    /*----------Display labels and button----------*/
    gc.weighty = 0.01;    
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridy = 1;
    add(selectLabel, gc);
    
    gc.gridy++;
    
    resetButton = new JButton("Reset");
    resetButton.addActionListener(new ButtonListener());
    toggleDistanceButton = new JButton("Toggle distance[meters] display");
    toggleDistanceButton.addActionListener(new ButtonListener());
    

    JPanel panel = new JPanel(new GridLayout(1,2)); // 1 row, 2 cols
    panel.add(resetButton);
    panel.add(toggleDistanceButton);
    add(panel, gc);
    
    /*----------Set and Scale Background @author : Julia----------*/

    ImageIcon image = new ImageIcon("wellesleyBG3.png");
    graphComponent.setBackgroundImage(image);

  }
  
  /*----------Button handler @author : Xinhui----------*/
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
      if (e.getSource() == resetButton){           
        selectLabel.setText("Select origin");
         
        /*----------Reset any selection-------*/
        graph.getModel().beginUpdate();        
        try {
          /*----------Reset edge style------------*/
          if (coloredCells != null){
            for (int j = 1; j < (coloredCells.length); j++){
              Object[] edge = graph.getEdgesBetween(coloredCells[j-1], coloredCells[j]);
              graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "", edge);
              graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1", edge);
            }
          }
          /*----------Reset node style------------*/
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "", coloredCells);
          graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "", coloredCells);
            
          coloredCells = null;
          selectedNodes = new String[2];

        } finally {
          graph.getModel().endUpdate();
          graphComponent.refresh();
        }
      /*------------Toggle distance display------------*/
      } else if (e.getSource() == toggleDistanceButton){
        
       graph.getModel().beginUpdate();
       try {
         if (toggleDistance == true) {
           graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
           toggleDistance = false;
         } else {
           graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "0");
           toggleDistance = true;
         }
       } finally {
         graph.getModel().endUpdate();
         graphComponent.refresh();
       }
      }
    }
  }
}


