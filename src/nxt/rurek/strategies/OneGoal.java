package nxt.rurek.strategies;

import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import nxt.rurek.Direction;
import nxt.rurek.Environment;
import nxt.rurek.geometry.Functions;
import nxt.rurek.geometry.Point;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.BallListener;

public class OneGoal extends Strategy{
	private static final int RECKLESS_CHARGE    = 0;
	private static final int PROTECT_THE_GATE   = 1;
	private static final int YOU_SHALL_NOT_PASS = 2;
	private static final int HOLD_HOLD_HOLD     = 3;
	private static final int THIS_IS_NOT_BALL   = 4;
	
	public double getChargeAngle (Pose position) {
		double fx, fy;
		fx = Environment.getEnvironment().getWidth()/2;
		fy = Environment.getEnvironment().getHeight();
		return Math.toDegrees(Math.atan2(fy-position.getY(), fx-position.getX()));
	}
	
	public boolean hasBall (Direction ballDirection) {
		return ballDirection.hasDistance() && ballDirection.getDistance() < 10;
	}
	
	public boolean charge_angle (Direction d, Pose position) {
		if (!d.hasDistance()) return false;
		double myAngle = (double) position.getHeading();
		return Math.abs(getChargeAngle(position) - myAngle) < 10;
	}
	
	public int getStan (Direction ballDirection, Pose position) {
		Point me = Functions.fromPose(position);
		Point ball = ballDirection.toPoint(position);
		if (charge_angle(ballDirection, position) && hasBall(ballDirection)) {
			return RECKLESS_CHARGE;
		} else if (ball.getY() < me.getY() && me.getY() > 40) { 
			return THIS_IS_NOT_BALL;
		} else if (ball.getY() < me.getY() && me.getY() <= 40) { 
			return YOU_SHALL_NOT_PASS;
		} else {
			return 7;
		}
	}
	
	
	public void goTo (double x, double y, double d,  Pose p, DifferentialPilot dp) {
		
	}
	
	
	public int getRotateDir (Pose p) {
		if (p.getX() <= 40) {
			return 1;
		} else return -1;
	}
	
	public double getRotation(double angle) {
		return angle > 180 ? 360 - angle : angle; 
	}
	
	@Override
	public void playWith(PositionController move) {
		DifferentialPilot dp = move.getDifferentialPilot();
		Navigator navigator = move.getNavigator();
		BallListener ball = move.getBallListener();
		PoseProvider pp = move.getNavigator().getPoseProvider();
		Direction bd;
		while (true) {
			bd = ball.getLast();
			boolean first = true;
			while (!bd.isInRange()) {
				
				if (first) {
					dp.rotate(5*getRotateDir(pp.getPose()));
					first = false;
				} else {
					dp.rotate(60*getRotateDir(pp.getPose()));
				}
				bd = ball.getLast();
			}
			
			
			
			Point me = Functions.fromPose(pp.getPose());
			Point b = bd.toPoint(pp.getPose());
			if (charge_angle(bd, pp.getPose()) && hasBall(bd)) {
				while(charge_angle(bd, pp.getPose()) && hasBall(bd)) {
		    		/* szarża */
		    			/* trochę popraw kąt */
		    		double angle = getChargeAngle(pp.getPose());
		    		dp.rotate(getRotation(angle));
		    		double d = Functions.getTarget().getDistance(Functions.fromPose(pp.getPose()));
		    		if (d < 40) {
		    			break;
		    		} else {
		    			dp.travel(d-30);
		    		}
		    	}
			} else if (b.getY() < me.getY() && me.getY() > 30) { 
				if (b.getX() > 60) {
					goTo(10, 10, 30, pp.getPose(), dp);
				} else {
					goTo (Environment.getEnvironment().getWidth() -10, 10, 30, pp.getPose(), dp );
				}
			} else if (b.getY() < me.getY() && me.getY() <= 30) { 
				
			} else {
				
			}
			switch (getStan(bd, pp.getPose())) {
			    case(RECKLESS_CHARGE): 
			    	
			    	break;
			    case(THIS_IS_NOT_BALL):
			    	
			    	break;
			}
			
		}
	}
}
