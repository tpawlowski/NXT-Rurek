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
		Environment env = Environment.getEnvironment();
		if(hasDistance() && rotation > 45 && rotation <= 125){
			//if(position.isXKnown()){
				//if(Math.abs(Math.cos(rotation) * distance - position.getX()) < 10)
					return Math.abs(Math.sin(Math.toRadians(rotation))) * distance;
				//else 
				//	return position.getX();
			//}
			//else {
			//	return Math.cos(rotation) * distance;
			//}
		}
		else if(hasDistance() && rotation > 225 && rotation <= 315) {
			//if(position.isXKnown()){
				//if(Math.abs(env.getWidth() - Math.cos(rotation) * distance - position.getX()) < 10)
					return env.getWidth() - Math.abs(Math.sin(Math.toRadians(rotation)) * distance);
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
		Environment env = Environment.getEnvironment();
		if(hasDistance() && (rotation > 125 && rotation <= 225)){
			//if(position.isYKnown()){
			//	if(Math.abs(Math.cos(rotation) * distance - position.getX()) < 10)
					return Math.abs(Math.cos(Math.toRadians(rotation))) * distance;
			//	else 
			//		return position.getX();
			//}
			//else
			//	return Math.cos(rotation) * distance;
		}
		else if(hasDistance() && (rotation > 315  || rotation <= 45)) {
			//if(position.isYKnown()){
			//	if(Math.abs(env.getHeight() - Math.sin(rotation) * distance - position.getY()) < 10)
					return env.getHeight() - Math.abs(Math.cos(Math.toRadians(rotation)) * distance);
			//	else 
			//		return position.getY();
			//}
			//else
			//	return Math.sin(rotation) * distance;
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
		return distance < 90;
	}

}