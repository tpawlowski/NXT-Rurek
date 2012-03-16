package nxt.rurek;

import lejos.nxt.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;

public class PositionController {
	private static int max_speed = 360;
	private static int max_round_speed = 150; 
	private DifferentialPilot pilot;
	private PoseProvider pose_provider;
	
	public PositionController() {
		pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		//ilot = new CompassPilot(new CompassHTSensor(SensorPort.S4), 8.95, 14.8, Motor.B, Motor.A);
		pose_provider = new OdometryPoseProvider(pilot);
		pilot.setTravelSpeed(30);
	}
	
	DifferentialPilot getDifferentialPilot() {
		return pilot;
	}
	
	PoseProvider getPoseProvider() {
		return pose_provider;
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
