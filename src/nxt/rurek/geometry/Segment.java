package nxt.rurek.geometry;

import nxt.rurek.exceptions.NoIntersectionException;

public class Segment extends Line{
	private Point a,b; /* segment endpoints */

	public Segment(Point a, Point b) {
		super(a, b); 
	}
	
	public boolean inSegment (Point b) {
		return (super.contains(b) && Math.abs(b.getDistance(getA()) + b.getDistance(getB()) - getA().getDistance(getB())) < 0.05);
	}
	
	public Point getIntersection (Segment b) throws NoIntersectionException {
		Point ret = super.getIntersection(b);
		if (!inSegment(ret) || !b.inSegment(ret)) {
			throw new NoIntersectionException();
		}
		return ret;
	}
}
