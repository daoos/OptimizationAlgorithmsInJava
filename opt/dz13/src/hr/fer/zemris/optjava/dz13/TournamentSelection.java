package hr.fer.zemris.optjava.dz13;

import java.util.Random;

public class TournamentSelection {

	private int n;
	private double[] fitness;
	private Random rand = null;
	private boolean fit_create = true;
	
	public TournamentSelection(int n,double[] fitness) {
		this.n = n;
		this.fitness = fitness;
		this.rand = new Random();
	}
	
	public int select() {
		int index = 0;
		if( fit_create ) {
			create_fit();
			fit_create = false;
		}
		
		int r = n;
		double best_fit=Double.MIN_VALUE;
		
		while ( r > 0 ) {
			r--;
			double chance = rand.nextDouble();
			for(int i = 0; i < fitness.length ; i++) {
				chance -= fitness[i];
				if(chance < 0) {
					if ( best_fit < fitness[i] ) {
						index = i;
						best_fit = fitness[i];
					}
				}
			}
			
		}
		
		return index;
	}

	private void create_fit() {
		
		double sum=0,min=Double.MAX_VALUE,max=Double.MIN_VALUE;
		
		for(Double t : fitness) {
			sum += t;
			if( t < min ) {
				min = t;
			}
			if( t > max ) {
				max = t;
			}
		}
		
		max = 0;
		for(int i=0;i<fitness.length;i++) {
			fitness[i] = (fitness[i]-min)/(sum-min*fitness.length);
			max += fitness[i];
		}
		//System.out.println(max);
	}

}
