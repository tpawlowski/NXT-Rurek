package nxt.rurek.tests.position;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import nxt.rurek.position.HeadController;

public class HeadMoveTest implements ButtonListener {
	
		private HeadController head;
		public static void main(String[] args) {
			HeadMoveTest test = new HeadMoveTest();
			Button.ESCAPE.waitForPress();
			test.endTest(); } 
		
		HeadMoveTest() {
			head = new HeadController();
			Button.LEFT.addButtonListener(this);
			Button.RIGHT.addButtonListener(this);
		}
		
		public void buttonPressed(Button b) {
			if(b.equals(Button.LEFT)) {
				head.rotateLeft();
			}
			else if(b.equals(Button.RIGHT)) {
				head.rotateRight();
			}
			LCD.drawString("Tacho: " + head.getTacho(), 0, 0);
			
			// max 2790
			// gdy zaczyna z prawej strony
		}
		
		public void buttonReleased(Button b) {
			
		}
		
		public void endTest() {
			head.resetHead();
		}
		
		
}
