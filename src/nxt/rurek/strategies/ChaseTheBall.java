package nxt.rurek.strategies;

import nxt.rurek.Ball;
import nxt.rurek.Direction;
import nxt.rurek.conditions.Condition;
import nxt.rurek.movement.MoveController;

public class ChaseTheBall extends Strategy{
	
	public ChaseTheBall() {
		super ();
	}
	
	@Override
	void playWith(Condition cond, MoveController move) {
		Direction ballDirection;
		while (true) {
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
