package nxt.rurek.position;

import lejos.nxt.LCD;

public class Position implements MeasurementListener {

	private double x = -1;
	private double y = -1;
	private double rotation = -1;
	
	public Position(){}
	
	public void addMesasurement(Measurement m) {
		if(m.getDistance() < 150) {
			if(m.canCalculateX()) {
				this.x = this.getX();
				LCD.drawString("Got X:" + this.x + "    ", 0, 5);
			}
			if(m.canCalculateY()) {
				this.y = this.getY();
				LCD.drawString("Got Y:" + this.y + "    ", 0, 4);
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
