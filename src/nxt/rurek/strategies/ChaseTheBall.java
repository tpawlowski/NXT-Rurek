package nxt.rurek.strategies;

import nxt.rurek.Ball;
import nxt.rurek.Direction;
import nxt.rurek.conditions.Condition;
import nxt.rurek.movement.PositionController;

public class ChaseTheBall extends Strategy{
	
	public ChaseTheBall() {
		super ();
	}
	
	@Override
	public void playWith(Condition cond, PositionController move) {
		Direction ballDirection;
		while (true) {
			boolean foundInRange = false;
			while (!foundInRange) {
				ballDirection = Ball.findBall();
				foundInRange = ballDirection.isInRange();
				if (!foundInRange) {
					
				}
			}
			
			ballDirection = Ball.findBall();
			if (!ballDirection.hasDistance()){
				ballDirection.setDistance(15);
			}
			move.goToPosition(ballDirection);
			if (cond.check()) {
				break;
			}
		}
	}
}
