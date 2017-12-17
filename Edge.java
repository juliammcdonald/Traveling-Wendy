import java.text.DecimalFormat;

/**
 * Creates a weighted Edge between two Nodes
 */
public class Edge {
  
  protected double length;
  protected Node node1;
  protected Node node2;
  
  /**
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
  
  /* The 'haversine' formula, referenced from https://www.movable-type.co.uk/scripts/latlong.html */
  public static double getGreatCircleDistance(double lat1, double lon1, double lat2, double lon2){
    double R = 6317E3;
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
  
  public double getLength(){
    return length;    
  }
  public String getLengthFormatted(){
   DecimalFormat df = new DecimalFormat();
   df.setMaximumFractionDigits(0);
   return df.format(getLength());
  }
//  public double getLength() {
//    return length;
//  }
  
  public Node getNode1() {
    return node1;
  }
  
  public Node getNode2() {
    return node2;
  }
  
  public String toString() {
    return "(" + node1 + ", " + node2 + ")";
  }
}