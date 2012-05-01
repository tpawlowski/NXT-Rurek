package nxt.rurek.position;

import lejos.nxt.LCD;
import nxt.rurek.exceptions.EnvironmentException;
import nxt.rurek.Direction;
import nxt.rurek.constants.Pitch;


/**
 * Position is measured relative to center of the left side of the pitch
 * @author sstarzycki
 *
 */
public class Position implements MeasurementListener {
	
	private double x = -1;
	private double y = -1;
	private double rotation = -1;
	
	public Position(){}
	
	public void addMesasurement(Measurement m) {
		if(m.getDistance() < 150) {
			if(m.canCalculateX()) {
				try {
					this.x = m.calculateX();
					LCD.drawString("Got X:" + this.x + "    ", 0, 5);
				}
				catch(EnvironmentException ex) {
					LCD.drawString("Got Exception: " + ex.getMessage(), 0, 6);
				}
			}
			if(m.canCalculateY()) {
				try {
					this.y = m.calculateY();
					LCD.drawString("Got Y:" + this.y + "    ", 0, 4);
				}
				catch(EnvironmentException ex) {
					LCD.drawString("Got Exception: " + ex.getMessage(), 0, 6);
				}
			}
		}
	}
	
	public boolean isPositionKnown(){
		return x != -1 && y != -1;
	}
	
	public boolean isXKnown(){
		return x != -1;
	}
	
	public boolean isYKnown(){
		return y != -1;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRotation() {
		return rotation;
	}
	
	public void gotMeasure(Measurement m){
		this.addMesasurement(m);
	}
	
	public static double getNorm (double x1, double x2) {
		return Math.sqrt (x1 * x1 + x2 * x2);
	}
	
	public static double getAngle (double x1, double y1) {
		if (x1 == 0 && y1 >= 0) {
			return 0;
		} else if (x1 == 0 && y1 < 0) {
			return 180;
		} else if (y1 == 0 && x1 > 0) {
			return 90;
		} else if (y1 == 0 && x1 < 0) {
			return -90;
		} else {
			double arctg = Math.atan(y1/x1);
			if (x1 < 0 && y1 < 0) {
				arctg -= Math.PI;
			} else if (x1 < 0 && y1 > 0) {
				arctg += Math.PI;
			}
			return Math.toDegrees(arctg);
		}
	}
	
	public Direction getRelativeDirection ( double rx, double ry) {
		if (!isPositionKnown()) {
			return new Direction();
		}
		double mx = x - rx;
		double my = y - ry;
		return new Direction(getAngle(mx, my), getNorm(mx, my));
	}
	
	public Direction getGoalRelativeDirection () {
		return getRelativeDirection(Pitch.width/2, -Pitch.height/2);
	}
	
}
