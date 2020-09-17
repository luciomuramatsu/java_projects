/**
 * 
 */

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;
import roadgraph.MapGraph.Edge;

/**
 * @author ktain
 *
 */

public class Vertex {
	// a Vertex is defined by its location and the list of edges
	// since an edge has two vertices, a precondition is that this Vertex
	// is the "start" of each of the edges in the list.
	
	private GeographicPoint coord;
	private List<Edge> edgeList;
	
	public Vertex(double xcoord, double ycoord) {
		coord = new GeographicPoint(xcoord, ycoord);
		edgeList = new ArrayList<Edge>();
	}
	
	public Vertex(GeographicPoint location) throws NullPointerException{
		
		if (location == null) throw new NullPointerException("location is null");
		
		coord = new GeographicPoint(location.x, location.y);
		edgeList = new ArrayList<Edge>();
	}
	
	
	// getters and setters - these may not be used, but it's good practice
	// to have get and set methods for all private variables.  they are here if 
	// I need them.
	
	/**
	 * Get the coordinate of the vertex (road intersections) in the graph
	 * @return GeographicPoint location of the vertex in the graph.
	 */

	public GeographicPoint getCoord()  {
		return coord;
	}

	/**
	 * Get the list of edges for this vertex
	 * @return The List edgeList of edges for this vertex.
	 */

	public List<Edge> getEdgeList() {
		return edgeList;
	}
	
	/**
	 * set the location of the vertex in the graph.  At the moment this isn't useful
	 * but it's possible that another application might have moving vertices.
	 * @param location The updated location for the vertex.
	 * @return void.
	 */

	public void setCoord(GeographicPoint location) throws NullPointerException {
		if (location == null) throw new NullPointerException("setCoord is null");
		coord = new GeographicPoint(location.x, location.y);
	}

	/**
	 *  @param w The new edge to add to edgeList of the vertex.
	 * @return void.
	 */

	public void addEdge(Edge w) throws NullPointerException {
		if (w == null) throw new NullPointerException("addEdge is null");
		edgeList.add(w);
	}
			
}
