package nxt.rurek;

import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import nxt.rurek.geometry.Point;
import nxt.rurek.position.BallDistanceEstimator;

public class Direction {
	
	/* angle is in [0, 360) */
	private double angle, distance;
	private boolean inRange;
	
	public static double normalize (double angle) {
		double nangle = angle;
		while (nangle < 0) nangle += 360;
		while (nangle >= 360 ) nangle -= 360; 
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
	
		angle = Direction.normalize(-angle);
		double maybeDistance = BallDistanceEstimator.getEstimation(val);
		this.distance = maybeDistance;
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
	
	public double getRotation() {
		if (angle > 180) return angle-360;
		else return angle;
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
	
	public double getImperativeAngle(double robotAngle) {
		return Direction.normalize(robotAngle+getAngle()-90);
	}
	
	public double getAngleDistance (double angleB) {
		return Math.abs(normalize(angleB-this.angle));
	}
	
	
	
	public Point toPoint (Pose p) {
		double d = getDistance();
		double a = Direction.normalize(p.getHeading() + getAngle() - 90); 
		return new Point (p.getX()+d*Math.cos(Math.toRadians(a)),p.getY()+d*Math.sin(Math.toRadians(a)));
	}
	
	public Waypoint toWaypoint (Pose p) {
		Waypoint ret = new Waypoint(p);
		double d;
		if (!hasDistance()) {
			d = 20;
		} else {
			d = getDistance();
		}
		if (isInRange()) {
			ret.setLocation(ret.getX()-d*Math.sin(getAngle()), ret.getY()+d*Math.cos(getAngle()));
		}
		return ret;
	}
	
}
