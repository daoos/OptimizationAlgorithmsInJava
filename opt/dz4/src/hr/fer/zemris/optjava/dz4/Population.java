package hr.fer.zemris.optjava.dz4;

public class Population  {

	public double fitMax;
	
	public Population(int sizeOfPopulation,int dimension,double[] min,double[] max) {
		DoubleArraySolution[] population  = new DoubleArraySolution[sizeOfPopulation];
		for(int i=0;i<population.length;i++){
			population[i] = new DoubleArraySolution(dimension,min,max);
		}
	}
	
}
