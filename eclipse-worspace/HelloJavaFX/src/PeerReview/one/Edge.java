/**
 * 
 */
package roadgraph;

import geography.GeographicPoint;
import roadgraph.MapGraph.Edge;

/**
 * @author ktain
 *
 */
public class Edge {
	// Edges consist of start and end Vertices, as well as a street name and type
	//, and a distance
	
	private Vertex start;
	private Vertex end;
	private String streetName;
	private String streetType;
	private double distance;
	
	/**
	 * this constructor will calculate the distance between points
	 * but doesn't give a street name or type
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param s The starting vertex of the Edge (from)
	 * @param e The ending vertex of the Edge (to)
	 *  
	 */

	public Edge (GeographicPoint s, GeographicPoint e) {
	
		start = hashNodes.get(s);
		end = hashNodes.get(e);
		streetName = "";
		streetType = "";
		distance = calcDistance();
	}
	
	/**
	 * this constructor uses existing values for all parameters
	 * @param s The starting vertex of the Edge (from)
	 * @param e The ending vertex of the Edge (to)
	 * @param sn The streetname
	 * @param st The streettype
	 * @param d  The distance between vertices  **not calculated**
	 */
	public Edge (GeographicPoint s, GeographicPoint e, String sn, String st, double d) {
		start = hashNodes.get(s);
		end = hashNodes.get(e);
		// create new copies of the strings to avoid inadvertent changes later
		streetName = new String(sn);
		streetType = new String(st);
		distance = d;
	}

	/**
	 * this constructor clones an existing edge but does not create new vertices
	 * @param e The Edge to clone
	 */
	public Edge(Edge e) {
		start = e.getStart();
		end = e.getEnd();
		streetName = new String(e.streetName);
		streetType = new String(e.streetType);
		distance = e.distance;
	}
	
	// setters and getters - it's a good idea to have get and set method for
	// every private variable in a class 
	
	/**
	 * returns the starting point of an Edge
	 * @return the starting Vertex
	 */
	public Vertex getStart() {
		return start;
	}
	/**
	 * Changes the starting point of an Edge - Not currently used
	 * once the change is made, the distance is updated
	 * @param s the starting Vertex
	 */
 void setStart(Vertex s) {
		if (s == null) throw new NullPointerException("start location is null");
		start = s;
		//update the distance
		calcDistance();
	}
	
	/**
	 * returns the ending point of an Edge
	 * @return the ending Vertex
	 */
	public Vertex getEnd() {
		return end;
	}
	
	/**
	 * Changes the ending point of an Edge - Not currently used
	 * once the change is made, the distance is updated
	 * @param e the ending Vertex
	 */
	public void setEnd(Vertex e) throws NullPointerException {
		if (e == null) throw new NullPointerException("end location is null");
		end = e;
		// update the distance
		calcDistance();
	}
	
	/**
	 * returns the street name of an Edge
	 * @return the street name String
	 */
	public String getStreetName() {
		return streetName;
	}
	
	/**
	 * Changes the street name of an Edge - Not currently used
	 * @param name the street name
	 */
	public void setStreetName(String name) throws NullPointerException {
		if (name == null) throw new NullPointerException("streetname is null");
		streetName = name;
	}
	
	/**
	 * returns the street type of an Edge
	 * @return the street type String
	 */
	public String getStreetType() {
		return streetType;
	}

	/**
	 * Changes the street type of an Edge - Not currently used
	 * @param type the street type
	 */

	public void setStreetType(String type) throws NullPointerException{
		if (type == null) throw new NullPointerException("streetType is null");
		streetType = type;
	}
	
	/**
	 * Updates the calculated distance between two vertices of an Edge 
	 * @return  distance the distance between vertices
	 */

	public double calcDistance() {
		return start.getCoord().distance(end.getCoord());
	}
}