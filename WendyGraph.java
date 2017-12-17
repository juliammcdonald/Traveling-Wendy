import java.util.*;
import java.io.*;
import javafoundations.*;

/**
 * Creates a Graph of Nodes and Edges. This is a weighted adjacency list graph.
 */
public class WendyGraph {
  /* Later: change to private? */
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
 
  
  /* @return int[x, y]*/
  public int[] getPixelCoordinates(double lat, double lon, int mapWidth, int mapHeight){

    //System.out.printf("maxLong:%f minLong:%f maxLat:%f minLat:%f\n", maxLong, minLong, maxLat, minLat);
    
    /* Brute force handling of floating point precision */
    Double spanLong = (maxLong - minLong) * 10E5;
    Double spanLat = (maxLat - minLat) * 10E5;
        
    int[] pixelCoords = new int[2];
    pixelCoords[0] = (int)Math.round(((1 - (lon*10E5 - minLong*10E5) / spanLong)) * mapWidth);
    pixelCoords[1] = (int)Math.round(((lat*10E5 - minLat*10E5) / spanLat) * mapHeight);
    
    return pixelCoords;
  }
  


  public void dijkstra( String sourceName ) {
    if( findNodeIndex( sourceName ) == -1 ) {
      System.err.println( "Enter a valid startName" );
      return;
    }
    
    Node source = vertices.get( findNodeIndex( sourceName ) );
    
    System.out.println( source );
    NavigableSet<Node> q = new TreeSet<Node>();
    
    for( Node v : vertices ) {
      
      if( v.getName().equals( sourceName ) ) {
        v.setWeight( 0 );
        v.setPrev( v );
      }
      else  {
        v.setWeight( Double.MAX_VALUE );
        v.setPrev( null );
      }
      q.add( v );
    }
    
    //System.out.println( q );
    
    dijkstra(q);
  }
  
  private void dijkstra( final NavigableSet<Node> q ) {
    Node u, v;
    //System.out.println( q );
    while( !q.isEmpty() ) {
      
      //System.out.println("START: " + q );
      u = q.pollFirst(); //node with the shortest distance
      //System.out.println( u );
      if( u.getWeight() == Double.MAX_VALUE) break; //we can ignore u because it is unreachable
      
      //distances to each neighbor
      for( Edge e: edges.get( findNodeIndex( u ) ) ) {
        v = e.getOtherNode( u );
        
        double altWeight = u.getWeight() + e.getLength();
        
        if( altWeight < v.getWeight() ) {
          //System.out.println( "changing weight" );
          q.remove( v );
          v.setWeight( altWeight );
          v.setPrev( u );
          q.add( v );
        }
      }
      //System.out.println( "END: " + q );
    }
  }
  
  public void printPath( String endName ) {
    if( findNodeIndex( endName ) == -1 ) {
      System.err.println( "Enter a valid endName" );
      return;
    }
    
    vertices.get( findNodeIndex( endName ) ).printPath();
    System.out.println();
  }
  
  public void printAllPaths() {
    for( Node n: vertices ) {
      n.printPath();
      System.out.println();
    }
  }
  
  public static void main( String[] args ) {
    WendyGraph w = new WendyGraph( "wellesleycoord.txt" );
    //System.out.println( w );
    
    w.dijkstra( "EASTENTRY" );

    w.printPath( "BATES" );
    w.printPath( "SCICTR" );
    w.printPath( "VILLE" );
}