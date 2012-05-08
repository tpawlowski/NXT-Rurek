package nxt.rurek.tests.movement;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class SquareMovement {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Dom Pawlowskiego: DifferentialPilot pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		//uczelnia :
		//DifferentialPilot pilot = new DifferentialPilot(7.8, 14.6, Motor.B, Motor.A);
		//DifferentialPilot pilot = new DifferentialPilot(5.6,  10.4, Motor.B, Motor.A);
		DifferentialPilot pilot = new DifferentialPilot(8.6, 18.6, Motor.B, Motor.A, true);
		CompassHTSensor compass = new CompassHTSensor(SensorPort.S4);
		compass.resetCartesianZero();
		while(!Button.ESCAPE.isDown()) {
			pilot.travel(20);
			pilot.rotate(90);
			
		}
		Button.ENTER.waitForPress();
		while(!Button.ESCAPE.isDown()) {
			pilot.travel(20);
			pilot.rotate(90);
			pilot.rotate(90);
			//pilot.rotate(180);
			
		}
	}

}
