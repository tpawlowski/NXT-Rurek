package nxt.rurek.conditions;

import nxt.rurek.position.Situation;

public class NotThis extends Condition{

	Condition c;
	
	public NotThis(Condition c) {
		super ();
		this.c = c;
	}
	
	@Override
	public boolean check(Situation s) {
		return !c.check(s);
	}

}
