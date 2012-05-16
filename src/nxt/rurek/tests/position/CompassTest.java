package nxt.rurek.tests.position;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.addon.CompassHTSensor;
import nxt.rurek.Environment;

public class CompassTest {
	public static void main(String[] args) {
		CompassHTSensor compass = Environment.getEnvironment().getCompass();
		compass.resetCartesianZero();
		while(!Button.ESCAPE.isDown()) {
			LCD.drawString("Compass: " + compass.getDegreesCartesian() + "    ", 0, 0);
			Button.ENTER.waitForPress();
		}
		/*
		Environment.getEnvironment().getCompass().resetCartesianZero();
		Environment env = Environment.getEnvironment();
		HeadController head = new HeadController();
		PositionController controller = new PositionController();
		Pose currentPose = new Pose();
		currentPose.setLocation((int)env.getWidth() / 2, 0);
		controller.getNavigator().getPoseProvider().setPose(currentPose);
		Position pos = new Position(controller);
		head.addMeasurementListener(pos);
		
		head.start();
		while(!Button.ESCAPE.isDown()) {
			head.rotationStopped(null, 0, false, 0);
			Button.ENTER.waitForPress();
		}
		Button.ESCAPE.waitForPress();
		head.resetHead();
		*/
	}
}
