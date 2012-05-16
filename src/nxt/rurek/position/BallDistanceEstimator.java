package nxt.rurek.position;

public class BallDistanceEstimator {

	/** Rstimate the distance from ball
	 * @argument val IR sensor values
	 * @return estimates distance 
	 */
	
	private static int sqr(int a) {return a*a;}
	
	private static int	 getDistance (int[] val, int[] b) {
		double ret = 0;
		if (val.length != 5 || b.length != 7) throw new NullPointerException(
				"try to count distance of two diferent size vectors\n");
		for (int i = 0; i < val.length; ++i ){
			ret += sqr(val[i]-b[i+2]);
		}
		return 	(int) Math.round(Math.sqrt(ret));
	}
	
	private static int getNorm (int[] b) {
		double ret=0;
		if (b.length == 7) {
			for (int i = 0; i < 5; ++i ){
				ret += sqr(b[i+2]);
			}
			return 	(int) Math.round(Math.sqrt(ret));
		} else {
			for (int i = 0; i < b.length; ++i ){
				ret += sqr(b[i]);
			}
			return 	(int) Math.round(Math.sqrt(ret));
		}
	}
	
	
	private static boolean inited = false;
	
	private static int[][] knownPoints;
	
	private static void initialize () {
		int[] data = {-40,	0,	0,	35,	0,	0,	0,
					  -40,	40,	0,	120,	0,	0,	0,
					  -40,	60,	0,	78,	80,	0,	0,
					  -40,	60,	0,	78,	0,	0,	0,
					  -20,	0,	180,	180,	0,	0,	0,
					  -20,	20,	0,	180,	0,	0,	0,
					  -20,	40,	0,	68,	86,	0,	0,
					  -20,	60,	0,	0,	96,	0,	0,
						0,	0,	0,	0,	232,	0,	0,
						0,	0,	0,	232,	0,	0,	0,
						15,	10,	0,	208,	230,	0,	0,
						0,	0,	0,	0,	0, 	232,	0,
						0,	0,	0,	0,	232, 	232,	0,
						0,	0,	0,	232, 	232,	0,	0,
						0,	0,	0,	180, 	180,	0,	0,
						0,	0,	0,	0, 	180,	180,	0,
						0,	0,	0,	0, 	0,	180,	0,
						0,	0,	0,	0, 	0,	200,	0,
						0,	20,	0,	0,	209,	0,	0,
						0,	40,	0,	0,	184,	0,	0,
						0,	60,	0,	0,	155,	0,	0,
						0,	70,	0,	0,	135,	0,	0,
						20,	0,	0,	0,	0,	100,	100,
						20,	20,	0,	0,	0,	230,	0,
						20,	40,	0,	0,	86,	68,	0,
						20,	60,	0,	0,	96,	0,	0,
						40,	0,	0,	0,	0,	35,	0,
						40,	20,	0,	0,	0,	123,	0,
						40,	40,	0,	0,	0,	180,	0,
						40,	60,	0,	0,	63,	78,	0,
						60,	0,	0,	0,	0,	32,	0,
						60,	20,	0,	0,	0,	87,	0,
						60,	40,	0,	0,	0,	150,	0,
						60,	60,	0,	0,	0,	71,	0};
		knownPoints = new int[data.length/7][7];	
		for (int i = 0; i < data.length / 7; ++i) {
			for (int j = 0; j < 7; ++j) {
				knownPoints[i][j] = data[i*7+j];
			}
		}
	}
	
	private static int[] getNeariestPoint (int[] val) {
		int bestNo = -1;
		double bestDist = 2000000000; //infty;
		double currDist;
		for (int i = 0; i < knownPoints.length; ++i) {
			currDist = getDistance(val,knownPoints[i]);
			if (currDist < bestDist) {
				bestNo = i;
				bestDist = currDist;
			}
		}
		return knownPoints[bestNo];
	}
	
	private static double neariestPointDistance(int[] val) {
		int[] np = getNeariestPoint(val);
		int[] a = new int[2];
		a[0] = np[0];
		a[1] = np[1];
		return getNorm(a);
	}
	
	public static double getEstimation(int[] val) {
		if (!inited) initialize();
		return neariestPointDistance(val);
	}
	
}
