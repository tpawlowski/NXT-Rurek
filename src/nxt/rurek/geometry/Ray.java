package nxt.rurek.geometry;

import nxt.rurek.exceptions.NoIntersectionException;

public class Ray extends Line{
	Point a, d;

	public Point getA() {
		return a;
	}

	public void setA(Point a) {
		this.a = a;
	}

	public Point getD() {
		return d;
	}

	public void setD(Point d) {
		this.d = d;
	}

	public Ray(Point a, Point d) {
		super(a,a.addP(d));
		this.a = a;
		this.d = d;
	}	
	
	public boolean contains (Point b) {
		return super.contains(b) && (b.getX() - getA().getX()) * getD().getX() > 0 && (b.getY() - getA().getY()) * getD().getY() > 0;
	}
	
	public Point getIntersection (Ray b) throws NoIntersectionException{
		Point ret = super.getIntersection(b);
		if (!contains(ret) || !b.contains(ret)) {
			throw new NoIntersectionException();
		} else {
			return ret;
		}
	}
	
	public Point getIntersection (Segment b) throws NoIntersectionException{
		Point ret = super.getIntersection(b);
		if (!contains(ret) || !b.contains(ret)) {
			throw new NoIntersectionException();
		} else {
			return ret;
		}
	}
}
