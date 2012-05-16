package nxt.rurek.conditions;

import nxt.rurek.Direction;
import nxt.rurek.geometry.Point;
import nxt.rurek.position.Situation;

public class NeedComute extends Condition {

	@Override
	public boolean check(Situation s) {
		Direction bd = s.getBl().getLast(); 
		if (!bd.isInRange()) return false;
		Point bp = bd.toPoint(s.getPp().getPose());
		return bp.getY() <= s.getPp().getPose().getY() + 10; 
	}
	
	

}
