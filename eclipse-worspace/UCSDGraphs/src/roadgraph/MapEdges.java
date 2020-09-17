package roadgraph;

import geography.GeographicPoint;

/* @author - LVM
 * 
 * MapEdges class, used to store all characteristics of a graph edge.
 *
*/
public class MapEdges{
	
	/* Instances variables
	 * @from: stores the vertex info of where the edge started.
	 * @to: stores the vertex info of where the edge ended.
	 * @name: Name of the edge.
	 * @roadType: stores if the edge is a street, avenue and etc..
	 * @distance: the length from the start to the end of an edge.
	 */
	private GeographicPoint from;
	private GeographicPoint to;
	private String name;
	private String roadType;
	private double distance;
	
	/* 
	 * 	Constructor to initialize all the instances variables.
	 * @start: GeographicPoint where the edge started.
	 * @end: GeographicPoint where the edge ended.
	 * @word: name of the edge.
	 * @length: distance length of the edge.
	 * @type: the type of the edge.
	 */
	public MapEdges(GeographicPoint start, GeographicPoint end, String word, String type, double length) {
		
		from = start;
		to = end;
		name = word;
		distance = length;
		roadType = type;
		
	}
	
	
	// Getter for from
	public GeographicPoint getFrom() {
		return this.from;
	}
	// Getter for to
	public GeographicPoint getTo() {
		return this.to;
	}
	// Getter for name
	public String getName() {
		return this.name;
	}
	// Getter for distance
	public double getDistance() {
		return this.distance;
	}
	// Getter for roadType
	public String getRoadType() {
		return this.roadType;
	}

}

