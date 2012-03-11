package nxt.rurek;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;

public class HelloWorld {
	
	/*
	 * S1 - ultrasonic
	 * S2 - podczerwień
	 * S3 - kolor
	 * S4 - kompass 
	 */
	
	
    public static void main(String[] args) {
    	CompassHTSensor cp = new CompassHTSensor(SensorPort.S4);
		LCD.clear();
		LCD.drawString("Compas calibrated", 0, 0);
		LCD.drawString("co: "+Float.toString(cp.getDegreesCartesian()), 0, 1);
		Button.waitForAnyPress();
    	try {
        	UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
        
        	if (us.continuous() != 0) {
        		throw new InitializaitonException();
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
			
			
        } catch (InitializaitonException e) {
        	LCD.clear();
        	LCD.drawString("Exception occured", 0, 0);
        } 
    }
}
