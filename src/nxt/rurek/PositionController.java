package nxt.rurek;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class PositionController {
	private static int max_speed = 360;
	private static int max_round_speed = 150; 
	private DifferentialPilot plot;
	
	public PositionController() {
		plot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		plot.setRotateSpeed(4);
	}
	
	DifferentialPilot getDifferentialPilot() {
		return plot;
	}

	public void goToPosition(Direction direction) {
		if(!direction.isInRange()){
			plot.rotateLeft();
		}
		else {
			if(Math.abs(direction.getAngle()) < 30) {
				plot.forward();
			}
			else {
				plot.arcForward(Math.sin(direction.getAngle()) * (120 - Math.abs(direction.getAngle())));
			}
		}
	}
}
