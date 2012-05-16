package nxt.rurek.conditions;

import nxt.rurek.Direction;
import nxt.rurek.position.Situation;

public class HasBall extends Condition{
	public boolean check (Situation s) {
		Direction bd = s.getBl().getLast();
		return bd.hasDistance() && bd.getDistance() < 5;
	}
}
