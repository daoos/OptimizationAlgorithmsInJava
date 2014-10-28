package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class RandomUtil {

	
	public static double uniformRandomize(double d, double e) {
		Random rand = new Random();
		return rand.nextDouble()*(e-d)+d;
	}

	public static double[] randomizeDoubleArray(int length, double[] min,double[] max) {
		double[] d = new double[length];
		Random rand = new Random();
		for(int i=0;i<length;i++) {
			d[i] = rand.nextDouble()*(max[i]-min[i])+min[i];
		}
		return d;
	}

	public static double normalRandomize(double d, double e) {
		Random rand = new Random();
		return rand.nextGaussian()*(e-d)+d;
	}

	public static boolean[] randomizeByteArray(int length) {
		Random rand = new Random();
		boolean[] d = new boolean[length];
		for(int i=0;i<length;i++) {
			d[i] = rand.nextBoolean();
		}
		return d;
	}
	
	

}
