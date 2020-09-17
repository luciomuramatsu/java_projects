package module3;
import processing.core.*;

public class MyPApplet extends PApplet{

	private String URL = "https://alliancebjjsf.com/assets/images/banner_modified.jpg";
	private PImage backgroundImg;
	
	public void setup() {
		size(800,600);
		backgroundImg = loadImage(URL, "jpg");
		backgroundImg.resize(0, height);
		image(backgroundImg, 0, 0);
	}
	
	public void draw() {
		int[] color = sunColorSec(second());
		fill(color[0], color[1] ,color[2]);
		ellipse(width/4, height/5,width/4, height/5);
	}
	
	public int[] sunColorSec(float seconds) {
		int[] rgb = new int[3];
		float diff30Ratio = Math.abs(30-seconds)/30;
		rgb[0] = (int)(255 * diff30Ratio);
		rgb[1] = (int)(255 * diff30Ratio);
		rgb[2] = 0;
		
		
		return rgb;
		
	}
}
