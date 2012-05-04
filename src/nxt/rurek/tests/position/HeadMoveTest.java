package nxt.rurek.tests.position;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import nxt.rurek.Environment;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.HeadController;
import nxt.rurek.position.Position;

public class HeadMoveTest implements ButtonListener {
	
		public PositionController controller;
		private HeadController head;
		public static void main(String[] args) {
			HeadMoveTest test = new HeadMoveTest();
			
			test.controller.getDifferentialPilot().setRotateSpeed(20);
			test.controller.getNavigator().goTo(80, 60);
			test.controller.getNavigator().goTo(90, 140);
			test.controller.getNavigator().goTo(10, 140);
			test.controller.getNavigator().goTo(60, 20);
			
			while (!Button.ESCAPE.isDown()) {
				LCD.drawString("x: " + test.controller.getNavigator().getPoseProvider().getPose().getX() + "      ", 0, 2);
				LCD.drawString("y: " + test.controller.getNavigator().getPoseProvider().getPose().getY() + "      ", 0, 3);
				try {
					Thread.sleep(500);
				}
				catch(Exception ex){
					
				}
			}
			
			Button.ESCAPE.waitForPress();
			test.endTest();
		} 
		
		HeadMoveTest() {
			head = new HeadController();
			Button.LEFT.addButtonListener(this);
			Button.RIGHT.addButtonListener(this);
			controller = new PositionController();
			Environment env = Environment.getEnvironment();
			Pose p = new Pose((int) env.getWidth() / 2, 10 , 90);
			controller.getNavigator().getPoseProvider().setPose(p);
			Environment.getEnvironment().getCompass().resetCartesianZero();
			Environment.getEnvironment().getHeadMotor().setSpeed(
					Environment.getEnvironment().getHeadMotor().getMaxSpeed());
			head.addMeasurementListener(new Position(controller));
			head.start();
			
		}
		
		public void buttonPressed(Button b) {
			if(b.equals(Button.LEFT)) {
				head.rotateLeft();
			}
			else if(b.equals(Button.RIGHT)) {
				head.rotateRight();
			}
			LCD.drawString("Tacho: " + head.getTacho(), 0, 0);
			
			// max 2790
			// gdy zaczyna z prawej strony
		}
		
		public void buttonReleased(Button b) {
			
		}
		
		public void endTest() {
			head.resetHead();
		}
		
}
