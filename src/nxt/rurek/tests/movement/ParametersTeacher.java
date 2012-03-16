package nxt.rurek.tests.movement;

import java.util.Arrays;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class ParametersTeacher {

	public static void main(String[] args) {
		//Dom Pawlowskiego: DifferentialPilot pilot = new DifferentialPilot(8.95, 14.8, Motor.B, Motor.A);
		//uczelnia :
		//DifferentialPilot pilot = new DifferentialPilot(7.8, 14.6, Motor.B, Motor.A);
		//DifferentialPilot pilot = new DifferentialPilot(8,  14.6 , Motor.B, Motor.A);
		CompassHTSensor compass = new CompassHTSensor(SensorPort.S4);
		compass.resetCartesianZero();
		/*while(!Button.ESCAPE.isDown()) {
			pilot.rotate(180);
			Button.ENTER.waitForPress();
			
		}*/
		
		Random gen = new Random();
		double [][] params  = new double [10][2];
		double [][] score = new double [10][2];
		double [] cost = new double [10];
		double [] sorted_cost;
		
		params[0][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[0][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[1][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[1][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[2][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[2][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[3][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[3][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[4][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[4][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[5][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[5][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[6][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[6][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[7][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[7][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[8][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[8][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;
		params[9][0] = 5.8 + ((gen.nextDouble() - 0.5) * 4); params[9][1] = 14.6 + (gen.nextDouble() - 0.5) * 6;

		try {
			while(!Button.ESCAPE.isDown()) {
				for(int i = 0; i < 10; ++i) {
					DifferentialPilot pilot = new DifferentialPilot(params[i][0], params[i][1], Motor.B, Motor.A);
					double begin, end;
					begin = compass.getDegrees();
					pilot.rotate(90);
					pilot.travel(10);
					end = compass.getDegrees();
					score[i][0] = (normalize(end - begin) - 90);
					LCD.drawString("score1: " + score[i][0] + "    ", 0, 3);
					
					Thread.sleep(1000);
					begin = compass.getDegrees();
					pilot.rotate(180);
					pilot.travel(10);
					Thread.sleep(1000);
					end = compass.getDegrees();
					score[i][1] = (normalize(end - begin) - 180) /2;
					LCD.drawString("score2: " + score[i][1] + "    ", 0, 4);
				}
				
				for(int i = 0; i < 10; ++i) {
					cost[i] = score[i][0]*score[i][0]  + score[i][1]*score[i][1];
					LCD.drawString("cost: " + cost[i] + "    ", 0, 2);
				}
				
				sorted_cost = cost.clone();
				Arrays.sort(sorted_cost);
				
				double median = sorted_cost[5];
				double min = sorted_cost[0];
				for(int i = 0; i < 10; ++i){
					if(cost[i] == min) {
						LCD.drawString("min:" + params[i][0] + "    ", 0, 5);
						LCD.drawString("min:" + params[i][1] + "    ", 0, 6);
						LCD.drawString("min_cost:" + cost[i] + "  ", 0, 7);
					}
				}
				LCD.drawString("median: " + median, 0, 1);
				for(int i = 0, j = 0; i < 10; ++i) {
					if(cost[i] <= median) { 
						score[j][0] = score[i][0];
						score[j][1] = score[i][1];
						params[j][0] = params[i][0];
						params[j][1] = params[i][1];
						++j;
					}
				}
				
				for(int i = 0; i < 15; ++i) {
					double tmp1, tmp2;
					int r1 = gen.nextInt(5);
					int r2 = gen.nextInt(5);
					if(r1 != r2) {
						tmp1 = params[r1][0];
						tmp2 = params[r1][1];
						params[r1][0] = params[r2][0];
						params[r1][1] = params[r2][1];
						params[r2][0] = tmp1;
						params[r2][1] = tmp2;
					}
				}
				
				params[5][0] = (params[0][0] + params[2][0]) / 2;
				params[5][1] = (params[0][1] + params[2][1]) / 2;
				params[6][0] = (params[1][0] + params[4][0]) / 2;
				params[6][1] = (params[1][1] + params[4][1]) / 2;
				params[7][0] = (params[2][0] + params[0][0]) / 2;
				params[7][1] = (params[2][1] + params[0][1]) / 2;
				params[8][0] = (params[3][0] + params[1][0]) / 2;
				params[8][1] = (params[3][1] + params[1][1]) / 2;
				params[9][0] = (params[4][0] + params[3][0]) / 2;
				params[9][1] = (params[4][1] + params[3][1]) / 2;
				
				Button.ENTER.waitForPress();
			}
		}
		catch(InterruptedException ex) {
			
		}
	}
	
	private static double normalize(double angle) {
		return angle > 360 ? angle - 360 : (angle < 0 ? angle + 360 : angle);
	}
}
