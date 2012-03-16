package nxt.rurek;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.CompassPilot;
//import lejos.robotics.navigation.DifferentialPilot;

@SuppressWarnings("deprecation")
public class PositionController {
	private static int max_speed = 360;
	private static int max_round_speed = 150; 
	private CompassPilot pilot;
	
	
	public PositionController() {
		//pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		pilot = new CompassPilot(new CompassHTSensor(SensorPort.S4), 8.95f, 14.8f, Motor.B, Motor.A);
		pilot.setTravelSpeed(80);
	}
	
	CompassPilot getDifferentialPilot() {
		return pilot;
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
