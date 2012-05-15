package nxt.rurek.position;

import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;

public class Situation {

	BallListener bl;
	DifferentialPilot dp;
	PoseProvider pp;
	
	public Situation(BallListener bl, DifferentialPilot dp, PoseProvider pp) {
		super();
		this.bl = bl;
		this.dp = dp;
		this.pp = pp;
	}
	
	public BallListener getBl() {
		return bl;
	}
	public void setBl(BallListener bl) {
		this.bl = bl;
	}
	public DifferentialPilot getDp() {
		return dp;
	}
	public void setDp(DifferentialPilot dp) {
		this.dp = dp;
	}
	public PoseProvider getPp() {
		return pp;
	}
	public void setPp(PoseProvider pp) {
		this.pp = pp;
	}
	
}
