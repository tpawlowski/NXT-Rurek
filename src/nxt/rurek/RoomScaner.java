package nxt.rurek;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class RoomScaner {

	public static void main(String[] args) {
		DifferentialPilot pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);

		while(!Button.ESCAPE.isDown()) {
			pilot.travel(60);
			pilot.rotate(90);
		}
	}
}
