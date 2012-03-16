package nxt.rurek;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.navigation.Pose;


public class Rurek {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ball ball = new Ball();
		Direction lastTarget = ball.findBall();
		PositionController move = new PositionController();
		Pose begin = move.getNavigator().getPoseProvider().getPose();
		LCD.drawString("old dir: " + lastTarget.getAngle(), 0, 1);
		LCD.drawString("new dir: " + lastTarget.getAngle(), 0, 2);
		try {
			while (!Button.ESCAPE.isDown()) {
				Direction current = ball.findBall();
				if(lastTarget.isInRange()){
					if(!inRange(lastTarget, current)){
						LCD.drawString("old dir: " + lastTarget.getAngle(), 0, 1);
						LCD.drawString("new dir: " + current.getAngle(), 0, 2);
						move.goToPosition(current);
						lastTarget = current;
					}
				}
				else if(current.isInRange()) {
					LCD.drawString("old dir: --", 0, 1);
					LCD.drawString("new dir: " + lastTarget.getAngle(), 0, 2);
					move.goToPosition(current);
					lastTarget = current;
				}
				else {
					LCD.drawString("old dir: --", 0, 1);
					LCD.drawString("new dir: --", 0, 2);
					move.goToPosition(current);
				}
				Thread.sleep(200);
			}
			move.getDifferentialPilot().stop();
			while (!Button.ENTER.isDown()) {}
			move.getNavigator().goTo(new Waypoint(begin));
			while (!Button.ESCAPE.isDown()) {}
		}
		catch (InterruptedException ex){
		}
	}
	
	private static boolean inRange(Direction old_direction, Direction new_direction) {
		double old_angle = Math.abs(old_direction.getAngle());
		double new_angle = Math.abs(new_direction.getAngle());
		return !old_direction.isInRange() || (sameSign(old_direction, new_direction) && new_angle > old_angle/2 &&
				old_angle > new_angle) || (old_angle < 5 && new_angle < 5);
	}
	
	private static boolean sameSign(Direction dir1, Direction dir2) {
		return dir1.getAngle() * dir2.getAngle() > 0;
	}

}
