package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet{
	public void setup() {
		size(400, 400);
		background(200, 200, 200);
	}

	public void draw() {
		fill(255, 255, 0);
		ellipse(200, 200, 390, 390);
		fill(0, 0, 0);
		ellipse(120, 120, 30, 20);
		ellipse(280, 120, 30, 20);
		//noFill();
		arc(200, 270, 140, 100, 0, PI);
	}
}
