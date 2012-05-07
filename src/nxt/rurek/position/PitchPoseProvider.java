package nxt.rurek.position;

import nxt.rurek.Environment;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Pose;

public class PitchPoseProvider implements PoseProvider {
	
	private PoseProvider poseProvider;
	
	public PitchPoseProvider(PoseProvider pose) {
		this.poseProvider = pose;
	}

	@Override
	public Pose getPose() {
		normalize();
		return poseProvider.getPose();
	}

	@Override
	public void setPose(Pose aPose) {
		poseProvider.setPose(aPose);
		normalize();
	}
	
	private void normalize() {
		Environment env = Environment.getEnvironment();
		Pose p = poseProvider.getPose();
		p.setLocation(normalizeCoord((int) p.getX(), (int) env.getWidth()),
					  normalizeCoord((int) p.getY(), (int) env.getHeight()));
		poseProvider.setPose(p);
	}
	
	private int normalizeCoord(int coord, int max) {
		return Math.min(Math.max(coord, 0), max);
	}

}
