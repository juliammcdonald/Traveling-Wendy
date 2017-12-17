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
    length = Math.sqrt( Math.pow( Math.abs( n1.getLat() - n2.getLat() ), 2 ) + 
              Math.pow( Math.abs( n1.getLon() - n2.getLon() ), 2 ));
    node1 = n1;
    node2 = n2;
  }
  
  public double getLength() {
    return length;
  }
  
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