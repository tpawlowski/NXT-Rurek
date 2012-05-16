package nxt.rurek.conditions;

import nxt.rurek.Direction;
import nxt.rurek.geometry.Functions;
import nxt.rurek.geometry.Point;
import nxt.rurek.position.Situation;

public class isCharging extends Condition{

	Point destination;
	
	public isCharging(Point destination) {
		super();
		this.destination = destination;
	}
	
	@Override
	public boolean check(Situation s) {
		double myAngle = (double)s.getPp().getPose().getHeading();
		Point myPoint = Functions.fromPose(s.getPp().getPose());
		return Math.abs(myPoint.getAngle(destination) - Direction.normalize(myAngle)) < 15;
	}

}
