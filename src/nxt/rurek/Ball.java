package nxt.rurek;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.IRSeekerV2;

public class Ball {

	
	private IRSeekerV2 ir;
	
	public Ball () {
		super();
		ir = new IRSeekerV2(SensorPort.S2, IRSeekerV2.Mode.AC);
	}
	
	/**
	 * Function findBall
	 * @return angle in [-180, 180] describing where is the ball.
	 */
	public Direction findBall() {
		return getDirection(ir.getSensorValues());
	}
	
	public Direction getDirection ( int[] val) {
		
		int sum = 0;
		for (int i : val) {
			sum += i;
		}
		
		double avg = (double) sum / 5;
		double dv = 0;
		for (int i : val) {
			dv += Math.pow(Math.abs(avg-i), 2);
		}
		if (sum < 10 || dv < 150) {
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
		double angle = 0;
		if (best < 4){
			angle = 60.0 * (best - 2);
			angle += 60.0 * ((double)val[best+1]/ (val[best]+val[best+1]));
		} else if (val[4] > val[0]) {
			angle = 120;
			angle += 120.0*((double)val[0]/(val[0]+val[4]));
		} else {
			angle = -120;
			angle -= 120.0*((double)val[4]/(val[0]+val[4]));
		}
		
		return new Direction (angle);
	}
}
