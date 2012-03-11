package nxt.rurek;

import lejos.nxt.Button;

public class Rurek {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ball ball = new Ball();
		Direction lastTarget = ball.findBall();
		MoveController move = new MoveController();
		while (!Button.ESCAPE.isDown()) {
			Direction current = ball.findBall();
			if(lastTarget.isInRange()){
				if(!inRange(lastTarget, current)){
					move.goToPosition(current);
					lastTarget = current;
				}
			}
			else if(current.isInRange()) {
				move.goToPosition(current);
			}
		}
	}
	
	private static boolean inRange(Direction old_direction, Direction new_direction) {
		double old_angle = Math.abs(old_direction.getAngle());
		double new_angle = Math.abs(new_direction.getAngle());
		return (sameSign(old_direction, new_direction) && new_angle > old_angle/2 &&
				old_angle > new_angle) || (old_angle < 5 && new_angle < 5);
	}
	
	private static boolean sameSign(Direction dir1, Direction dir2) {
		return dir1.getAngle() * dir2.getAngle() > 0;
	}
}
