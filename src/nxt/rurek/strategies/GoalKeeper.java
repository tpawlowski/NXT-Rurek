package nxt.rurek.strategies;

import nxt.rurek.Ball;
import nxt.rurek.Direction;
import nxt.rurek.conditions.Condition;
import nxt.rurek.exceptions.PositionLostException;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.Position;

/**
 * This strategy tries to stand all the time between goal and the ball
 * @author tomasz
 *
 */
public class GoalKeeper extends Strategy{

	/**
	 * Checks if my position is suitable for goalkeeping.
	 * @param myPosition
	 * @param ballDirection
	 * @return true if my position is good for keeping the ball.
	 */
	public static boolean isKeeping (Position myPosition, Direction ballDirection) {
		if (!ballDirection.isInRange()) return false;
		/* position is correct if Rurek has goal on his back and ball in front of him */
		Direction goalRDirection = myPosition.getGoalRelativeDirection();
		if (!goalRDirection.isInRange()) return false;
		return ballDirection.isInFront() && ballDirection.getAngleDistance(goalRDirection.getAngle()) < 30;
	}
	
	@Override
	public void playWith(Condition cond, PositionController move) throws PositionLostException {
		Position myPosition = new Position ();
		Direction ballDirection;
		while (true) {
			
			// TODO update position
			
			if (!myPosition.isPositionKnown()) {
				throw new PositionLostException();
			}
			
			ballDirection = Ball.findBall();
			if (!ballDirection.hasDistance()){
				ballDirection.setDistance(15);
			}
			
			if (!isKeeping(myPosition, ballDirection)) {
				/* evade the ball on the side of a goal */
			}
			
			if (cond.check()) {
				break;
			}
		}
		
	}

}
