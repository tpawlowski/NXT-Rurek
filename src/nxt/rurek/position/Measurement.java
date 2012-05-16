package nxt.rurek.position;

import lejos.nxt.LCD;
import nxt.rurek.Environment;
import nxt.rurek.exceptions.EnvironmentException;

public class Measurement {
	private double rotation;
	private double robot_rotation;
	private int distance;
	
	public Measurement(double rotation, int distance) {
		super();
		this.rotation = normalizeAngle(rotation);
		this.distance = distance;
		LCD.drawString("M, rot: " + this.rotation + "  ", 0, 0);
	}
	
	public double calculateX() throws EnvironmentException {
		Environment env = Environment.getEnvironment();
		if(hasDistance() && rotation > 55 && rotation <= 115){
			return Math.abs(Math.sin(Math.toRadians(rotation))) * distance;
		}
		else if(hasDistance() && rotation > 235 && rotation <= 305) {
			return env.getWidth() - Math.abs(Math.sin(Math.toRadians(rotation)) * distance);
		}
		return -1;
	}
	
	public double calculateY() throws EnvironmentException {
		Environment env = Environment.getEnvironment();
		if(hasDistance() && (rotation > 135 && rotation <= 215)){
					return Math.abs(Math.cos(Math.toRadians(rotation))) * distance;
		}
		else if(hasDistance() && (rotation > 325  || rotation <= 35)) {
			return env.getHeight() - Math.abs(Math.cos(Math.toRadians(rotation)) * distance);
		}
		return -1;
	}
	
	public boolean canCalculateY() {
		return hasDistance() && ((rotation > 135 && rotation <= 215) || (rotation >325  || rotation <= 35)); } 
	public boolean canCalculateX() {
		return hasDistance() && ((rotation > 55 && rotation <= 115) || (rotation > 235 && rotation <= 305));
	}
	
	public static double normalizeAngle(double angle) {
		while(angle < 0) {
			angle += 360;
		}
		while(angle >= 360) {
			angle -= 360;
		}
		return angle;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public boolean hasDistance() {
		return distance < 90;
	}
	
	public double getRobotRotation() {
		return robot_rotation;
	}

	public void setRobotRotation(double robot_rotation) {
		this.robot_rotation = normalizeAngle(robot_rotation);
	}

}