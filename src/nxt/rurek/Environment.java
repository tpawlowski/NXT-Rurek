package nxt.rurek;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import nxt.rurek.exceptions.EnvironmentException;

public class Environment {
	private static Environment singleton;
	double width;
	double height;
	NXTRegulatedMotor leftMotor;
	NXTRegulatedMotor rightMotor;
	NXTRegulatedMotor headMotor;
	CompassHTSensor compass;
	
	public static Environment defaultEnvironment() {
		singleton = new Environment();
		singleton.width = 100;
		singleton.height = 150;
		singleton.leftMotor = Motor.A;
		singleton.rightMotor = Motor.B;
		singleton.headMotor = Motor.C;
		singleton.compass = new CompassHTSensor(SensorPort.S4);
		return singleton;
	}
	
	public static Environment createEnvironment(double width, double height,
			NXTRegulatedMotor left, NXTRegulatedMotor right, NXTRegulatedMotor head,
			CompassHTSensor compass) {
		singleton = new Environment();
		singleton.width = width;
		singleton.height = height;
		singleton.leftMotor = left;
		singleton.rightMotor = right;
		singleton.compass = compass;
		return singleton;
	}

	public static Environment getEnvironment() throws EnvironmentException {
		if(singleton == null) {
			throw new EnvironmentException("Null environment");
		}
		return singleton;
	}
	
	private Environment(){	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
}
