package nxt.rurek.conditions;

import nxt.rurek.position.Situation;

public class DontSeeBall extends Condition{

	@Override
	public boolean check(Situation s) {
		return !s.getBl().getLast().isInRange();
	}
}
