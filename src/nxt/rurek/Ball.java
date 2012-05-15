package nxt.rurek;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.IRSeekerV2;


/** @deprecated */
public class Ball {

	
	private static IRSeekerV2 ir;
	
	private static void init() {
		ir = new IRSeekerV2(SensorPort.S2, IRSeekerV2.Mode.AC);
	}
	
	public Ball () {
		super();
		init();
	}
	
	/**
	 * Function findBall
	 * @return angle in [-180, 180] describing where is the ball.
	 */
	public static Direction findBall() {
		if (ir == null) {
			init();
		}
		return getDirection(ir.getSensorValues());
	}
	
	public static Direction getDirection ( int[] val) {
		return new Direction (val);
	}
}
