package nxt.rurek;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import nxt.rurek.strategies.ChaseTheBall;
import nxt.rurek.strategies.OneGoal;

public class Strategy1 {
	public static void main(String[] args) {
    	Init i = new Init();
    	LCD.drawString("dupa", 0, 0);
    	try {
    		OneGoal strategy = new OneGoal();
    		strategy.playWith(i.controller);
    	} catch (Exception e) {
    		i.release();
    	} finally {
    		while(!Button.ENTER.isDown());
    		i.release();
    	}
    	
    }
}
