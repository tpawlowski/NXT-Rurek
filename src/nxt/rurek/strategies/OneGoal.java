package nxt.rurek.strategies;

import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import nxt.rurek.Direction;
import nxt.rurek.Environment;
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
		return ballDirection.hasDistance() && ballDirection.getDistance() < 10 ;
	}
	
	public boolean charge_angle (Direction d, Pose position) {
		if (!d.hasDistance()) return false;
		double myAngle = (double) position.getHeading();
		return Math.abs(getChargeAngle(position) - myAngle) < 10;
	}
	
	public int getStan (Direction ballDirection, Pose position) {
		if (charge_angle(ballDirection, position) && hasBall(ballDirection)) {
			return RECKLESS_CHARGE;
		} else if (Math.abs(90 - ballDirection.getImperativeAngle(position.getHeading())) > 45) { 
			/* piłka jest nie w tym kierunku co chcę jechać */
			return THIS_IS_NOT_BALL;
		} else if () { /* piłka jest w prawie dobrym kierunku i jest blisko */
			
		} else if () { /* piłka jest w dobrym kierunku */
			
		} 
	}
	
	@Override
	public void playWith(PositionController move) {
		Navigator navigator = move.getNavigator();
		BallListener ball = move.getBallListener();
		Direction ballDirection = null;
		while (true) {
			boolean foundInRange = false;
			while (!foundInRange) {
				ballDirection = ball.getLast();
				foundInRange = ballDirection.isInRange();
				if (!foundInRange) {
					/* szukaj */
					double angle = (double) navigator.getPoseProvider().getPose().getHeading();
					navigator.rotateTo(Direction.normalize(angle+90));
				}
			}
			
			switch (getStan(ballDirection, navigator.getPoseProvider().getPose())) {
			    case(RECKLESS_CHARGE): 
			    	while(charge_angle(ballDirection, navigator.getPoseProvider().getPose()) && hasBall(ballDirection)) {
			    		/* szarża */
			    			/* trochę popraw kąt */
			    		double angle = getChargeAngle(navigator.getPoseProvider().getPose());
			    		navigator.rotateTo(angle);
			    			/* przejedź trochę */
			    		double x = navigator.getPoseProvider().getPose().getX() + 15*Math.cos(angle);
			    		double y = navigator.getPoseProvider().getPose().getY() + 15*Math.sin(angle);
			    		navigator.addWaypoint((float)x, (float)y);
			    		navigator.followPath();
			    		navigator.waitForStop();
			    		ballDirection = ball.getLast();
			    	}
			    	break;
			    case(THIS_IS_NOT_BALL):
			    	/* cofam się trochę w stronę bramki */
			    	double x = navigator.getPoseProvider().getPose().getX() + 15*Math.cos(angle);
	    			double y = navigator.getPoseProvider().getPose().getY() + 15*Math.sin(angle);
	    			navigator.addWaypoint((float)x, (float)y);
	    			navigator.followPath();
	    			navigator.waitForStop();
			    	
			}
			
			ballDirection = ball.getLast();
			if (!ballDirection.hasDistance()){
				ballDirection.setDistance(15);
			}
			move.goToPosition(ballDirection);
			ballDirection = ball.getLast();
			if (ballDirection.hasDistance() && ballDirection.getDistance() < 10) {
				break;
			}
		}
	}
}
