package nxt.rurek;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import nxt.rurek.exceptions.EnvironmentException;

public class Environment {
	private static Environment singleton;
	double width;
	double height;
	NXTRegulatedMotor leftMotor;
	NXTRegulatedMotor rightMotor;
	NXTRegulatedMotor headMotor;
	UltrasonicSensor ultrasens;

	CompassHTSensor compass;
	
	public static Environment defaultEnvironment() {
		singleton = new Environment();
		singleton.width = 100;
		singleton.height = 150;
		singleton.leftMotor = Motor.A;
		singleton.rightMotor = Motor.B;
		singleton.headMotor = Motor.C;
		singleton.compass = new CompassHTSensor(SensorPort.S4);
		singleton.ultrasens = new UltrasonicSensor(SensorPort.S1);
		
		return singleton;
	}
	

	public static Environment createEnvironment(double width, double height,
			NXTRegulatedMotor left, NXTRegulatedMotor right, NXTRegulatedMotor head,
			CompassHTSensor compass, UltrasonicSensor ultras) {
		singleton = new Environment();
		singleton.width = width;
		singleton.height = height;
		singleton.leftMotor = left;
		singleton.rightMotor = right;
		singleton.compass = compass;
		singleton.ultrasens = ultras;
		return singleton;
	}

	public static Environment getEnvironment(){
		if(singleton == null) {
			defaultEnvironment();
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
	
	public NXTRegulatedMotor getLeftMotor() {
		return leftMotor;
	}

	public NXTRegulatedMotor getRightMotor() {
		return rightMotor;
	}

	public NXTRegulatedMotor getHeadMotor() {
		return headMotor;
	}
	
	public CompassHTSensor getCompass() {
		return compass;
	}

	public UltrasonicSensor getUltrasonicSensor() {
		return ultrasens;
	}

}
