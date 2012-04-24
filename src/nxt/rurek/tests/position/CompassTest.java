package nxt.rurek.tests.position;

import lejos.nxt.Button;
import nxt.rurek.Environment;
import nxt.rurek.position.HeadController;
import nxt.rurek.position.Position;

public class CompassTest {
	public static void main(String[] args) {
		/*CompassHTSensor compass = Environment.getEnvironment().getCompass();
		compass.resetCartesianZero();
		while(!Button.ESCAPE.isDown()) {
			LCD.drawString("Compass: " + compass.getDegreesCartesian() + "    ", 0, 0);
			Button.ENTER.waitForPress();
		}*/
		
		Environment.getEnvironment().getCompass().resetCartesianZero();
		HeadController head = new HeadController();
		Position pos = new Position();
		head.addMeasurementListener(pos);
		head.start();
		
		Button.ESCAPE.waitForPress();
		head.resetHead();
	}
}
