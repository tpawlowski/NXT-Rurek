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
		Point me = new Point (s.getPp().getPose());
		rotateTo(convertRotation(me.getAngle(Functions.getLeftCorner()), s.getPp().getPose().getHeading()), s);
		goTo(Functions.getLeftCorner(), s, new EmptyCondition());
		rotateTo(convertRotation(90, s.getPp().getPose().getHeading()), s);
	}
	
	private boolean aroundFromLeft(Situation s) {
		Direction bd = s.getBl().getAny();
		if (!bd.isInRange()) return false;
		return bd.toPoint(s.getPp().getPose()).getX() < Environment.getEnvironment().getWidth() / 2;
	}
	
	
	public boolean checkBack(Situation s) {
		Direction bd = s.getBl().getLast(); 
		if (!bd.isInRange()) return false;
		Point bp = bd.toPoint(s.getPp().getPose());
		return bp.getY() + 5 < s.getPp().getPose().getY(); 
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
		Point trg = Functions.getTarget();
		
		while (!Button.ENTER.isDown()) {
			
			/*
			LCD.clear(4);
			LCD.drawInt((int)bd.getAngle(), 0, 4);
			LCD.drawInt((int)bd.getDistance(), 4, 4);
			LCD.drawInt((int)b.getX(), 8, 4);
			LCD.drawInt((int)b.getY(), 12, 4);*/
			
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
			
			forceUpdateBall(s);
			bd = s.getBl().getLast();
			LCD.drawInt((int)bd.getAngle(), 0, 3);
			LCD.drawInt((int)bd.getDistance(), 4, 3);
			
			if (isChargingCond.check(s) && (bd.hasDistance() && bd.getDistance() < 5)) {
				LCD.drawInt(0, 15, 3);
				if (debug > 0) LCD.drawString("   " + 1 +  "   ", 0, 3);
		    	double angle = getChargeAngle(s.getPp().getPose(), trg);
		    	rotateWithBallTo(convertRotation(angle, s.getPp().getPose().getHeading()), s);
		    	me = Functions.fromPose(s.getPp().getPose());
				s.getDp().travel(me.getDistance(trg), true);
				while(s.getDp().isMoving()) {
					if (!semiHasBallCond.check(s)) {
						s.getDp().stop();
						break;
					}  else if (!isChargingCond.check(s)) {
						angle = getChargeAngle(s.getPp().getPose(), trg);
				    	rotateWithBallTo(convertRotation(angle, s.getPp().getPose().getHeading()), s);
					}  else if (isTooCloseCond.check(s)) {
						s.getDp().stop();
						defenceMode(s);
					}
				}
			} else if (isTooCloseCond.check(s)) {
				defenceMode(s);
			} else if (bd.hasDistance() && bd.getDistance() < 5) {
				/* obracam się w stronę bramki */
				LCD.drawInt(1, 14, 3);
				double angle = getChargeAngle(s.getPp().getPose(), trg);
		    	rotateWithBallTo(convertRotation(angle, s.getPp().getPose().getHeading()), s);
		    	break;
			} else if (checkBack(s)) {
				LCD.drawInt(1, 14, 3);
			    defenceMode(s);
				/*boolean fromLeft = aroundFromLeft(s);
				double wantedAngle;
				
				while (backForBallCond.check(s)) {
					bd = s.getBl().getLast();
					if (!bd.isInRange()) break;
					me = new Point(s.getPp().getPose());
					b = bd.toPoint(s.getPp().getPose());
					wantedAngle = me.getAngle(b) + (fromLeft ? -90 : 90);
					rotateTo(convertRotation(wantedAngle, s.getPp().getPose().getHeading()), s);
					double angle;
					boolean frwd; 
					if (fromLeft) {
						if (me.getAngle(b) >= 270) {
							angle = 360 - me.getAngle(b);
						}
						else angle = me.getAngle(b)- 90 ; 
					} else {
						if (me.getAngle(b) >= 270) angle = 180 + ( 90 - (360 - me.getAngle(b)));
						else angle = me.getAngle(b) - 90;
					}
					s.getDp().arc(25 * (fromLeft ? 1 : -1) , angle);
					rotateTo((fromLeft ? 90 : -90), s);
				}*/
			}  else /* if (needComuteCond.check(s)) */ {
				bd = s.getBl().getLast();
				
				LCD.drawInt(2, 14, 3);
				b = bd.toPoint(s.getPp().getPose());
				me = new Point(s.getPp().getPose()); 
				rotateTo(bd.getAngle(), s);
				
				s.getDp().travel(bd.getDistance()+7, true);
				while (s.getDp().isMoving()) {
					forceUpdateBall(s);
					bd = s.getBl().getLast();
					if (!bd.isInRange()) break;
					b = bd.toPoint(s.getPp().getPose());
					me = new Point(s.getPp().getPose());
					//int xxx = (int) Math.abs(me.getAngle(b) - s.getPp().getPose().getHeading());
					if (/*min(xxx, 360-xxx) > 30 || */(bd.hasDistance() && bd.getDistance() < 5)) {
						s.getDp().stop();
						break;
					}
				}
				LCD.drawInt(1, 6, 3);
			}
			
			
		}
		
		
		
		
	}
}
