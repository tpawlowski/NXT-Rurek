package nxt.rurek.strategies;

import nxt.rurek.movement.MoveController;

public abstract class Strategy {
	
	protected MoveController move;

	public Strategy () {
		this.move = new MoveController();
	}
	
	abstract void playWith();

}
