import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Rand {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] resask = new int[20];
		int[] timestamp = new int[resask.length];
		timestamp[0] = 0;
		ExponentialDistribution exp = new ExponentialDistribution(40.0);
		for(int i = 1; i <timestamp.length; i++){
			timestamp[i] = (int)exp.sample()+ 1+ timestamp[i-1];
			System.out.println(timestamp[i]);
	}
	}
}
