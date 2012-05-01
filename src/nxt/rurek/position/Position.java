package nxt.rurek.position;

import lejos.nxt.LCD;
import nxt.rurek.exceptions.EnvironmentException;

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
	
}
