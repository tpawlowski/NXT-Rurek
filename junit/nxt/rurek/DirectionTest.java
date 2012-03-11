package nxt.rurek;

import static org.junit.Assert.fail;

import org.junit.Test;

public class DirectionTest extends Direction {

	
	public void normalizeTest (){
		if (normalize(-180) != -180) fail ("1");
		if (normalize(-360) != 0) fail ("2");
		if (normalize(360) != 0) fail ("3");
		if (normalize(0) != 0) fail ("4");
		if (normalize(-720) != 0) fail ("5");
		if (normalize(180) != -180) fail ("6");
	}
	
	@Test
	public void test() {
		normalizeTest();
	}

}
