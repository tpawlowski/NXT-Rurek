package nxt.rurek;

import nxt.rurek.position.BallDistanceEstimator;

public class Direction {
	
	/* angle is in [-180, 180) */
	private double angle, distance;
	private boolean inRange;
	
	public static double normalize (double angle) {
		double nangle = angle - 360 * (int)((angle / 360) + 0.5);
		if (nangle < -180) nangle += 360;
		if (nangle >= 180) nangle -= 360;
		return nangle;
	}
	
	public Direction(double angle) {
		this();
		this.angle = normalize(angle);
		this.inRange = true;
	}
	
	public Direction(double angle, double distance) {
		this(angle);
		this.distance = distance;
	}
	
	public Direction () {
		super();
		this.angle=0;
		this.distance = -1;
		this.inRange = false;
	}
	
	/**
	 * Creates new direction from ir sensor values
	 * @param val
	 */
	public Direction (int[] val) {
		this();
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
			return; /* not in range */
		} else {
			this.inRange = true;
		}
		
		int m = 0;
		int best = 0;
		for (int i = 0; i < 5; ++i) {
			if (m < val[i%5] + val[(i+1)%5]) {
				m = val[i%5] + val[(i+1)%5];
				best = i;
			}
		}
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
		
		double maybeDistance = BallDistanceEstimator.getEstimation(val);
		if (maybeDistance >= 0) {
			this.distance = maybeDistance;
		} else {
			this.distance = -1;
		}
	}

	public boolean hasDistance() {
		return distance >=0;
	}
	
	public boolean isInRange() {
		return inRange;
	}
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public boolean isInFront() {
		if (!inRange) {
			return false;
		} else {
			return Math.abs(this.angle) < 30;
		}
	}
	
	public double getAngleDistance (double angleB) {
		return Math.abs(normalize(angleB-this.angle));
	}
}
