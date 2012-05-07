package nxt.rurek.strategies;

import lejos.robotics.navigation.Navigator;
import nxt.rurek.Ball;
import nxt.rurek.Direction;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.BallListener;

public class ChaseTheBall extends Strategy{
	
	public ChaseTheBall() {
		super ();
	}
	
	@Override
	public void playWith(PositionController move) {
		Navigator navigator = move.getNavigator();
		BallListener ball = move.getBallListener();
		Direction ballDirection;
		
		while (true) {
			boolean foundInRange = false;
			while (!foundInRange) {
				ballDirection = ball.getLast();
				foundInRange = ballDirection.isInRange();
				if (!foundInRange) {
					/* szukaj */
					double angle = (double) navigator.getPoseProvider().getPose().getHeading();
					navigator.rotateTo(Direction.normalize(angle+90));
				}
			}
			navigator.addWaypoint(ball.getLast().toWaypoint(navigator.getPoseProvider().getPose()));
			navigator.waitForStop();
			navigator.clearPath();
		}
	}
}
