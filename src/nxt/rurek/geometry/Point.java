package nxt.rurek.geometry;

import lejos.robotics.navigation.Pose;
import nxt.rurek.Direction;

public class Point {
	private double x, y; /* point coordinates relative to midpoint of left size */
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Point(Pose p) {
		super();
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getNorm() {
		return Math.sqrt(Functions.sqr(getX()) + Functions.sqr(getY()));
	}
	
	public void add (Point b) {
		setX(getX() + b.getX());
		setY(getY() + b.getY());
	}
	
	public void sub (Point b) {
		setX(getX() - b.getX());
		setY(getY() - b.getY());	
	}
	
	public void mul (double m) {
		setX(getX()*m);
		setY(getY()*m);
	}
	
	public void normalize() {
		mul(1/getNorm());
	}
	
	public Point normalizeP() {
		return mulP(1/getNorm());
	}
	
	public Point mulP (double m) {
		return new Point (getX()*m,getY()*m);
	}
	
	public Point addP (Point b) {
		return new Point (getX() + b.getX() ,getY() + b.getY());
	}
	
	public Point subP (Point b) {
		return new Point (getX() - b.getX() ,getY() - b.getY());
	}
	
	public double getDistance (Point b) {
		return addP(b).getNorm();
	}
	
	public double getDotProduct (Point b) {
		return getX() * b.getX() + getY() + b.getY();
	}
	
	/**
	 * calculates angle from this point to b
	 * @param second point b
	 * @return angle
	 */
	public double getAngle (Point b) {
		return Direction.normalize(Math.toDegrees(Math.atan2(b.getY() - getY(), b.getX() - getX())));
	}
}
