package nxt.rurek;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.addon.IRSeekerV2;

public class RushSeeking {
	
	private IRSeekerV2 ir;
	private CompassHTSensor compass;
	
	public RushSeeking () {
		super();
		ir = new IRSeekerV2(SensorPort.S2, IRSeekerV2.Mode.AC);
		compass = new CompassHTSensor(SensorPort.S4);	
	}
	
	
	private double getMean (int a, int b) {
		return ((double)b)/(a+b);  
	}
	
	/**
	 * Function findBall
	 * @return angle in [-180, 180] saying where is the ball.
	 */
	private double findBall() {
		int[] val = ir.getSensorValues();
		
		int sum = 0;
		for (int i : val) {
			sum += i;
		}
		if (sum < 100) {
			return -1000;
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
		
		return 60*(best-2)+ mul*getMean(val[best], val[(best+1)%5]);
	}
	
	public void run() {
		float ball_dir = findBall();
	}
	
	public void stopSeeking () { 
		
	}
}
