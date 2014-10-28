package hr.fer.zemris.optjava.dz3;

public class DoubleArraySolutionDecoder implements IDecoder<DoubleArraySolution> {

	@Override
	public double[] decode(DoubleArraySolution t) {
		return t.values.clone();
	}

	@Override
	public void decode(DoubleArraySolution t, double[] d) {
		d = t.values.clone();
		
	}
	
}
