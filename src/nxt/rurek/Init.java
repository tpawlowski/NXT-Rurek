package nxt.rurek;

import lejos.robotics.navigation.Pose;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.HeadController;
import nxt.rurek.position.Position;

public class Init {
	public PositionController controller;
	public HeadController head;
	
	public Init() {
			head = new HeadController();
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
	
	public void release() {
		head.resetHead();
	}
}
