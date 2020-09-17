package module3;

//Java utilities libraries
import java.util.*;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;
import de.fhpotsdam.unfolding.providers.Microsoft;
/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(displayWidth, displayHeight, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 1000, 600, new Microsoft.HybridProvider());
		// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		//earthquakesURL = "2.5_week.atom";
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    //System.out.println(earthquakes);
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    for (PointFeature feature : earthquakes) {
	    	SimplePointMarker spm = createMarker(feature);
	    	markers.add(spm);
	    }
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setup method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		System.out.println("Properties " + feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");		
		float mag = Float.parseFloat(magObj.toString());		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int green = color(0, 255, 0);
	    int yellow = color(255, 255, 0);
	    int red = color(255, 0, 0);
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    
	    if (mag <= THRESHOLD_LIGHT) {
	    	marker.setColor(green);
	    	marker.setRadius(10);
	    }
	    if (mag > THRESHOLD_LIGHT && mag <= THRESHOLD_MODERATE) {
	    	marker.setColor(yellow);
	    	marker.setRadius(13);
	    }
	    if (mag > THRESHOLD_MODERATE) {
	    	marker.setColor(red);
	    	marker.setRadius(16);
	    }
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill(200, 200, 200);
		rect(20, 70, 160, 300, 7);
		fill(0, 255, 0);
		ellipse(50, 170, 10, 10);
		fill(255, 255, 0);
		ellipse(50, 245, 13, 13);
		fill(255, 0, 0);
		ellipse(50, 320, 16, 16);
		fill(0, 0, 0);
		text("Earthquake Key", 50, 120);
		text("Below 4.0", 70, 175);
		text("4.0+ Magnitude", 70, 250);
		text("5.0+ Magnitude", 70, 325);
	}
}
