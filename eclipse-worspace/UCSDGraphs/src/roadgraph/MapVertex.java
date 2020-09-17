package roadgraph;

import java.util.*;

import geography.GeographicPoint;

/* @author - LVM
 * Class MapVertices, stores info about all the vertices in a MapGraph.
 * 
 */

public class MapVertex implements Comparable<MapVertex>{
	/*
	 * Instance variables:
	 * @location: A GeographicPoint containing lat and log of a the vertex.
	 * @myEdges: A list that stores all the edges of a vertex using another class.
	 * named MapEdges
	 * @id: Stores an int that relates to the order the vertix was added to the map
	 */
	private GeographicPoint location;
	private List<MapEdges> myEdges;
	private int id;
	private double distanceFromStart;
	private double distanceToGoal;
	
	/* Constructor to initialize all the instances variables
	 * @gp: GeographicPoint location of the vertex
	 * @idNumber: int passed to track the order it was added.
	 */
	 
	public MapVertex(GeographicPoint gp, int idNumber) {
		
		location = gp;
		myEdges = new ArrayList<MapEdges>();
		id = idNumber;
		distanceFromStart = Double.POSITIVE_INFINITY;
		distanceToGoal = Double.POSITIVE_INFINITY;
	}
	

	// getter for location
	public GeographicPoint getLocation() {
		return this.location;
	}
	// getter for myEdges
	public List<MapEdges> getEdges() {
		return this.myEdges;
	}
	// getter for id
	public int getId() {
		return this.id;
	}
	
	// getter for distanceFromStart
	public double getDistanceFromStart() {
		return this.distanceFromStart;
	}
	
	// setter for distanceFromStart
	public void setDistanceFromStart(double distance) {
		this.distanceFromStart = distance;
	}
	
	// getter for distanceToGoal
	public double getDistanceToGoal() {
		return this.distanceToGoal;
	}
	
	// setter for distanceToGoal
	public void setDistanceToGoal(double distance) {
		this.distanceToGoal = distance;
	}
	
	// describe how to compare vertex by the distance from start
	@Override
	public int compareTo(MapVertex other) {
		double otherTotalDistance = other.distanceFromStart + other.distanceToGoal;
		double thisTotalDistance = this.distanceFromStart + this.distanceToGoal;
		if (thisTotalDistance > otherTotalDistance) return 1;
		if (thisTotalDistance < otherTotalDistance) return -1;
		return 0;

	}
}
