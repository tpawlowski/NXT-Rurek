package nxt.rurek;

import lejos.nxt.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class PositionController {
	private static int max_speed = 360;
	private static int max_round_speed = 150; 
	private DifferentialPilot pilot;
	private Navigator navigator;
	
	public PositionController() {
		pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		//ilot = new CompassPilot(new CompassHTSensor(SensorPort.S4), 8.95, 14.8, Motor.B, Motor.A);
		navigator = new Navigator(pilot);
		pilot.setTravelSpeed(30);
	}
	
	DifferentialPilot getDifferentialPilot() {
		return pilot;
	}
	
	Navigator getNavigator() {
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
			}
		}
	}
}
