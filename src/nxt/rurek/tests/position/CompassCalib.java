package nxt.rurek.tests.position;

import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.DifferentialPilot;
import nxt.rurek.Environment;

public class CompassCalib {
	public static void main(String[] args) {
		Environment env = Environment.getEnvironment();
		DifferentialPilot pilot = new DifferentialPilot(6.1,  13.4, env.getLeftMotor(), env.getRightMotor());
		CompassHTSensor compass = env.getCompass();
		pilot.setRotateSpeed(15);
		compass.startCalibration();
		pilot.rotate(360 * 2);
		compass.stopCalibration();
		
	}
}
