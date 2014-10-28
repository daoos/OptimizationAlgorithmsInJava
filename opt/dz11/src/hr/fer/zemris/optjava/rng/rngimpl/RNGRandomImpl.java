package hr.fer.zemris.optjava.rng.rngimpl;

import java.util.Random;

import hr.fer.zemris.optjava.rng.IRNG;

public class RNGRandomImpl implements IRNG {

	private Random rand=null;
	
	public RNGRandomImpl() {
		rand = new Random();
	}

	@Override
	public double nextDouble(double min, double max) {
		return rand.nextDouble()*(max-min)+min;
	}

	@Override
	public float nextFloat() {
		return rand.nextFloat();
	}

	@Override
	public float nextFloat(float min, float max) {
		return rand.nextFloat()*(max-min)+min;
	}

	@Override
	public int nextInt() {
		return rand.nextInt();
	}

	@Override
	public int nextInt(int min, int max) {
		if(max - min <= 0)
			return rand.nextInt(100);
		return rand.nextInt(max-min+1)+min;
	}

	@Override
	public boolean nextBoolean() {
		return rand.nextBoolean();
	}

	@Override
	public double nextGaussian() {
		return rand.nextGaussian();
	}

	@Override
	public double nextDouble() {
		return rand.nextDouble();
	}

}
