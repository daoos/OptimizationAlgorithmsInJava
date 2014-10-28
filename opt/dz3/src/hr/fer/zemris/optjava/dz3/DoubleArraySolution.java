package hr.fer.zemris.optjava.dz3;

public class DoubleArraySolution extends SingleObjectiveSolution {
	public double [] values;
	
	public DoubleArraySolution ( int numberOfElements ) {
		values = new double [numberOfElements];
	}
	
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}
	
	public DoubleArraySolution duplicate() {
		DoubleArraySolution d = new DoubleArraySolution(values.length);
		d.values = values.clone();
		return d;
	}
	
	public void Randomize (double[] minimum, double[] maximum ) {
		values = RandomUtil.randomizeDoubleArray(minimum.length, minimum, maximum);	
	}

}
