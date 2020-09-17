/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

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
	//TODO: Add your member variables here in WEEK 3
	private HashMap<GeographicPoint, MapVertex> vertices;
	private int idVertice;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		vertices = new HashMap<GeographicPoint, MapVertex>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return vertices.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		int numEdges = 0;
		for (GeographicPoint gp : vertices.keySet()) {
			numEdges += vertices.get(gp).getEdges().size();
		}
		return numEdges;
	}

	/**
	 * Get all the road segments in the graph
	 * @return HashMap <location, all the edges for that location>.
	 */
	public HashMap<GeographicPoint, ArrayList<GeographicPoint>> getAllEdges()
	{
		HashMap<GeographicPoint, ArrayList<GeographicPoint>> allEdges = 
				new HashMap<GeographicPoint, ArrayList<GeographicPoint>>();
		
		
		for (GeographicPoint gp : vertices.keySet()) {
			List<MapEdges> currEdges = vertices.get(gp).getEdges();
			ArrayList<GeographicPoint> currEdgeLocation = new ArrayList<GeographicPoint>();

			for(MapEdges me : currEdges) {
				currEdgeLocation.add(me.getTo());  
			}
			allEdges.put(gp, currEdgeLocation);
		}
		return allEdges;
	}
	
	/* 
	 * printGraph method to print the graph as an adjacency list
	 */
	public void printGraph() 
	{
		// the all the edges
		HashMap<GeographicPoint, ArrayList<GeographicPoint>> allEdges = getAllEdges();
		// build and print a string with number of vertices and edges
		String s = "MapGraph with ";
		s += getNumVertices();
		s += " vertices and ";
		s += getNumEdges();
		s += " edges.";
		System.out.println(s);
		
		// find, build and print the vertex id and all its edges destinations (the edge to)
		for (GeographicPoint gp : allEdges.keySet()) {
			String verAndEd = "Vertex ";
			verAndEd += vertices.get(gp).getId();
			verAndEd += ": (";
			for(GeographicPoint edgeGE : allEdges.get(gp)) {
				verAndEd += vertices.get(edgeGE).getId();
				verAndEd += " (";
				verAndEd += vertices.get(edgeGE).getLocation();
				verAndEd += ")";
				verAndEd += " => ";
			}
			verAndEd += ")";
			System.out.println(verAndEd);
		}
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
		// TODO: Implement this method in WEEK 3
		
		// returns false if the location variable is null or already exists in the map
		if (location == null) return false;
		if (vertices.containsKey(location)) return false;
		// add to the id tracker
		idVertice ++;
		// create a new MapVertex and add to the HashMap with the location as its key.
		MapVertex thisVertex = new MapVertex(location, idVertice);
		vertices.put(location, thisVertex);
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

		//TODO: Implement this method in WEEK 3
		
		// if from, to, roadName or roadType, or length is < 0 throw an exception
		if (from == null || to == null || roadName == null 
				|| roadType == null || length < 0) 
			throw new IllegalArgumentException();
		
		// the the location from or to doesn't exist as a vertex. Throw an exception
		if (!vertices.containsKey(from) || !vertices.containsKey(to))
			throw new IllegalArgumentException();
		
		// create a new MapEdges and add to the list of vertices
		vertices.get(from).getEdges().add(new MapEdges(from, to, roadName, roadType, length));
		
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
	public List<GeographicPoint> bfs(
			GeographicPoint start,
			GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		
		/* Initialize what is necessary:
		 * @parentMap: stores every visited vertex and its parent. It'll be handy to reconstruct the path
		 * @found: a boolean to know when to stop searching
		 */
		
		HashMap<MapVertex, MapVertex> parentMap = 
				new HashMap<MapVertex, MapVertex>();
		boolean found = false;
		
		// if start or goal is null print that it's impossible to find a path
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}

		// call a helper method to check if the goal has been found
		found = checkIfFoundBFS(start, goal, parentMap, nodeSearched);
		// call a helper method to reconstruct the path and return it
		return reconstructPath(start, goal, parentMap, found);
	}
		

	// private helper method to do the BFS and return true if path is found
	private boolean checkIfFoundBFS(
			GeographicPoint start, 
			GeographicPoint goal, 
			HashMap<MapVertex, MapVertex> parentMap, 
			Consumer<GeographicPoint> nodeSearched) 
	{
	
		/*
		 * @visited: HashSet to store the vertices that has been visited.
		 * it's important to be an HashSet so it's fast to search if a vertex is already there
		 * @queue: a line of vertices that are going to be explored
		*/
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		Queue<GeographicPoint> queue = new LinkedList<GeographicPoint>();
		
		// add start to queue and visited lists
		queue.add(start);
		visited.add(start);
		
		// Until queue is not empty it will check if vertices are equal to goal
		while(!queue.isEmpty()) {
			
			// remove first element of the queue
			GeographicPoint curr = queue.remove();
			// add vertex to the nodeSearched to use in the frontend app
			nodeSearched.accept(curr);
			MapVertex currVertex = vertices.get(curr);
			if(curr.equals(goal))  {
				return true; 
			}
			// for all the edges related to the current vertex, add its destination
			// to the queue to be searched if needed
			for (MapEdges me : currVertex.getEdges()) {
				GeographicPoint next = me.getTo();
				
				if (!visited.contains(next)) {
					queue.add(next);
					visited.add(next);
					parentMap.put(vertices.get(next), vertices.get(curr));
				}
			}	
		}
		return false;
	}
		
	// private helper method to reconstruct the path from start to goal
	private LinkedList<GeographicPoint> reconstructPath(
			GeographicPoint start, 
			GeographicPoint goal, 
			HashMap<MapVertex, MapVertex> parentMap, 
			boolean found) 
	{
		//@path: initialize LinkedList to store the vertices for the solution (path from start to goal)
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		
		// if there is no path
		if (!found) {
			System.out.println("No path exists");
			return path;
		}
		
		// reconstruct the path
		GeographicPoint curr = goal;
		while (!curr.equals(start)) {
			path.addFirst(curr);
			curr = parentMap.get(vertices.get(curr)).getLocation();
		}
		path.addFirst(start);
		
		return path;
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
		
		/* Initialize what is necessary:
		 * @parentMap: stores every visited vertex and its parent. It'll be handy to reconstruct the path
		 * @found: a boolean to know when to stop searching
		 */
		
		HashMap<MapVertex, MapVertex> parentMap = 
				new HashMap<MapVertex, MapVertex>();
		boolean found = false;
		
		// if start or goal is null print that it's impossible to find a path
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		if (!vertices.containsKey(start) || !vertices.containsKey(goal)){
			System.out.println("Start or goal node doesn't exist in the graph!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}

		// call a helper method to check if the goal has been found
		found = checkIfFoundDijkstra(start, goal, parentMap, nodeSearched);
		// call a helper method to reconstruct the path and return it
		return reconstructPath(start, goal, parentMap, found);
		
	}
	
	// private helper method to do the BFS and return true if path is found
	private boolean checkIfFoundDijkstra(
			GeographicPoint start, 
			GeographicPoint goal, 
			HashMap<MapVertex, MapVertex> parentMap, 
			Consumer<GeographicPoint> nodeSearched) 
	{
	
		/*
		 * @visited: HashSet to store the vertices that has been visited.
		 * it's important to be an HashSet so it's fast to search if a vertex is already there
		 * @queue: a line of vertices that are going to be explored
		*/
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		PriorityQueue<MapVertex> pQueue = new PriorityQueue<MapVertex>();
		
		// add start to queue and visited lists
		MapVertex startVertex = vertices.get(start);
		MapVertex goalVertex = vertices.get(goal);
		
		startVertex.setDistanceFromStart(0.0);
		startVertex.setDistanceToGoal(0.0);
		pQueue.add(startVertex);
		parentMap.put(startVertex, startVertex);
		int countPasses = 0;
		// Until queue is not empty it will check if vertices are equal to goal
		while(!pQueue.isEmpty()) {
			
			// remove first element of the queue
			MapVertex curr = pQueue.remove();
			countPasses ++;
			if (!visited.contains(curr.getLocation())) {
				
				System.out.println(curr.getLocation());
				visited.add(curr.getLocation());
				// add vertex to the nodeSearched to use in the frontend app
				nodeSearched.accept(curr.getLocation());
				if(curr.equals(goalVertex))  {
					System.out.println("Dijkstra " + countPasses);
					return true; 
				}
				// for all the edges related to the current vertex, add its destination
				// to the queue to be searched if needed
				for (MapEdges me : curr.getEdges()) {
					MapVertex next = vertices.get(me.getTo());

					if (!visited.contains(next.getLocation())) {
						double currDistance = curr.getDistanceFromStart() + me.getDistance();
						double oldDistance = next.getDistanceFromStart();
						next.setDistanceFromStart(currDistance);
						next.setDistanceToGoal(0.0);
						pQueue.add(next);
						if (parentMap.containsKey(next)) {
							if (oldDistance > currDistance) {
								parentMap.put(next, curr);
							}
						} else {
							parentMap.put(next, curr);
						}
					}
				}
			}	
		}
		System.out.println("Dijkstra " + countPasses);

		return false;
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
		
		HashMap<MapVertex, MapVertex> parentMap = 
				new HashMap<MapVertex, MapVertex>();
		boolean found = false;
		
		// if start or goal is null print that it's impossible to find a path
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		if (!vertices.containsKey(start) || !vertices.containsKey(goal)){
			System.out.println("Start or goal node doesn't exist in the graph!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}

		// call a helper method to check if the goal has been found
		found = checkIfFoundAStart(start, goal, parentMap, nodeSearched);
		// call a helper method to reconstruct the path and return it
		return reconstructPath(start, goal, parentMap, found);
		
	}

	private boolean checkIfFoundAStart(
			GeographicPoint start, 
			GeographicPoint goal, 
			HashMap<MapVertex, MapVertex> parentMap, 
			Consumer<GeographicPoint> nodeSearched) 
	{
	
		/*
		 * @visited: HashSet to store the vertices that has been visited.
		 * it's important to be an HashSet so it's fast to search if a vertex is already there
		 * @queue: a line of vertices that are going to be explored
		*/
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		PriorityQueue<MapVertex> pQueue = new PriorityQueue<MapVertex>();
		
		// add start to queue and visited lists
		MapVertex startVertex = vertices.get(start);
		MapVertex goalVertex = vertices.get(goal);
		
		startVertex.setDistanceFromStart(0.0);
		startVertex.setDistanceToGoal(0.0);
		pQueue.add(startVertex);
		int countPasses = 0;
		//parentMap.put(startVertex, startVertex);
		// Until queue is not empty it will check if vertices are equal to goal
		while(!pQueue.isEmpty()) {
			
			// remove first element of the queue
			MapVertex curr = pQueue.remove();
			if (!visited.contains(curr.getLocation())) {
				countPasses ++;
				System.out.println(curr.getLocation());
				visited.add(curr.getLocation());
				// add vertex to the nodeSearched to use in the frontend app
				nodeSearched.accept(curr.getLocation());
				if(curr.equals(goalVertex))  {
					System.out.println("AStart " + countPasses);
					return true; 
				}
				// for all the edges related to the current vertex, add its destination
				// to the queue to be searched if needed
				for (MapEdges me : curr.getEdges()) {
					MapVertex next = vertices.get(me.getTo());

					if (!visited.contains(next.getLocation())) {
						double currDistanceFromStart = curr.getDistanceFromStart() + me.getDistance();
						double oldDistanceFromStart = next.getDistanceFromStart();
						next.setDistanceFromStart(currDistanceFromStart);
						double currDistanceToGoal = next.getLocation().distance(goal);
						next.setDistanceToGoal(currDistanceToGoal);
						pQueue.add(next);
						if (parentMap.containsKey(next)) {
							if (oldDistanceFromStart > currDistanceFromStart) {
								parentMap.put(next, curr);
							}
						} else {
							parentMap.put(next, curr);
						}
					}
				}
			}	
		}
		System.out.println("AStart " + countPasses);

		return false;
	}	
	
	public static void main(String[] args)
	{
		/*
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		List<GeographicPoint> testroute = firstMap.bfs(testStart, testEnd);
		System.out.println("Route " + testroute);
		firstMap.printGraph();
        */
		
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		System.out.println("Dijkstr: " + testroute);
		System.out.println("aStarSearch: " + testroute2);
		 */
		
		/*
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		GeographicPoint testStart = new GeographicPoint(32.869423, -117.220917);
		GeographicPoint testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		List<GeographicPoint> testroute = testMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		
		
		
	}
	
}
