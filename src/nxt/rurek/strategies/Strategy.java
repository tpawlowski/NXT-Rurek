package nxt.rurek.strategies;

import lejos.nxt.addon.IRSeekerV2;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;
import nxt.rurek.Direction;
import nxt.rurek.Environment;
import nxt.rurek.conditions.Condition;
import nxt.rurek.conditions.SemiHasBall;
import nxt.rurek.geometry.Functions;
import nxt.rurek.geometry.Point;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.BallListener;
import nxt.rurek.position.Situation;

public abstract class Strategy {

	private IRSeekerV2 irsensor;
	
	public Strategy () {
		super ();
		irsensor = Environment.getEnvironment().getIrsensor();
	}
	/* Rurek plays with this strategy until condition is set to true */
	public abstract void playWith(PositionController move);

	/* to point po as long as the condition is satisfied */
	public void goTo (Point po, Situation s, Condition c) {
		Point p = Functions.fromPose(s.getPp().getPose());
		rotateTo(p.getAngle(po), s);
		s.getDp().travel(p.getDistance(po), true);
		while(s.getDp().isMoving()) {
			if (!c.check(s)) {
				s.getDp().stop();
				break;
			}
		}
	}
	
	public int min (int a, int b) {
		return (a > b) ? b : a;
	}
	
	public int max (int a, int b) {
		return (a > b) ? a : b;
	}
	
	public void rotate (double angle, Situation s, Condition c) {
		s.getDp().rotate(angle, true);
		while (s.getDp().isMoving()) {
			if (!c.check(s)) {
				s.getDp().stop();
				break;
			}
		}
	}
	
	public void forceUpdateBall(Situation s) {
		s.getBl().gotMeasure(irsensor.getSensorValues());
	}
	
	/* rotates to (not by) given angle immedietly*/
	public void rotateTo (double a, Situation s) {
		s.getDp().rotate(getRotation(a)); 
	}
	
	/* rotates slowly to (not by) given angle */
	public void rotateWithBallTo (double a, Situation s) {
		s.getDp().setRotateSpeed(s.getDp().getRotateMaxSpeed()/3);
		s.getDp().rotate(a, true);
		SemiHasBall shb = new SemiHasBall();
		while (s.getDp().isMoving()) {
			if (!shb.check(s)) {
				s.getDp().stop();
			}
		}
		s.getDp().setRotateSpeed(s.getDp().getRotateMaxSpeed());
	}
	
	public int getRotateDir (Pose p) {
		if (p.getX() < Environment.getEnvironment().getWidth()/ 2) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public double getRotation(double angle) {
		return angle > 180 ? angle - 360 : angle; 
	}
	
	public double convertRotation(double toAngle, double myAngle) {
		return getRotation(Direction.normalize(toAngle-myAngle));
	}
	
	public double getChargeAngle (Pose position, Point p) {
		return Math.toDegrees(Math.atan2(p.getY()-position.getY(), p.getX()-position.getX()));
	}
	
	public boolean hasBall (Direction ballDirection) {
		return ballDirection.hasDistance() && ballDirection.getDistance() < 10;
	}
	
	public boolean isChargeAngle (Direction bd, Pose position, Point charge) {
		if (!bd.hasDistance()) return false; /* wrong angle if I don't see ball */
		double myAngle = (double)position.getHeading();
		return Math.abs(getChargeAngle(position, charge) - Direction.normalize(myAngle)) < 10;
	}
	
}
