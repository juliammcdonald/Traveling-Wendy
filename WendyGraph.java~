/*****************************************************************
  * WendyGraph.java
  * Creates a Graph of Nodes and Edges. This is a weighted adjacency list graph.
  * 
  * @author Julia McDonald, Xinhui Xu (all methods written by Julia unless noted)
  * @date Dec. 18, 2017
  ****************************************************************/

import java.util.*;
import java.io.*;
import javafoundations.*;

public class WendyGraph {
  //instance variables
  public ArrayList<Node> vertices;
  public ArrayList<LinkedList<Edge>> edges;
  public Double[] latitudes; public Double[] longitudes;
  public Double maxLong, minLong, maxLat, minLat;
  
  /* 
   * Constructor
   * @param fileName - name of textfile containing latitude and longitude data.
   */
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
  
  /* Adds a node to the vertices.
   * @param n - the new node. */
  public void addNode( Node n ) {
    vertices.add( n );
  }
  
  /* Adds an edge to the edges/
   * @param e - the new edge */
  public void addEdge( Edge e ) {
    edges.get( findNodeIndex( e.getNode1() ) ).add( e );
    edges.get( findNodeIndex( e.getNode2() ) ).add( e );
  }
  
  /* Find index of a node in vertices list.
   * @param n - the node to be found 
   * @return - the index of the node.
   */
  private int findNodeIndex( Node n ) {
    for( int i = 0; i < vertices.size(); i++ ) {
      if( n.equals( vertices.get( i ) ))
        return i;
    }
    return -1;
  }
  
  /* Find index of a node from the name of the node in vertices list.
   * @param name - name of the node.
   * @return - the index of the node.
   */
  private int findNodeIndex( String name ) {
    for( int i = 0; i < vertices.size(); i++ ) {
      if( name.equals( vertices.get( i ).getName() ) )
        return i;
    }
    System.out.println( name );
    return -1;
  }
  
  /* Prints a string representation of WendyGraph object.
   * @return - the string. */
  public String toString() {
    String s = "";
    for( int i = 0; i < vertices.size(); i++ ) {
      s += vertices.get( i ) + ": ";
      s += edges.get( i ) + "\n";
    }
    return s;
  }
  
  
  /* @author Xinhui Xu
   * 
   * Converts latitude and longitude of a node into its appropriate location on graph of any size.
   * @param lat - the latitude of the node.
   * @param lon - the longitude of the node.
   * @param mapWidth - the width of the graph viewport.
   * @param mapHeight - the height of the graph viewport.
   * @return - a integer array of x and y coordinates.*/
  public int[] getPixelCoordinates(double lat, double lon, int mapWidth, int mapHeight){
    /* Brute force handling of floating point precision */
    Double spanLong = (maxLong - minLong) * 10E5;
    Double spanLat = (maxLat - minLat) * 10E5;
    
    int[] pixelCoords = new int[2];
    pixelCoords[0] = (int)Math.round(((1 - (lon*10E5 - minLong*10E5) / spanLong)) * mapWidth);
    pixelCoords[1] = (int)Math.round(((lat*10E5 - minLat*10E5) / spanLat) * mapHeight);
    
    return pixelCoords;
  }
  
  /** 
   * The following methods are with help from https://rosettacode.org/wiki/Dijkstra%27s_algorithm#Java
   * They have been modified according to our own Node, Edge, and WendyGraph classes, and multiple attempts to
   * implement Dijkstra's Algorithm with a Priority Queue were made prior to aid from this implementation.
   */
  
  /**
   * Sets up the "Queue" (which is actually a tree set) needed for Dijjkstra's algorithm
   * and makes the needed modifications.
   * 
   * @param sourceName - the name of the start Node
   */
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
    
    dijkstra(q);
  }
  
  /**
   * Empties the "Queue" and updates the weights and previous nodes of all vertices
   * (Performs the bulk of Dijkstra's algorithm)
   * 
   * @param q - a NavigableSet containing all Nodes in vertices
   */
  private void dijkstra( final NavigableSet<Node> q ) {
    Node u, v;
    while( !q.isEmpty() ) {
      
      u = q.pollFirst(); //node with the shortest distance
      
      if( u.getWeight() == Double.MAX_VALUE) break; //we can ignore u because it is unreachable
      
      //distances to each neighbor
      for( Edge e: edges.get( findNodeIndex( u ) ) ) {
        v = e.getOtherNode( u );
        
        double altWeight = u.getWeight() + e.getLength();
        
        //change weight if there is a lower one found
        if( altWeight < v.getWeight() ) {
          q.remove( v );
          v.setWeight( altWeight );
          v.setPrev( u );
          q.add( v );
        }
      }
    }
  }
  
  /*
   * Finds all nodes along the shortest path from the source node (for which Dijkstra's alg has
   * already been run) and the end node.
   * 
   * @param endName - the name of the destination node
   * @return an ArrayList of all nodes along the path from the source node to the end node
   */
  public ArrayList<Node> getPath( String endName ) {
    if( findNodeIndex( endName ) == -1 ) {
      System.err.println( "Enter a valid endName" );
      return null;
    }
    
    ArrayList<Node> path = new ArrayList<Node>();
    
    path.add( vertices.get( findNodeIndex( endName ) ) );
    
    while( path.get( path.size() - 1 ).getPrev() != null && 
          path.get( path.size() - 1 ).getPrev() != path.get( path.size() - 1 )) {
      path.add( path.get(path.size() - 1).getPrev() );
    }
    
    Collections.reverse( path );
    return path;
  }
  
  /**
   * Finds the shortest path of nodes from the source node to all other nodes
   * 
   * @return an ArrayList of all paths (each of which is also an ArrayList)
   */
  public ArrayList<ArrayList<Node>> getAllPaths() {
    ArrayList<ArrayList<Node>> allPaths = new ArrayList<ArrayList<Node>>();
    
    for( Node n: vertices ) {
      allPaths.add( getPath( n.getName() ) );
    }
    
    return allPaths;
  }
  
  /**
   * Runs dijkstra's alg and returns the path from the start node to the end node
   * 
   * @param startName - the name of the start node
   * @param endName - the name of the end node
   * 
   * @return the shortest path of nodes from start node to end node
   */
  public ArrayList<Node> runDijkstra( String startName, String endName ) {
    dijkstra( startName );
    return getPath( endName );
  }
  
  /* Testing driver.*/
  public static void main( String[] args ) {
    WendyGraph w = new WendyGraph( "wellesleycoord.txt" );
    System.out.println( w );
    
    w.dijkstra( "EASTENTRY" );
    
    System.out.println( w.getPath( "BATES" ) );
    System.out.println( w.getPath( "SCICTR" ) );
    System.out.println(  w.getPath( "VILLE" ) );
  }
}