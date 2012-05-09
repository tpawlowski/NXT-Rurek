package nxt.rurek.position;

import nxt.rurek.Environment;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Pose;

public class PitchPoseProvider implements PoseProvider {
	
	private PoseProvider poseProvider;
	private CompassHTSensor compass;
	
	public PitchPoseProvider(PoseProvider pose) {
		this.poseProvider = pose;
	}

	@Override
	public Pose getPose() {
		normalize();
		
		Pose aPose = poseProvider.getPose();
		float heading = aPose.getHeading();
		
		float compass_heading = compass.getDegreesCartesian();
		aPose.setHeading((heading + compass_heading) / 2);
		
		return aPose;
	}

	@Override
	public void setPose(Pose aPose) {
		float heading = aPose.getHeading();
		
		float compass_heading = compass.getDegreesCartesian();
		aPose.setHeading((heading + compass_heading) / 2);
		
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
	
	int max (int a, int b) {
		return a > b ? a : b;
	}
	
	int min (int a, int b) {
		return a < b ? a : b;
	}
	
	private int normalizeCoord(int coord, int max) {
		return min(max(coord, 0), max);
	}

}
