package nxt.rurek.geometry;

import lejos.robotics.navigation.Pose;
import nxt.rurek.Environment;

public class Functions {

	public static double sqr (double a) {
		return a * a;
	}
	
	public static Point angleToDirection (double a) {
		return new Point (Math.cos(Math.toRadians(a)), Math.sin(Math.toRadians(a)));
	}
	
	public static Point getTarget () {
		double x = Environment.getEnvironment().getWidth()/2;
		double y = Environment.getEnvironment().getHeight();
		return new Point(x,y);
	}
	
	public static Point getLeftCorner() {
		return new Point (10, 10);
	}
	
	public static Point getFountain () {
		return new Point(Environment.getEnvironment().getWidth()/2,0);
	}
	
	public static Point fromPose (Pose p) {
		return new Point (p.getX(), p.getY());
	}
}
