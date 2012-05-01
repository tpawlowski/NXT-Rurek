package nxt.rurek.conditions;

import nxt.rurek.Ball;
import nxt.rurek.position.Position;
import nxt.rurek.strategies.GoalKeeper;

public class GoalKeeperCondition extends Condition {

	@Override
	public boolean check() {
		//TODO get position from somewhere
		Position myPosition = new Position();
		return GoalKeeper.isKeeping(myPosition, Ball.findBall());
	}

	
}
