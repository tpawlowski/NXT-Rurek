package nxt.rurek.strategies;

import nxt.rurek.conditions.Condition;
import nxt.rurek.exceptions.PositionLostException;
import nxt.rurek.movement.MoveController;

public abstract class Strategy {

	public Strategy () {
		super ();
	}
	
	/* Rurek plays with this strategy until condition is set to true */
	abstract void playWith(Condition cond, MoveController move) throws PositionLostException;

}
