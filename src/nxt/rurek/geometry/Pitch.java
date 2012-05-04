package nxt.rurek.geometry;

import nxt.rurek.PitchDescription;
import nxt.rurek.exceptions.NoIntersectionException;

public class Pitch extends Shape{
	
	Segment bounds[];
	
	public Pitch (PitchDescription p) {
		bounds = new Segment[10];
		bounds[0] = new Segment(new Point(0,0), new Point(0, p.height));
		bounds[1] = new Segment(new Point(0,p.height), new Point(p.width, p.height));
		bounds[2] = new Segment(new Point(p.width, p.height), new Point(p.width, 0));
		bounds[3] = new Segment(new Point(p.width, 0), new Point(0, 0));
		bounds[4] = new Segment(new Point(p.leftPost,p.height), new Point(p.leftPost, p.height-p.penaltyDepth));
		bounds[5] = new Segment(new Point(p.leftPost,p.height-p.penaltyDepth), new Point(p.leftPost + p.penaltyWidth, p.height-p.penaltyDepth));
		bounds[6] = new Segment(new Point(p.leftPost + p.penaltyWidth, p.height-p.penaltyDepth), new Point(p.leftPost + p.penaltyWidth, p.height));
	}
	
	public double getMaxForward(Point p, double a) {
		double min = 1000000; //infty
		Ray r;
		for (int i = 0; i < bounds.length; ++i) {
			r = new Ray(p, Functions.angleToDirection(a));
			try {
				min = Math.min(min, p.getDistance(r.getIntersection(bounds[i])));
			} catch (NoIntersectionException e) {} //trivial catch, because no other is needed
		}
		return min;
	}
}
