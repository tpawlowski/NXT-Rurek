package nxt.rurek.position;

import nxt.rurek.Environment;
import nxt.rurek.exceptions.EnvironmentException;

public class Measurement {
	private Position position;
	private double rotation;
	private int distance;
	
	public Measurement(Position position, double rotation, int distance) {
		super();
		this.position = position;
		this.rotation = normalizeAngle(rotation);
		this.distance = distance;
	}
	
	public double calculateX() throws EnvironmentException {
		double rotationD = normalizeAngle(Math.toDegrees(rotation));
		Environment env = Environment.getEnvironment();
		if(hasDistance() && rotationD > 45 && rotationD < 125){
			//if(position.isXKnown()){
				//if(Math.abs(Math.cos(rotation) * distance - position.getX()) < 10)
					return Math.abs(Math.sin(rotation)) * distance;
				//else 
				//	return position.getX();
			//}
			//else {
			//	return Math.cos(rotation) * distance;
			//}
		}
		else if(hasDistance() && rotationD > 225 && rotationD < 315) {
			//if(position.isXKnown()){
				//if(Math.abs(env.getWidth() - Math.cos(rotation) * distance - position.getX()) < 10)
					return env.getWidth() - Math.abs(Math.sin(rotation) * distance);
				//else 
				//	return position.getX();
			//}
			//else {
			//	return Math.cos(rotation) * distance;
			//}
		}
		return -1;
	}
	
	public double calculateY() throws EnvironmentException {
		double rotationD = normalizeAngle(Math.toDegrees(rotation));
		Environment env = Environment.getEnvironment();
		if(hasDistance() && (rotationD > 125 && rotationD <= 225)){
			//if(position.isYKnown()){
			//	if(Math.abs(Math.cos(rotation) * distance - position.getX()) < 10)
					return Math.abs(Math.cos(rotation)) * distance;
			//	else 
			//		return position.getX();
			//}
			//else
			//	return Math.cos(rotation) * distance;
		}
		else if(hasDistance() && (rotationD >=315  || rotationD < 45)) {
			//if(position.isYKnown()){
			//	if(Math.abs(env.getHeight() - Math.sin(rotation) * distance - position.getY()) < 10)
					return env.getHeight() - Math.abs(Math.cos(rotation) * distance);
			//	else 
			//		return position.getY();
			//}
			//else
			//	return Math.sin(rotation) * distance;
		}
		return -1;
	}
	
	public boolean canCalculateY() {
		double rotationD = normalizeAngle(Math.toDegrees(rotation));
		return hasDistance() && ((rotationD > 125 && rotationD <= 225) || (rotationD >315  || rotationD <= 45));
	}
	
	public boolean canCalculateX() {
		double rotationD = normalizeAngle(Math.toDegrees(rotation));
		return hasDistance() && ((rotationD > 45 && rotationD <= 125) || (rotationD > 225 && rotationD <= 315));
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

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
		
	public Position getPosition() {
		return position;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public boolean hasDistance() {
		return distance < 150;
	}

}