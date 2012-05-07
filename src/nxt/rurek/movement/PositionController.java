package nxt.rurek.movement;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import nxt.rurek.Direction;
import nxt.rurek.Environment;
import nxt.rurek.position.BallListener;
import nxt.rurek.position.PitchPoseProvider;
import nxt.rurek.position.Position;

public class PositionController {
	private static int max_speed = 360;
	private static int max_round_speed = 150; 
	private DifferentialPilot pilot;
	private Navigator navigator;
	private Position position;
	private BallListener ballListener;
	
	public PositionController() {
		//pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		//Dom pawloskiego: pilot = new CompassPilot(new CompassHTSensor(SensorPort.S4), 8.95f, 14.8f, Motor.B, Motor.A);
		Environment env = Environment.getEnvironment();
		pilot = new DifferentialPilot(env.getRobotWheel(), env.getRobotwidth(), env.getLeftMotor(), env.getRightMotor());
		//pilot = new DifferentialPilot(8.1f, 14.8f, Motor.B, Motor.A);
		position = new Position(this);
		navigator = new Navigator(pilot);
		navigator.setPoseProvider(new PitchPoseProvider(navigator.getPoseProvider()));
		//pilot.setTravelSpeed(30);
	}
	
	public DifferentialPilot getDifferentialPilot() {
		return pilot;
	}
	
	public Navigator getNavigator() {
		return navigator;
	}

	public BallListener getBallListener() {
		return ballListener;
	}

	public void setBallListener(BallListener ballListener) {
		this.ballListener = ballListener;
	}

	public void goToPosition(Direction direction) {
		if(!direction.isInRange()){
			pilot.rotateLeft();
		}
		else {
			if(Math.abs(direction.getAngle()) < 30) {
				pilot.forward();
			}
			else {
				//pilot.arcForward(Math.signum(direction.getAngle()) * -1 * (125 - Math.abs(direction.getAngle())) / 2);
				pilot.steer(direction.getAngle() * -1);
				if(direction.getAngle() == 0);
					pilot.forward();
			}
		}
	}
}
