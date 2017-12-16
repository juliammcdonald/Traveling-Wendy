import java.util.*;
import java.io.*;
import javafoundations.*;
/**
 * Creates a Graph of Nodes and Edges. This is a weighted adjacency list graph.
 */
public class WendyGraph {
  public ArrayList<Node> vertices;
  public ArrayList<LinkedList<Edge>> edges;
  public Double[] latitudes; public Double[] longitudes;
  public Double maxLong, minLong, maxLat, minLat;
  
  public WendyGraph( String fileName ) {
    vertices = new ArrayList<Node>();
    edges = new ArrayList<LinkedList<Edge>>();
    try {
      Scanner in = new Scanner( new File( fileName ));
      
      //new Node variables
      String name;
      double lat;
      double lon;
      boolean isBuilding;
      
      String next = in.next();
      
      while( !next.equals("r") ) {
        name = in.next();
        isBuilding = (name.charAt( 0 ) != 'i');
        lat = Double.parseDouble( in.next() );
        lon = Double.parseDouble( in.next() );

        addNode( new Node( name, lat, lon, isBuilding, Integer.MAX_VALUE ));
        //add adjacency list for each new node
        edges.add( new LinkedList<Edge>() );
        
        next = in.next();
      }
      
      //new Edge variables
      Node n1;
      Node n2;

      while( in.hasNext() ) {
        n1 = vertices.get( findNodeIndex( in.next() ));
        n2 = vertices.get( findNodeIndex( in.next() ));
        
        addEdge( new Edge( n1, n2 ) );
        //read and add edges (3 columns -> r, lat, long)
        if( in.hasNext() )
          in.next();
      }
      
      /* Initialize array of longitudes */
      longitudes = new Double[vertices.size()];
      /* Initialize array of latitudes */
      latitudes = new Double[vertices.size()];            
      int i = 0;
      for (Node n : vertices){
        longitudes[i] = n.getLon();
        latitudes[i] = n.getLat();
        i++;
      }
      /* Instantiate minimum and maximum latitude and longitude */
      Sorting.quickSort(longitudes, 0, longitudes.length - 1);
      Sorting.quickSort(latitudes, 0, latitudes.length - 1);
      maxLat = Math.abs(latitudes[0]); 
      minLat = Math.abs(latitudes[latitudes.length - 1]); 
      maxLong = Math.abs(longitudes[0]);
      minLong = Math.abs(longitudes[longitudes.length - 1]);
            
    } catch( IOException e ) {
      System.out.println( "File IO Exception" );
    }
  }
  
  public void addNode( Node n ) {
    vertices.add( n );
  }
  
  public void addEdge( Edge e ) {
    edges.get( findNodeIndex( e.getNode1() ) ).add( e );
    edges.get( findNodeIndex( e.getNode2() ) ).add( e );
  }
  
  private int findNodeIndex( Node n ) {
    for( int i = 0; i < vertices.size(); i++ ) {
      if( n.equals( vertices.get( i ) ))
           return i;
    }
    return -1;
  }
  
  private int findNodeIndex( String name ) {
    for( int i = 0; i < vertices.size(); i++ ) {
      if( name.equals( vertices.get( i ).getName() ) )
        return i;
    }
    System.out.println( name );
    return -1;
  }
  
  public String toString() {
    String s = "";
    for( int i = 0; i < vertices.size(); i++ ) {
      s += vertices.get( i ) + ": ";
      s += edges.get( i ) + "\n";
    }
    return s;
  }
  
  public void dijkstra( String startName ) {
    PriorityQueue q = new PriorityQueue( vertices );
  }
  
/* -----------((deprecated, moved to constructor)) 
  public Double[] getAllLongitudes(){
    Double[] longs = new Double[vertices.size()];
    int i = 0;
    for (Node n : vertices){
      longs[i] = n.getLon();
      i++;
    }
    return longs;
  }    
  public Double[] getAllLatitudes(){
    Double[] lats = new Double[vertices.size()];
    int i = 0;
    for (Node n : vertices){
      lats[i] = n.getLat();
      i++;
    }
    return lats;
  }
  
  public Double getLongitude(String type){    
    Sorting.quickSort(longitude, 0, longs.length - 1);
    if (type.equals("max")){     
      return Math.abs(longs[0]); //max
    } else {//does not consider invalid input since not open to user
      return Math.abs(longs[longs.length - 1]); //min
    }
  }
  
  public Double getLatitude(String type){
    Double[] lats = getAllLatitudes();
    Sorting.quickSort(lats, 0, lats.length - 1);
    if (type.equals("max")){     
      return Math.abs(lats[0]); //max
    } else {//does not consider invalid input since not open to user
      return Math.abs(lats[lats.length - 1]); //min
    }
  }-------------------*/
 
  
  /* @return */
  public int[] getPixelCoordinates(double lat, double lon, int mapWidth, int mapHeight){

    System.out.printf("maxLong:%f minLong:%f maxLat:%f minLat:%f\n", maxLong, minLong, maxLat, minLat);
    
    /* Brute force handling of floating point precision */
    Double spanLong = (maxLong - minLong) * 10E5;
    Double spanLat = (maxLat - minLat) * 10E5;
        
    int[] pixelCoords = new int[2];
    pixelCoords[0] = (int)Math.round(((lon*10E5 - minLong*10E5) / spanLong) * mapWidth);
    pixelCoords[1] = (int)Math.round(((lat*10E5 - minLat*10E5) / spanLat) * mapHeight);
    
    return pixelCoords;
  }
  
  public static void main( String[] args ) {
    WendyGraph w = new WendyGraph( "wellesleycoord.txt" );

    System.out.println(w.getPixelCoordinates(42.29449, 71.30, 750, 520)[0] + " " + 
                       w.getPixelCoordinates(42.29449, 71.30, 750, 520)[1]);
    System.out.println(w.getPixelCoordinates(42.29450, 71.30, 750, 520)[0] + " " + 
                       w.getPixelCoordinates(42.29450, 71.30, 750, 520)[1]);
    System.out.println(w.getPixelCoordinates(42.29455, 71.30, 750, 520)[0] + " " + 
                       w.getPixelCoordinates(42.29455, 71.30, 750, 520)[1]);
    System.out.println(w.getPixelCoordinates(42.29456, 71.30, 750, 520)[0] + " " + 
                       w.getPixelCoordinates(42.29456, 71.30, 750, 520)[1]);
  }
}