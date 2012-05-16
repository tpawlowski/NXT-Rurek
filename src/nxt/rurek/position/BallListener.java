package nxt.rurek.position;

import nxt.rurek.Direction;

public class BallListener {
	
	private int acc;
	private Direction[] lastPos;
	private int n = 5;
	private int no;
	
	public BallListener() {
		lastPos = new Direction[n];
		acc = 0;
		no = 0;
		lastPos[0] = new Direction();
	}
	
	/** i remember 5 last seen, witch times of unseen */
	public void gotMeasure(int[] v) {
		acc = (acc+1)%n;
		lastPos[acc] = new Direction(v);
		no++;	
	}
	
	public Direction getPrevious() {
		return lastPos[(acc+n-1)%n];
	}
	
	public Direction getLast () {
		//return new Direction(0, 100);
		return lastPos[acc];
	}
	
	public Direction getAny() {
		if (lastPos[acc].isInRange()) {
			return lastPos[acc];
		} else if (lastPos[(acc+n-1)%n] != null && lastPos[(acc+n-1)%n].isInRange()) {
			return lastPos[(acc+n-1)%n];
		} else {
			return lastPos[acc];
		}
	}
	
	public int getMeasureNo() {
		return no;
	}
}
