package nxt.rurek.tests.UltrasonicSensor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;

public class DistanceTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		for(int i = 20; i < 0; ++i) {
			Button.ESCAPE.waitForPressAndRelease();
			sensor.getDistance();
			LCD.drawString(""+sensor.getDistance(), 0, 0);
			RConsole.println(sensor.getDistance()+"");
			
		}
	}*/
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
        FileOutputStream out = null; // declare outside the try block
        while (!Button.ESCAPE.isDown()){
			LCD.drawString("dist" + sensor.getDistance()+"     ", 0, 0);
			Button.ENTER.waitForPress();
        }
        if(true)
        	return;
        
        File data = new File("distance.dat");

        try {
            out = new FileOutputStream(data);
        } catch (IOException e) {
            System.err.println("Failed to create output stream");
            while (!Button.ENTER.isDown());
            System.exit(1);
        }

        DataOutputStream dataOut = new DataOutputStream(out);

        StringBuffer sb= new StringBuffer();

		for(int i = 0; i < 20; ++i) {
			Button.ENTER.waitForPress();
			LCD.drawString("dist"+sensor.getDistance()+"     ", 0, 0);
			sb.append(sensor.getDistance() + "\n");
			RConsole.println(sensor.getDistance()+"");
			
		}
        try { // write
            dataOut.writeUTF(sb.toString());
            out.close(); // flush the buffer and write the file
        } catch (IOException e) {
            System.err.println("Failed to write to output stream");
            while (!Button.ENTER.isDown());
        }
	}

}
