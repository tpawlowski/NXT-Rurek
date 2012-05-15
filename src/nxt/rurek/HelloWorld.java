package nxt.rurek;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.addon.IRSeekerV2;
import nxt.rurek.strategies.ChaseTheBall;
import nxt.rurek.strategies.OneGoal;

public class HelloWorld {
	
	/*
	 * S1 - ultrasonic
	 * S2 - podczerwien
	 * S3 - kolor
	 * S4 - kompass 
	 */
	
	
	private static void testCompass() {
		CompassHTSensor cp = new CompassHTSensor(SensorPort.S4);
		LCD.clear();
		LCD.drawString("Compas calibrated", 0, 0);
		LCD.drawString("co: "+Float.toString(cp.getDegreesCartesian()), 0, 1);
		Button.waitForAnyPress();
	}
	
	private static void testUltrasonic () {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
        
    	if (us.continuous() != 0) {
    		LCD.drawString("lipa", 0, 4);
    	}
    	
		while (!Button.ESCAPE.isDown()) {
			while(!Button.ENTER.isDown());
			LCD.clear();
			LCD.drawString(us.getVendorID(), 0, 0);
			LCD.drawString(us.getProductID(), 0, 1);
			LCD.drawString(us.getVersion(), 0, 2);
			LCD.drawInt(us.getDistance(), 0, 3);
		}
		Button.waitForAnyPress();
	}
	
	private static void testIRSeeker() {
		IRSeekerV2 ir = new IRSeekerV2(SensorPort.S2, IRSeekerV2.Mode.AC);
		while (!Button.ESCAPE.isDown()) {
			while(!Button.ENTER.isDown());
			int[] val = ir.getSensorValues();
			LCD.clear();
			for (int i = 0; i < 5; ++i) {
				LCD.drawString(""+val[i], 0, i);
			}
		}
	}
	
	private static void testBall() {
		Ball b = new Ball();
		Direction d;
		IRSeekerV2 ir = new IRSeekerV2(SensorPort.S2, IRSeekerV2.Mode.AC);
		while (!Button.ESCAPE.isDown()) {
			while(!Button.ENTER.isDown());
			int[] val = ir.getSensorValues();
			d = b.getDirection(val);
			LCD.clear();
			for (int i = 0; i < 5; ++i) {
				LCD.drawString(""+val[i], 0, i);
			}
			LCD.drawString(""+d.isInRange()+" "+d.getAngle(), 0, 5);
		}
	}
	
    public static void main(String[] args) {
    	Init i = new Init();
    	LCD.drawString("dupa", 0, 0);
    	try {
    		OneGoal strategy = new OneGoal();
    		ChaseTheBall strategy2 = new ChaseTheBall();
    		strategy.playWith(i.controller);
    	} catch (Exception e) {
    		i.release();
    	} finally {
    		while(!Button.ENTER.isDown());
    		i.release();
    	}
    	
    }
}
