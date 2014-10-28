package hr.fer.zemris.generic.ga;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class GASolution<T extends Number> implements Comparable<GASolution<T>>{

	public int chromosomeLength;
	public Number[] data=null;
	public double fitness = 0.0d;
	private T maximumValueOfX,maximumValueOfY;

	
	public GASolution(int chromosomeLength,boolean randomize,T maximumValueOfX,T maximumValueOfY) {
		
		this.chromosomeLength = chromosomeLength;
		this.maximumValueOfX = maximumValueOfX;
		this.maximumValueOfY = maximumValueOfY;
		
		this.data = new Number[chromosomeLength];
		
		generateRandomChromosome();
		
	}
	
	private void generateRandomChromosome() {
		
		IRNG rand = RNG.getRNG(); 
		
		data[0] = rand.nextDouble(0, 255);
		
		for (int i = 1; i < chromosomeLength; i += 5) {
			data[i] = (rand.nextInt(0, maximumValueOfX.intValue() - 1));
			data[i + 1] = (rand.nextInt(0,  maximumValueOfY.intValue() - 1));
			data[i + 2] = (rand.nextInt(0, maximumValueOfX.intValue() - data[i].intValue() - 1));
			data[i + 3] = (rand.nextInt(0, maximumValueOfY.intValue() - data[i + 1].intValue() - 1));
			data[i + 4] = (rand.nextInt(0, 255));
		}
		
	}

	public GASolution(int chromosomeLength) {
		this.chromosomeLength = chromosomeLength;
		this.data = new Number[chromosomeLength];
	}

	public Number[] getData() {
		return data;
	}

	public GASolution<T> duplicate() {
		GASolution<T> returnValue = new GASolution<T>(chromosomeLength);
		
		returnValue.data = data.clone();
		returnValue.chromosomeLength = chromosomeLength;
		
		return returnValue;
	}

	@Override
	public int compareTo(GASolution<T> o) {
		return -Double.compare(this.fitness, o.fitness);
	}

}
