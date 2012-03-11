package nxt.rurek;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class MoveController {
	NXTRegulatedMotor left = Motor.B;
	NXTRegulatedMotor right = Motor.A;
	private static int max_speed = 360;
	private static int max_round_speed = 150; 
	
	
	public void goToPosition(Direction direction) {
		int left_speed = max_speed;
		int right_speed = max_speed;
		if(!direction.isInRange()) {
			left_speed = -1 * max_round_speed;
			right_speed = max_round_speed;
		}
		else {
			if(direction.getAngle() < 0){
				left_speed *= ((180 + direction.getAngle()) / 180);
			}
			else {
				right_speed *= ((180 - direction.getAngle()) / 180);
			}
		}
		left.setSpeed(Math.abs(left_speed));
		right.setSpeed(Math.abs(right_speed));
		if(left_speed > 0)
			left.forward();
		else
			left.backward();
		if(right_speed > 0)
			right.forward();
		else
			right.backward();
	}
	
}
