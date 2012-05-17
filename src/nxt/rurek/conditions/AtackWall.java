package nxt.rurek.conditions;

import nxt.rurek.Environment;
import nxt.rurek.position.Situation;

public class AtackWall extends Condition{

	@Override
	public boolean check(Situation s) {
		int x, y, a;
		int da = 8;
		
		x = (int) s.getPp().getPose().getX();
		y = (int) s.getPp().getPose().getY();
		a = (int) s.getPp().getPose().getHeading();
		
		if (x <= 10 && Math.abs(a-180) < da ) return true;
		if (x >= Environment.getEnvironment().getWidth() - 10 && Math.abs(a) < da ) return true;
		if (y >= Environment.getEnvironment().getHeight() - 10 && Math.abs(a-90) < da ) return true;
		if (y <= 10 && Math.abs(a-270) < da ) return true;
		return false;
	}

}
