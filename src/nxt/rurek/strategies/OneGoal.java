package nxt.rurek.strategies;

import lejos.nxt.LCD;
import nxt.rurek.Direction;
import nxt.rurek.Environment;
import nxt.rurek.conditions.DontSeeBall;
import nxt.rurek.conditions.HasBall;
import nxt.rurek.conditions.isCharging;
import nxt.rurek.geometry.Functions;
import nxt.rurek.geometry.Point;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.Situation;

public class OneGoal extends Strategy {
	
	private final int debug = 1;
	
	@Override
	public void playWith(PositionController move) {
		Situation s = new Situation(move.getBallListener(), move.getDifferentialPilot(), move.getNavigator().getPoseProvider());
		Point destination = new Point (Environment.getEnvironment().getWidth()/2, Environment.getEnvironment().getHeight() + 10);
		Direction bd; /* place to store ball direction */
		HasBall hasBallCond = new HasBall();
		DontSeeBall dontSeeBallCond = new DontSeeBall();	
		isCharging isChargingCond = new isCharging(destination);
		int try_cnt;
		
		while (dontSeeBallCond.check(s));
		
		while (true) {
			bd = s.getBl().getLast();
			try_cnt = 0;
			while (!bd.isInRange()) {
				if (try_cnt > 2) {
					goTo(Functions.getFountain(), s, dontSeeBallCond);
				}
				rotate(getRotateDir(s.getPp().getPose())*360, s, dontSeeBallCond);
				try_cnt++; 
				bd = s.getBl().getLast();
			}
			Point me = Functions.fromPose(s.getPp().getPose());
			Point b = bd.toPoint(s.getPp().getPose());
			
			//TODO po kolei rozpatrzyć przypadki.
			/*
			if (isChargingCond.check(s) && hasBallCond.check(s)) {
				if (debug > 0) LCD.drawString("   " + 1 +  "   ", 0, 3);
		    	double angle = getChargeAngle(pp.getPose());
		    	rotateTo(angle, pp.getPose(), dp);
		    	double d = Functions.getTarget().getDistance(Functions.fromPose(pp.getPose()));
		    	dp.travel(d*4/5);
			} else if (b.getY() < me.getY() && me.getY() > 30) { 
				if (b.getX() > 60) {
					LCD.drawString("   " + 2 +  "   ", 0, 3);
					//dp.rotate(15*getRotateDir(pp.getPose()));
					goTo(new Point(10, 10), 30, pp.getPose(), dp);
				} else {
					LCD.drawString("   " + 3 +  "   ", 0, 3);
					//dp.rotate(-15*getRotateDir(pp.getPose()));
					goTo (new Point (Environment.getEnvironment().getWidth() -10, 10), 30, pp.getPose(), dp );
				}
				
			}  else if (b.getY() >= me.getY() && hasBall(bd)) {
				LCD.drawString("   " + 4 +  "   ", 0, 3);
				rotateTo(getChargeAngle(pp.getPose()), pp.getPose(), dp);
			} else if (b.getY() >= me.getY()) {
				LCD.drawString("   " + 5 +  "   ", 0, 3);
				goTo(b, bd.getDistance(), pp.getPose(), dp);
			} else {
				LCD.drawString("   " + 6 +  "   ", 0, 3);
				goTo(Functions.getFountain(), 30, pp.getPose(), dp);
			}
			*/
		}
	}
}
