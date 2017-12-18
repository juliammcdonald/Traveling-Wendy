/*****************************************************************
  * Edge.java
  * Creates a weighted Edge between two Nodes
  * @author Julia McDonald, Xinhui Xu
  * @date Dec 18, 2017
  ****************************************************************/
import java.text.DecimalFormat;

public class Edge {
  
  protected double length;
  protected Node node1;
  protected Node node2;
  
  /**@author Xinhui
    * 
    * Creates a weighted Edge between two Nodes by setting the length equal to the
    * linear distance between the two lat/long coordinates.
    * 
    * @param n1 - Node #1
    * @param n2 - Node #2
    */
  public Edge( Node n1, Node n2 ) {
    length = getGreatCircleDistance(n1.getLat(), n1.getLon(), n2.getLat(), n2.getLon());
    node1 = n1;
    node2 = n2;
  }
  
  /* @author Xinhui
   * 
   * The 'haversine' formula to find distance in meter given the latitude&longitude of two points, 
   * referenced from https://www.movable-type.co.uk/scripts/latlong.html 
   * @param lat1 - latitude of first point.
   * @param lon1 - longitude of first point.
   * @param lat2 - latitude of 2nd point.
   * @param lon2 - longitude of 2nd point.
   * @return - the distance in meters.
   */
  public static double getGreatCircleDistance(double lat1, double lon1, double lat2, double lon2){
    double R = 6317E3; //Radius of Earth!
    double phi1 = Math.toRadians(lat1);
    double phi2 = Math.toRadians(lat2);
    double dPhi = Math.toRadians(lat2-lat1);
    double dLam = Math.toRadians(lon2-lon1);
    double a = Math.sin(dPhi / 2.0) * Math.sin(dPhi / 2.0) +
      Math.cos(phi1) * Math.cos(phi2) *
      Math.sin(dLam / 2.0) * Math.sin(dLam / 2.0);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double d = R * c;
    return d;     
  }
  
  /*@author Julia
   * Gets length
   * @return length
   */
  public double getLength(){
    return length;    
  }
  
  /*@author Xinhui
   * Gets length formatted to numDigitsAfterDecimal
   * @return string containing the formatted length
   */
  public String getLengthFormatted(int numDigitsAfterDecimal){
    DecimalFormat df = new DecimalFormat();
    df.setMaximumFractionDigits(numDigitsAfterDecimal);
    return df.format(getLength());
  }
  
  /*@author Julia
   * Gets node1
   * @return node1
   */
  public Node getNode1() {
    return node1;
  }
  
  /*@author Julia
   * Gets node2
   * @return node2
   */
  public Node getNode2() {
    return node2;
  }
  
  /*@author Julia
   * Gets Edge's unknown node (either node 1 or node 2)
   * 
   * @return node that is not known
   */
  public Node getOtherNode( Node known ) {
    if( known.equals( node1 ) )
      return node2;
    return node1;
  }
  
  /**@author Julia
   * Creates string representation of the Edge (both nodes)
   * 
   * @return string (node1, node2) representing the Edge
   */
  public String toString() {
    return "(" + node1 + ", " + node2 + ")";
  }
}