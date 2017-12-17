/* */
import javax.swing.*;
import java.awt.*;

public class TravelingWendyGUI {
  /**
   * Driver method
   */
  public static void main (String[] args){
    JFrame frame = new JFrame("Traveling Wendy");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setMaximumSize(new java.awt.Dimension(480, 360));
    //frame.setMinimumSize(new java.awt.Dimension(480, 360));
    
    
    frame.setPreferredSize(new java.awt.Dimension(1280, 960));
    //frame.setResizable(false);
       
    //TravelingWendyPanel panel = new TravelingWendyPanel(825, 600);
    
    TabbedPanePanel panel = new TabbedPanePanel();
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true);
  }
}
