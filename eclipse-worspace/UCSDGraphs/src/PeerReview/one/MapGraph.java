/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */


import java.util.*;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	// MapGraphs are defined by nodes, edges, number of edges, and number of nodes
	// the hashNodes hashMap makes searching by location faster
	// right now the mapEdges are not used anywhere, but they could be useful for
	// debugging
	private List<Vertex> mapNodes;
	private HashMap<GeographicPoint, Vertex> hashNodes;
	private List<Edge> mapEdges;
	private int numVertices;
	private int numEdges;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		mapNodes = new ArrayList<Vertex>();
		hashNodes = new HashMap<GeographicPoint, Vertex>() ;
		mapEdges = new ArrayList<Edge>();
		numVertices = 0;
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return hashNodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if (hashNodes.containsKey(location) || location == null) return false;
		
		// create a new Vertex with required information
		Vertex nVertex = new Vertex(location);
		
		// add the vertex to the list of vertices and the hashNodes;
		mapNodes.add(nVertex);
		hashNodes.put(location,nVertex);
		
		//increase the count
		numVertices++;
		
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		if (from == null || to == null || roadName == null || roadType == null || length < 0) throw new IllegalArgumentException("addEdge null parameter");
		if (!hashNodes.containsKey(from) || !hashNodes.containsKey(to)) throw new IllegalArgumentException("addEdge null vertex");
		
		// create a new Edge
		Edge nEdge = new Edge(from,to,roadName,roadType,length);
		
		// add the edge to the list of edges for the map
		mapEdges.add(nEdge);
		numEdges++;
		
		// add this to the list of edges for Vertex 1
		Vertex vFrom = hashNodes.get(from);
		vFrom.addEdge(nEdge);
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// initialize the queue, HashSet and HashMap
		Queue<Vertex> vQ = new LinkedList<Vertex>();
		HashSet<Vertex> visited = new HashSet<Vertex>();
		HashMap<Vertex,Vertex> parentMap = new HashMap<Vertex,Vertex>();
		
		// I made a choice to create edges from vertices rather than GeoPoints, this
		// could be used to expand functionality later, but it's just an extra layer
		// right now
		
		// find the vertices associated with the locations of start and end
		Vertex startV = hashNodes.get(start);
		Vertex goalV = hashNodes.get(goal);
		
		//push start onto the queue
		vQ.add(startV);
		
		// iterate through the queue until it's empty
		while (vQ.size() > 0) {
			
			// get the front location
			Vertex curr = vQ.remove();
			
			// Hook for visualization.  See writeup.
			nodeSearched.accept(curr.getCoord());

			if (curr.equals(goalV)) {
				//need to return a list of locations along the pathway
				 LinkedList<GeographicPoint> pathway = new LinkedList<GeographicPoint>();
				 Vertex pnode = goalV;
				 while (!pnode.equals(startV)) {
					 pathway.addFirst(pnode.getCoord());
					 pnode = parentMap.get(pnode);
				 }
				 // include the starting point
				 pathway.addFirst(pnode.getCoord());
				 return pathway;
			} else {
				// we aren't there yet, keep exploring
				// for each neighbor we need to end vertex
				for (Edge neighborE : curr.getEdgeList()) {
					Vertex eEnd = neighborE.getEnd();
					if(!visited.contains(eEnd)) {
						// if not already visited add node to visited list
						visited.add(eEnd);
						// add curr as the parent of node
						parentMap.put(eEnd,curr);
						// add node to queue
						vQ.add(eEnd);
					}
				}
			}
		}
		// if we empty the queue there is no pathway
		return null;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	public void printMap() {
		// print out some identifying information
		System.out.println("This map has " + numVertices + " vertices.");
		System.out.println("This map has " + numEdges + " edges.");
		System.out.println("The adjacency map for each node is: ");
		
		int nodeCount = 1;
		for (Vertex v : mapNodes) {
			System.out.println("Node " + nodeCount + " at <" + v.coord.toString() + ">: ");
			System.out.print("{ " );
			for(Edge e : v.edgeList) {
				System.out.print(" <" + e.end.getCoord().toString() + ">  ");
			}
			System.out.println(" } \n");
			nodeCount++;
		}
	}
	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		firstMap.printMap();
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println( firstMap.bfs(testStart,testEnd).toString());
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	

}
