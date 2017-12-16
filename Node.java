/**
 * Node objects hold the location of a given point on the map. They are the vertices of the graph.
 */
public class Node {
  
  protected String name;
  protected double latitude;
  protected double longitude;
  protected boolean isBuilding;
  protected double weight;
  /**
   * Creates a Node object that holds latitude, longitude, and whether or not the Node represents a 
   * building (true) or an intersection (false).
   * 
   * @param latitude - the latitude of the Node
   * @param longitude - the longitude of the Node
   * @param isBuilding - true if it is a building, false otherwise
   * @param name - name of buildings or name of intersections is "i" + number
   */
  public Node( String name, double latitude, double longitude, boolean isBuilding, int weight ) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.isBuilding = isBuilding;
    this.name = name;
    this.weight = weight;
  }
  
  public double getLat() {
    return latitude;
  }
  
  public double getLon() {
    return longitude;
  }
  
  public boolean getisBuilding() {
    return isBuilding;
  }
  
  public String getName() {
    return name;
  }
  
  public String toString() {
    return name;
  }
}