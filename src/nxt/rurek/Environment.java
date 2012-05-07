package nxt.rurek;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.addon.IRSeekerV2;
import nxt.rurek.geometry.Pitch;

public class Environment {
	private static Environment singleton;
	double width;
	double height;
	NXTRegulatedMotor leftMotor;
	NXTRegulatedMotor rightMotor;
	NXTRegulatedMotor headMotor;
	UltrasonicSensor ultrasens;
	IRSeekerV2 irsensor;
	Pitch pitchModel; 

	final double leftPost = 40;
	final double rightPost = 80;
	final double goalSize = 40;
	final double penaltyDepth = 30;
	final double penaltyWidth = 50;
	final double closerMidline = 70;
	
	final double Robotwidth = 20.5;
	final double RobotLength = 18;
	final double RobotHeight = 10;
	final double RobotWheel = 4.8;
	
	public static Environment getSingleton() {
		return singleton;
	}

	CompassHTSensor compass;
	
	public static Environment defaultEnvironment() {
		singleton = new Environment();
		singleton.width = 120;
		singleton.height = 180;
		singleton.pitchModel = new Pitch(new PitchDescription());
		singleton.leftMotor = Motor.B;
		singleton.rightMotor = Motor.A;
		singleton.headMotor = Motor.C;
		singleton.compass = new CompassHTSensor(SensorPort.S4);
		singleton.ultrasens = new UltrasonicSensor(SensorPort.S1);
		singleton.irsensor = new IRSeekerV2(SensorPort.S2, IRSeekerV2.Mode.AC);
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
	public double getLeftPost() {
		return leftPost;
	}


	public IRSeekerV2 getIrsensor() {
		return irsensor;
	}


	public double getRobotwidth() {
		return Robotwidth;
	}


	public double getRobotLength() {
		return RobotLength;
	}


	public double getRobotHeight() {
		return RobotHeight;
	}


	public double getRightPost() {
		return rightPost;
	}


	public double getGoalSize() {
		return goalSize;
	}

	public double getRobotWheel() {
		return RobotWheel;
	}
}
