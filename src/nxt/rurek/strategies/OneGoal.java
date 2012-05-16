package nxt.rurek.strategies;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import nxt.rurek.Direction;
import nxt.rurek.Environment;
import nxt.rurek.conditions.BackForBall;
import nxt.rurek.conditions.DontSeeBall;
import nxt.rurek.conditions.EmptyCondition;
import nxt.rurek.conditions.HasBall;
import nxt.rurek.conditions.NeedComute;
import nxt.rurek.conditions.SemiHasBall;
import nxt.rurek.conditions.isCharging;
import nxt.rurek.conditions.isTooClose;
import nxt.rurek.geometry.Functions;
import nxt.rurek.geometry.Point;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.Situation;


public class OneGoal extends Strategy {
	
	private final int debug = 1;
	
	
	/** Tries to defend goal without suicide */
	private void defenceMode (Situation s) {
		Point target = Functions.getFountain();
		isTooClose isTooCloseCond = new isTooClose(Functions.getLeftCorner());
		
		Point me = new Point (s.getPp().getPose());
		rotateTo(me.getAngle(target), s);
		me = new Point (s.getPp().getPose());
		
		while (!isTooCloseCond.check(s)) {
			s.getDp().travel(me.getDistance(target), true);
			while(s.getDp().isMoving()) {
				if (isTooCloseCond.check(s)) {
					break;
				}
			}
			goTo(Functions.getFountain(), s, new EmptyCondition());
			rotateTo(90, s);
		}
	}
	
	private boolean aroundFromLeft(Situation s) {
		Direction bd = s.getBl().getAny();
		if (!bd.isInRange()) return false;
		return bd.toPoint(s.getPp().getPose()).getX() < Environment.getEnvironment().getWidth() / 2;
	}
	
	
	
	@Override
	public void playWith(PositionController move) {
		Situation s = new Situation(move.getBallListener(), move.getDifferentialPilot(), move.getNavigator().getPoseProvider());
		Point destination = new Point (Environment.getEnvironment().getWidth()/2, Environment.getEnvironment().getHeight() + 10);
		Direction bd; /* place to store ball direction */
		HasBall hasBallCond = new HasBall();
		SemiHasBall semiHasBallCond = new SemiHasBall();
		DontSeeBall dontSeeBallCond = new DontSeeBall();	
		BackForBall backForBallCond = new BackForBall();
		NeedComute needComuteCond = new NeedComute();
		isTooClose isTooCloseCond = new isTooClose(Functions.getTarget());
		isCharging isChargingCond = new isCharging(destination);
		int try_cnt;
		
		while (dontSeeBallCond.check(s));
		
		Point b, me;
		bd = s.getBl().getLast();
		
		LCD.drawInt(2, 14, 3);
		b = bd.toPoint(s.getPp().getPose());
		me = new Point(s.getPp().getPose()); 
		rotateTo(bd.getAngle(), s);
		
		s.getDp().travel(bd.getDistance(), true);
		while (s.getDp().isMoving()) {
			forceUpdateBall(s);
			bd = s.getBl().getLast();
			if (!bd.isInRange()) break;
			b = bd.toPoint(s.getPp().getPose());
			me = new Point(s.getPp().getPose());
			int xxx = (int) Math.abs(me.getAngle(b) - s.getPp().getPose().getHeading());
			if (min(xxx, 360-xxx) > 30 || hasBallCond.check(s)) {
				s.getDp().stop();
				break;
			}
		}
		LCD.drawInt(1, 6, 3);
		
	}
}
