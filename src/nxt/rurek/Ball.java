package nxt.rurek;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.IRSeekerV2;

public class Ball {

	
	private IRSeekerV2 ir;
	
	public Ball () {
		super();
		ir = new IRSeekerV2(SensorPort.S2, IRSeekerV2.Mode.AC);
	}
	
	private double getMean (int a, int b) {
		return ((double)b)/(a+b);  
	}
	
	/**
	 * Function findBall
	 * @return angle in [-180, 180] saying where is the ball.
	 */
	public Direction findBall() {
		int[] val = ir.getSensorValues();
		
		int sum = 0;
		for (int i : val) {
			sum += i;
		}
		if (sum < 100) {
			return new Direction();
		}
		
		int m = 0;
		int best = 0;
		for (int i = 0; i < 5; ++i) {
			if (m < val[i%5] + val[(i+1)%5]) {
				m = val[i%5] + val[(i+1)%5];
				best = i;
			}
		}
		
		double mul = (best < 4) ? 60.0 : 120.0;
		
		return new Direction (60*(best-2)+ mul*getMean(val[best], val[(best+1)%5]));
	}
}
