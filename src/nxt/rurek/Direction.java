package nxt.rurek;

public class Direction {
	
	/* angle is in [-180, 180) */
	private double angle, distance;
	private boolean inRange;
	
	private double normalize (double angle) {
		double nangle = angle - 360 * (int)((angle / 360) + 0.5);
		if (nangle < -180) nangle += 360;
		if (nangle >= 180) nangle -= 360;
		return nangle;
	}
	
	public Direction(double angle) {
		super();
		this.angle = normalize(angle);
		this.distance = -1;
		this.inRange = true;
	}
	
	public Direction () {
		this.angle=0;
		this.distance = -1;
		this.inRange = false;
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

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	
}
