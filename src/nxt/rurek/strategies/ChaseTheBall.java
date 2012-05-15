package nxt.rurek.strategies;

import lejos.nxt.LCD;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import nxt.rurek.Ball;
import nxt.rurek.Direction;
import nxt.rurek.geometry.Functions;
import nxt.rurek.geometry.Point;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.BallListener;

public class ChaseTheBall extends Strategy{
	
	public ChaseTheBall() {
		super ();
	}
	
	double max (double a, double b) {
		return a > b ? a : b;
	}
	
	public void goTo (Point po, Pose p, DifferentialPilot dp) {
		po.subP(Functions.fromPose(p));
		rotateTo(Functions.fromPose(p).getAngle(po), p, dp);
		dp.travel(Functions.fromPose(p).getDistance(po) * 2 / 3);
	}
	
	
	public void rotateTo (double a, Pose p, DifferentialPilot dp) {
		dp.rotate(Direction.normalize(a-p.getHeading())); 
	}
	
	
	@Override
	public void playWith(PositionController move) {
		Navigator navigator = move.getNavigator();
		BallListener ball = move.getBallListener();
		PoseProvider pp = navigator.getPoseProvider();
		DifferentialPilot dp = move.getDifferentialPilot();
		Direction bd;
		Point me;
		int i = 0;
		while (true) {
			bd = ball.getLast();
			if(!bd.isInRange()) {
				dp.rotate(60);
				continue;
			} else{
				me = Functions.fromPose(pp.getPose());
				Point b = bd.toPoint(pp.getPose());
				if (me.getY() > b.getY()) {
					LCD.drawInt(1, 3, 3);
				  	goTo(new Point (10, 10), pp.getPose(), dp);
				} else {
				 if (Math.abs(bd.getAngle()) > 40 ) {
					 LCD.drawInt(2, 3, 3);
					dp.rotate(bd.getRotation()/2);
				 }  else {
					 LCD.drawInt(3, 3, 3);
					dp.travel(bd.getDistance()*2/3);
				 }
				}
			}
		}
	}
}
