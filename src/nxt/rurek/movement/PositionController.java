package nxt.rurek.movement;

import nxt.rurek.Direction;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.CompassPilot;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class PositionController {
	private static int max_speed = 360;
	private static int max_round_speed = 150; 
	private DifferentialPilot pilot;
	private Navigator navigator;
	
	public PositionController() {
		//pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		//Dom pawloskiego: pilot = new CompassPilot(new CompassHTSensor(SensorPort.S4), 8.95f, 14.8f, Motor.B, Motor.A);
		pilot = new CompassPilot(new CompassHTSensor(SensorPort.S4), 8.1f, 14.8f, Motor.B, Motor.A);
		//pilot = new DifferentialPilot(8.1f, 14.8f, Motor.B, Motor.A);
		navigator = new Navigator(pilot);
		pilot.setTravelSpeed(30);
	}
	
	public DifferentialPilot getDifferentialPilot() {
		return pilot;
	}
	
	public Navigator getNavigator() {
		return navigator;
	}

	public void goToPosition(Direction direction) {
		if(!direction.isInRange()){
			pilot.rotateLeft();
		}
		else {
			if(Math.abs(direction.getAngle()) < 30) {
				pilot.forward();
			}
			else {
				//pilot.arcForward(Math.signum(direction.getAngle()) * -1 * (125 - Math.abs(direction.getAngle())) / 2);
				pilot.steer(direction.getAngle() * -1);
				if(direction.getAngle() == 0);
					pilot.forward();
			}
		}
	}
}
