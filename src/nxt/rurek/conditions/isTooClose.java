package nxt.rurek.conditions;

import nxt.rurek.geometry.Functions;
import nxt.rurek.geometry.Point;
import nxt.rurek.position.Situation;

public class isTooClose extends Condition  {

	Point target;
	
	public isTooClose (Point target) {
		this.target = target;
	}
	
	@Override
	public boolean check(Situation s) {
		return Functions.fromPose(s.getPp().getPose()).getDistance(target) < 30;
	}

}
