package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Selection {

	Object[] population = null;
	private double fitnessSum=0,minimumFitness=Double.MAX_VALUE;
	private int numberOfRows;
	private IRNG rand=null;
	
	
	public Selection(Object[] objects, int numberOfRows) {
		
		this.numberOfRows = numberOfRows;
		
		this.population = objects;
		
		rand = RNG.getRNG();
		
		for(Object h:objects) {
			GASolution<Number> t = (GASolution<Number>) h;
			fitnessSum += t.fitness;
			if( minimumFitness > t.fitness ) {
				minimumFitness = t.fitness;
			}
		}
		
		for(int i = 0; i < objects.length;i++) {
			
			((GASolution<Number>)this.population[i]).fitness -= minimumFitness;
			((GASolution<Number>)this.population[i]).fitness /= (fitnessSum - objects.length*minimumFitness);
			//System.out.println(this.population[i].fitness);
		
		}
		
	}
	
	public GASolution<Number> getUnit() {
		GASolution<Number> ret= (GASolution<Number>)population[0];
		
		double bestFitness = 0;
		int count = numberOfRows;
		
		while (count > 0) {
			
			rand = RNG.getRNG();
			double chance = rand.nextDouble();
			
			for(int i = 0; i < population.length; i++) {
				chance -= ((GASolution<Number>)population[i]).fitness;
				
				if ( chance < 0 ) {
					if ( bestFitness < ((GASolution<Number>)population[i]).fitness) {
						bestFitness = ((GASolution<Number>)population[i]).fitness;
						ret = (GASolution<Number>)population[i];
					}
					break;
				}
				
			}
			
			count--;
			
		}
		
		return ret;
	}

}
