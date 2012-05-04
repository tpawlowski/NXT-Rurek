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
		LCD.drawString("M, rot: " +  this.rotation+ "  ", 0, 0);
	}
	
	public double calculateX() throws EnvironmentException {
		Environment env = Environment.getEnvironment();
		if(hasDistance() && rotation > 45 && rotation <= 125){
			return Math.abs(Math.sin(Math.toRadians(rotation))) * distance;
		}
		else if(hasDistance() && rotation > 225 && rotation <= 315) {
			return env.getWidth() - Math.abs(Math.sin(Math.toRadians(rotation)) * distance);
		}
		return -1;
	}
	
	public double calculateY() throws EnvironmentException {
		Environment env = Environment.getEnvironment();
		if(hasDistance() && (rotation > 125 && rotation <= 225)){
					return Math.abs(Math.cos(Math.toRadians(rotation))) * distance;
		}
		else if(hasDistance() && (rotation > 315  || rotation <= 45)) {
			return env.getHeight() - Math.abs(Math.cos(Math.toRadians(rotation)) * distance);
		}
		return -1;
	}
	
	public boolean canCalculateY() {
		return hasDistance() && ((rotation > 125 && rotation <= 225) || (rotation >315  || rotation <= 45));
	}
	
	public boolean canCalculateX() {
		return hasDistance() && ((rotation > 45 && rotation <= 125) || (rotation > 225 && rotation <= 315));
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
		this.robot_rotation = robot_rotation;
	}

}