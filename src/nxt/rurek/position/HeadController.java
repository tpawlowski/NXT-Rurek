package nxt.rurek.position;

import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import nxt.rurek.Environment;

public class HeadController implements RegulatedMotorListener  {
	private NXTRegulatedMotor head;
	private CompassHTSensor compass;
	private MeasurementListener listener;
	private UltrasonicSensor distance;
	private MoveDirection currentDirection = MoveDirection.Stoped;
	
	private enum MoveDirection {Left, Right, Stoped};
	
	private static int max_tacho = 2780;
	private static int move_step = max_tacho / 7;
	
	public HeadController () {
		head = Environment.getEnvironment().getHeadMotor();
		compass = Environment.getEnvironment().getCompass();
		distance = Environment.getEnvironment().getUltrasonicSensor();
		head.addListener(this);
	}
	
	public void start() {
		currentDirection = MoveDirection.Left;
		this.rotationStopped(null, 0, false, 0);
		head.rotate(move_step, true);
	}
	
	public void rotationStarted(RegulatedMotor motor, int tachoCount, boolean stalled, long timeStamp) {}
	
	public void rotationStopped(RegulatedMotor motor, int tachoCount, boolean stalled, long timeStamp) {
		if(currentDirection == MoveDirection.Stoped) return;
		
		if(listener != null) {
			LCD.drawString("dist: " + distance.getDistance() + "  ", 0, 6);
			LCD.drawString("direction: " +  compass.getDegrees() + "  ", 0, 7);
					
			double head_angle = compass.getDegreesCartesian() + (((double) head.getTachoCount() / max_tacho) * 180) - 90;
			Measurement current = new Measurement(head_angle,  distance.getDistance());
			listener.gotMeasure(current);
		}
		
		if(currentDirection == MoveDirection.Left) {
			if(head.getTachoCount() + move_step > max_tacho + 10) {
				currentDirection = MoveDirection.Right;
				head.rotate(-move_step, true);
			}
			else {
				head.rotate(move_step, true);
			}
		}
		else {
			if(head.getTachoCount() - move_step < -10) {
				currentDirection = MoveDirection.Left;
				head.rotate(move_step, true);
			}
			else {
				head.rotate(-move_step, true);
			}
		}
		
		LCD.drawString("Tacho: " + head.getTachoCount(), 0, 1);
	}
	
	public int getTacho() {
		return head.getTachoCount();
	}
	
	public void rotateLeft() {
		head.rotate(90);
	}
	
	public void rotateRight() {
		head.rotate(-90);
	}
	
	public void resetHead() {
		currentDirection = MoveDirection.Stoped;
		head.rotateTo(0);
	}
	
	public void addMeasurementListener (MeasurementListener listener) {
		this.listener = listener;
	}
}
