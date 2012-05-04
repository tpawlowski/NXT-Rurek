package nxt.rurek.geometry;

public class Functions {

	public static double sqr (double a) {
		return a * a;
	}
	
	public static Point angleToDirection (double a) {
		return new Point (Math.cos(Math.toRadians(a)), Math.sin(Math.toRadians(a)));
	}
}
