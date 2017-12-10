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
    
    //TravelingWendy t = new TravelingWendy();
    
    TravelingWendyPanel panel = new TravelingWendyPanel(/*t*/);
    frame.setSize(600, 300);
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true);
  }
}
