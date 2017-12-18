/********************************************************************
* TravelingWendyGUI.java
* 
* Driver to start the Traveling Wendy app,
* which finds the shortest path between any two buildings or road intersections
* on the Wellesley College campus.
* 
* 
* @author Xinhui Xu, Julia McDonald
* @date Dec. 18, 2017
********************************************************************/
import javax.swing.*;
import java.awt.*;

public class TravelingWendyGUI {
  /**
   * Driver method for the Project
   */
  public static void main (String[] args){
    JFrame frame = new JFrame("Traveling Wendy");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
    frame.setPreferredSize(new java.awt.Dimension(1280, 960));
    
    //Creates a TabbedPanePanel object and adds it to the GUI.

    TabbedPanePanel panel;
    if( args.length == 2 ) {
      panel = new TabbedPanePanel(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
    else {
      panel = new TabbedPanePanel(825, 570);
    }
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true);
  }
}
