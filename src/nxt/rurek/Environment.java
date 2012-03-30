package nxt.rurek;

import nxt.rurek.exceptions.EnvironmentException;

public class Environment {
	private static Environment singleton;
	double width;
	double height;

	public static Environment getEnvironment() throws EnvironmentException {
		if(singleton == null) {
			throw new EnvironmentException("Null environment");
		}
		return singleton;
	}
	
	private Environment(){
		
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
}
