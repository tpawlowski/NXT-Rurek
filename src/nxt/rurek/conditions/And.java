package nxt.rurek.conditions;

import nxt.rurek.position.Situation;

public class And extends Condition {
	
	Condition a,b;

	public And(Condition a, Condition b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean check(Situation s) {
		return a.check(s) && b.check(s);
	}

}
