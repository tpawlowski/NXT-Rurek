package nxt.rurek.position;

import lejos.geom.Point;
import lejos.nxt.LCD;
import nxt.rurek.Direction;
import nxt.rurek.Environment;
import lejos.robotics.navigation.Pose;
import nxt.rurek.exceptions.EnvironmentException;
import nxt.rurek.movement.PositionController;

public class Position implements MeasurementListener {

	private double x = -1;
	private double y = -1;
	private double rotation = -1;
	
	private PositionController controller;
	
	public Position(PositionController controller){
		this.controller = controller;
	}
	
	public void addMesasurement(Measurement m) {
		Pose current = controller.getNavigator().getPoseProvider().getPose();
		Point p = new Point(current.getX(), current.getY());
		if(m.getDistance() < 140) {
			if(m.canCalculateX()) {
				try {
					this.x = m.calculateX();
					if(Math.abs(this.x - current.getX()) < 20)
						p.x = (float) this.x;
				}
				catch(EnvironmentException ex) {
					LCD.drawString("Got Exception: " + ex.getMessage(), 0, 6);
				}
			}
			if(m.canCalculateY()) {
				try {
					this.y = m.calculateY();
					if(Math.abs(this.y - current.getY()) < 20)
						p.y = (float) this.y;
				}
				catch(EnvironmentException ex) {
					LCD.drawString("Got Exception: " + ex.getMessage(), 0, 6);
				}
			}
		}
		current.setLocation(p);
		current.setHeading((float) m.getRobotRotation());	
		LCD.drawString("X:" + (int) current.getX() + "Y:" + (int) current.getY() + "R:" + current.getHeading(), 0, 5);
		controller.getNavigator().getPoseProvider().setPose(current);
	}
	
	public PositionController getController() {
		return controller;
	}

	public void setController(PositionController controller) {
		this.controller = controller;
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
		return Math.toDegrees(Math.atan2(y1, x1));
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
		return getRelativeDirection(Environment.getEnvironment().getWidth()/2, -Environment.getEnvironment().getHeight()/2);
	}
}
