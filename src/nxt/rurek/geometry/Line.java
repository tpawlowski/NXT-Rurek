package nxt.rurek.geometry;

import nxt.rurek.exceptions.NoIntersectionException;

public class Line {
	Point a, b;
	Point d;
	
	public Point getA() {
		return a;
	}

	public void setA(Point a) {
		this.a = a;
		calculateD();
	}

	public Point getB() {
		return b;
	}

	public void setB(Point b) {
		this.b = b;
		calculateD();
	}
	
	public double getDirection () {
		return a.getAngle(b);
	}
	
	public double getDistance (Point b) {
		return b.getDistance(getNeariest(b));
	}
	
	private void calculateD () {
		setD(b.subP(a).normalizeP());
	}
	
	public Point getD() {
		return d;
	}

	public void setD(Point d) {
		this.d = d;
	}

	public Line(Point a, Point b) {
		super();
		this.a = a;
		this.b = b;
		calculateD();
	}

	public Point getNeariest (Point b) {
		return getA().addP(getD().mulP(b.subP(getA()).getDotProduct(getD())));
	}
	
	public boolean contains (Point b) {
		return getNeariest(b).getDistance(b) < 0.01;
	}
	
	public Point getIntersection (Line x) throws NoIntersectionException{
		Point d = getD();
		Point e = x.getD();
		double detA = d.getX() * e.getY() - d.getY() * e.getX();
		if (detA < 0.01) throw new NoIntersectionException();
		Point A = getA();
		A.sub(x.getA());
		double s = (e.getY()*A.getX() - d.getY() *A.getY())/detA;
		Point ret = x.getA();
		ret.add(x.getD().mulP(s));
		return ret;
	}
}
