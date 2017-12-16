import java.util.*;
import java.io.*;

/**
 * Creates a Graph of Nodes and Edges. This is a weighted adjacency list graph.
 */
public class WendyGraph {
  public ArrayList<Node> vertices;
  public ArrayList<LinkedList<Edge>> edges;
  
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
  
  public static void main( String[] args ) {
    WendyGraph w = new WendyGraph( "wellesleycoord.txt" );
    System.out.println( w );
  }
}