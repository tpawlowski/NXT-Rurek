package nxt.rurek;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class BallCatcher {
	UltrasonicSensor usonic;
	
	
	public BallCatcher () {
		usonic = new UltrasonicSensor(SensorPort.S1);
    	if (usonic.continuous() != 0) {
    		/* signalize fail */
    	}
	}
	
	public boolean hasBall() {
		return usonic.getDistance() < 10;
	}
	
	
}
