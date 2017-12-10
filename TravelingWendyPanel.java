/* */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TravelingWendyPanel extends JPanel /*implements ChangeListener*/ {
  /* Instance variables */
  JLabel selectLabel, mapLabel, resultLabel;
  
  /* Constructor */  
  public TravelingWendyPanel(/*TravelingWendy t*/){
    /* Set size of GUI window */
    Dimension size = getPreferredSize();
    size.width = 480;
    size.height = 360;
    setPreferredSize(size);
    
    /* [TODO] Dynamic label: set to select destination after click */
    selectLabel = new JLabel("Select origin building");
    
    resultLabel = new JLabel("The shortest path is: ");
    mapLabel = new JLabel("This is the map placeholder XD");
    
    setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    
    /* Leftmost column (controls) */    
    gc.weightx = 0.5;
    gc.weighty = 0.5;
    
    gc.gridx = 0;
    gc.gridy= 0;    
    add(selectLabel, gc);
    
    gc.weightx = 20;
    gc.gridx = 1;
    gc.gridy = 0;
    add(resultLabel, gc);
    
    gc.anchor = GridBagConstraints.FIRST_LINE_START;
    gc.gridy = 1;
    gc.gridx = 0;
    gc.weighty = 10;
    add(mapLabel, gc);
    
  }
  
}