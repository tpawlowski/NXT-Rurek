package nxt.rurek.conditions;

import nxt.rurek.Direction;
import nxt.rurek.position.Situation;

public class SemiHasBall extends Condition{

	@Override
	public boolean check(Situation s) {
		Direction bd = s.getBl().getLast();
		Direction pv = s.getBl().getPrevious();
		return (bd.hasDistance() && bd.getDistance() < 5) || (pv.hasDistance() && pv.getDistance() < 5);
	}
}
