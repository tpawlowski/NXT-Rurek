package nxt.rurek.tests.movement;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import nxt.rurek.movement.PositionController;

public class MoveTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*DifferentialPilot pilot = new DifferentialPilot(8.1f, 14.8f, Motor.B, Motor.A);
		CompassHTSensor compass = new CompassHTSensor(SensorPort.S4);
		compass.startCalibration();
		pilot.setTravelSpeed(3);
		pilot.steer(-200,720);
		compass.stopCalibration();*/

		
		PositionController move = new PositionController();
		Pose begin = move.getNavigator().getPoseProvider().getPose();
		LCD.drawString("starting x: " + begin.getX(), 0, 1);
		LCD.drawString("starting y: " + begin.getY(), 0, 2);
		//move.getNavigator().addWaypoint(new Waypoint(0, 30));
		
		move.getDifferentialPilot().setRotateSpeed(20);
		Waypoint p1 = new Waypoint(20,30);
		Waypoint p2 = new Waypoint(0,0);
		p2.setMaxPositionError(4);
		Point p3 = new Point(0, 0);
		move.getNavigator().goTo(0, 20);
		move.getNavigator().goTo(30, 40);
		move.getNavigator().goTo(0, 0);
		//move.getNavigator().goTo(150, 0 );
		//move.getNavigator().goTo(150, -150);
		//move.getNavigator().addWaypoint(p2);
		//move.getNavigator().goTo(400, 0 );
		//move.getNavigator().goTo(400, -240 );
		//move.getNavigator().goTo(-150, -240 );
		//move.getNavigator().goTo(-150, 0 );
		//move.getNavigator().goTo(0, 0 );
		
		
		while (!Button.ESCAPE.isDown()) {
			LCD.drawString("x: " + move.getNavigator().getPoseProvider().getPose().getX() + "      ", 0, 2);
			LCD.drawString("y: " + move.getNavigator().getPoseProvider().getPose().getY() + "      ", 0, 3);
			try {
				Thread.sleep(500);
			}
			catch(Exception ex){
				
			}
		}
	}

}
