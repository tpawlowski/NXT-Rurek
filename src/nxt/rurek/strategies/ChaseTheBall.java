package nxt.rurek.strategies;

import lejos.nxt.LCD;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import nxt.rurek.Ball;
import nxt.rurek.Direction;
import nxt.rurek.movement.PositionController;
import nxt.rurek.position.BallListener;

public class ChaseTheBall extends Strategy{
	
	public ChaseTheBall() {
		super ();
	}
	
	double max (double a, double b) {
		return a > b ? a : b;
	}
	
	@Override
	public void playWith(PositionController move) {
		Navigator navigator = move.getNavigator();
		BallListener ball = move.getBallListener();
		PoseProvider pp = navigator.getPoseProvider();
		DifferentialPilot dp = move.getDifferentialPilot();
		Direction bd;
		int i = 0;
		while (true) {
			bd = ball.getLast();
			if(!bd.isInRange()) {
				dp.rotate(60);
				continue;
			} else{
				if (Math.abs(bd.getAngle()) > 30 && bd.getDistance() > 10 ) {
					dp.rotate(bd.getRotation());
				} else {
					dp.travel(max(bd.getDistance(), 50));
				}
			}
			i++;
			if (i == 20) break;
		}
	}
}
