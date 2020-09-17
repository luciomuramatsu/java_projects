package module6;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import java.util.HashMap;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.marker.Marker;

/**
 * Visualizes life expectancy in different countries. 
 * 
 * It loads the country shapes from a GeoJSON file via a data reader, and loads the population density values from
 * another CSV file (provided by the World Bank). The data value is encoded to transparency via a simplistic linear
 * mapping.
 */
public class CovidRate extends PApplet {

	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;
	
	UnfoldingMap map;
	HashMap<String, Float> covidMap;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	int color1 = color(10, 250, 0);
	int color2 = color(75, 150, 0);
	int color3 = color(204, 204, 0);
	int color4 = color(255, 128, 0);
	int color5 = color(205, 0, 0);
	
	public void setup() {
		size(900, 700, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 650, 600, new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load Covid data
		covidMap = ParseFeed.loadCOVIDFromCSV(this,"WHO-COVID-19-global-data.csv");
		//for (String key : covidMap.keySet()) {
			//if (covidMap.get(key) > -101) {
		    //System.out.println("CovidMap Size " + covidMap.size());
			//}		
		//}
		
		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		//System.out.println(countryMarkers.get(0).getProperties().get("name"));
		
		// Country markers are shaded according to life expectancy (only once)
		shadeCountries();
	}

	public void draw() {
		// Draw map tiles and country markers
		background(0);
		map.draw();
		addKey();
	}

	//Helper method to color each country based on life expectancy
	//Red-orange indicates low (near 40)
	//Blue indicates high (near 100)
	private void shadeCountries() {
		int numFoundConuntries = 0;
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			//String countryId = marker.getId();
			//System.out.println("country id " + countryId);
			String countryName = (String) marker.getProperties().get("name");
			//System.out.println("country name " + countryName);
			if (covidMap.containsKey(countryName)) {
				numFoundConuntries ++;
				float covidRate = covidMap.get(countryName);
				// Encode value as brightness (values range: 40-90)
				if (covidRate < -50) {
					//int colorLevel = (int) map(covidRate, -100, 400, 0, 255);
					marker.setColor(color1);
				}
				if (covidRate > -50 && covidRate < 0) {
					marker.setColor(color2);
				}
				if (covidRate >= 0 && covidRate < 100) {
					marker.setColor(color3);
				}
				if (covidRate >= 100 && covidRate < 200) {
					marker.setColor(color4);
				}
				if (covidRate >= 200) {
					marker.setColor(color5);
				}
				
			}
			else {
				marker.setColor(color(155,155,155));
			}
		}
		System.out.println("numFoundConuntries " + numFoundConuntries);

	}
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		fill(255, 250, 240);
		
		int xbase = 15;
		int ybase = 50;
		
		rect(xbase, ybase, 170, 250);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("COVID-19", xbase+50, ybase+25);
		text("Rate Of New Cases", xbase+25, ybase+50);
		text("Month-Over-Month", xbase+25, ybase+65);
		
		fill(0, 0, 0);
		textAlign(LEFT, CENTER);		

		text("< -50%", xbase+50, ybase+90);
		text("-50% ~ 0%", xbase+50, ybase+110);
		text("0% ~ 100%", xbase+50, ybase+130);
		text("100% ~ 200%", xbase+50, ybase+150);
		text("> 200%", xbase+50, ybase+170);
		text("Not available", xbase+50, ybase+200);
		
		textSize(9);
		text("Source: World Healh Organization", xbase+10, ybase+220);
		text("https://covid19.who.int/", xbase+30, ybase+230);
		
		
		fill(color1);
		rect(xbase+35-5, ybase+90-5, 10, 10);
		fill(color2);
		rect(xbase+35-5, ybase+110-5, 10, 10);
		fill(color3);
		rect(xbase+35-5, ybase+130-5, 10, 10);
		fill(color4);
		rect(xbase+35-5, ybase+150-5, 10, 10);
		fill(color5);
		rect(xbase+35-5, ybase+170-5, 10, 10);
		
		fill(color(155,155,155));
		rect(xbase+35-5, ybase+200-5, 10, 10);

		
	}

}
