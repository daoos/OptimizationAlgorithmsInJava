package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.RNG;

public class Crossing {
	
	public GASolution<Number> getChild( GASolution<Number> 	parent1,GASolution<Number> parent2) {
		
		GASolution<Number> ret = parent1.duplicate();
		
		
		int slice = RNG.getRNG().nextInt(0, parent1.data.length-1);
		
		for(int i = slice;i<parent2.chromosomeLength;i++) {
			
			ret.data[i] = (Number)((parent1.data[i].intValue() + parent2.data[i].intValue())/2);
		
		}
		
		return ret;
	}
}
