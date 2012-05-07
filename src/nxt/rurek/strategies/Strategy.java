package nxt.rurek.strategies;

import nxt.rurek.movement.PositionController;

public abstract class Strategy {

	public Strategy () {
		super ();
	}
	/* Rurek plays with this strategy until condition is set to true */
	public abstract void playWith(PositionController move);

}
