package hr.fer.zemris.optjava.dz4;

import java.util.Random;
	
public class DoubleArraySolution{
	public double [] values;
	public double fit;
	
	public DoubleArraySolution ( int numberOfElements ) {
		values = new double [numberOfElements];
	}
	
	public DoubleArraySolution ( int numberOfElements,double[] min,double[] max) {
		values = new double [numberOfElements];
		Random rand = new Random();
		for(int i=0;i<values.length;i++) {
			values[i] = rand.nextDouble()*(max[i]-min[i])+min[i];
		}
	}
	
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}
	
	public String toString(){
		String st="";
		for(Double d:values) {
			st += d.toString()+" ";
		}
		return st;
		
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
