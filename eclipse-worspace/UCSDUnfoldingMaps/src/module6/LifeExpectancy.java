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
public class LifeExpectancy extends PApplet {

	UnfoldingMap map;
	HashMap<String, Float> lifeExpMap;
	HashMap<String, Float> covidMap;
	List<Feature> countries;
	List<Marker> countryMarkers;

	public void setup() {
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load lifeExpectancy data
		lifeExpMap = ParseFeed.loadLifeExpectancyFromCSV(this,"LifeExpectancyWorldBank.csv");
		covidMap = ParseFeed.loadCOVIDFromCSV(this,"WHO-COVID-19-global-data.csv");
		//for (String key : covidMap.keySet()) {
			//if (covidMap.get(key) > -101) {
			//System.out.println(key + " Rate of increase " + covidMap.get(key));
			//}		
		//}
		
		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		System.out.println(countryMarkers.get(0).getProperties().get("name"));
		
		// Country markers are shaded according to life expectancy (only once)
		shadeCountries();
	}

	public void draw() {
		// Draw map tiles and country markers
		map.draw();
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
				System.out.println("numFoundConuntries " + numFoundConuntries);
				float covidRate = covidMap.get(countryName);
				// Encode value as brightness (values range: 40-90)
				if (covidRate < -50) {
					//int colorLevel = (int) map(covidRate, -100, 400, 0, 255);
					marker.setColor(color(10, 250, 0));
				}
				if (covidRate > -50 && covidRate < 0) {
					marker.setColor(color(75, 150, 0));
				}
				if (covidRate >= 0 && covidRate < 100) {
					marker.setColor(color(204, 204, 0));
				}
				if (covidRate >= 100 && covidRate < 200) {
					marker.setColor(color(255, 128, 0));
				}
				if (covidRate >= 200) {
					marker.setColor(color(205, 0, 0));
				}
				
			}
			else {
				marker.setColor(color(255,255,255));
			}
		}
	}


}
