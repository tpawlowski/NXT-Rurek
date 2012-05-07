package nxt.rurek.position;

import nxt.rurek.Direction;

public class BallListener {
	
	private int acc;
	private Direction[] lastPos;
	private int n = 5;
	
	public BallListener() {
		lastPos = new Direction[n];
		acc = 0;
		lastPos[0] = new Direction();
	}
	
	/** i remember 5 last seen, wiech times of unseen */
	public void gotMeasure(int[] v) {
		acc = (acc+1)%n;
		lastPos[acc] = new Direction(v);
	}
	
	public Direction getLast () {
		return lastPos[acc];
	}
}
