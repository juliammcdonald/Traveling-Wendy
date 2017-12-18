/*****************************************************************
 * Node.java
 * Node objects hold the location of a given point on the map. They are the vertices of the graph.
 ****************************************************************/
public class Node implements Comparable<Node>{
  
  protected String name;
  protected double latitude;
  protected double longitude;
  protected boolean isBuilding;
  protected double weight;
  protected Node prev;
  
  /**
   * Creates a Node object that holds latitude, longitude, and whether or not the Node represents a 
   * building (true) or an intersection (false).
   * 
   * @param latitude - the latitude of the Node
   * @param longitude - the longitude of the Node
   * @param isBuilding - true if it is a building, false otherwise
   * @param name - name of buildings or name of intersections is "i" + number
   */
  public Node( String name, double latitude, double longitude, boolean isBuilding, double weight ) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.isBuilding = isBuilding;
    this.name = name;
    this.weight = weight;
    this.prev = null;
  }
  
  
  /*
   * Gets latitude
   * @return latitude
   */
  public double getLat() {
    return latitude;
  }
  
  /*
   * Gets longitude
   * @return longitude
   */
  public double getLon() {
    return longitude;
  }
  
  /*
   * Gets isBuilding
   * @return true if building, false otherwise (does not determine this)
   */
  public boolean getisBuilding() {
    return isBuilding;
  }
  
  /*
   * Gets name
   * @return name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Returns a string representation of a Node
   * @return string containing the name of the node
   */
  public String toString() {
    return name;
  }
  
  /*
   * Sets the weight of a node
   * @param w - the new weight of the node
   */
  public void setWeight( double w ) {
    weight = w;
  }
  
  /**
   * Gets weight
   * @param weight
   */
  public double getWeight() {
    return weight;
  }
  
  public Node getPrev() {
    return prev;
  }
  
  public void setPrev( Node n ) {
    prev = n;
  }
  
  /**
   * Negative if less than, 0 if equal, positive if greater than
   */
  public int compareTo( Node n ) {
    if (this.weight == n.weight)
      return name.compareTo(n.name);
    return Double.compare( this.weight, n.weight );
  }
}