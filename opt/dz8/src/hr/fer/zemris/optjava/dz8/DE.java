package hr.fer.zemris.optjava.dz8;

import java.util.ArrayList;
import java.util.Random;

public class DE {

	Double F1 = 0.8,F2=0.99;
	ArrayList<Double[]> population=null;
	INeuralNet network=null;
	Random rand = null;
	Double Cr = 0.20,Crp=0.25;
	Double[] best = null;
	
	public DE(INeuralNet net,int n, double maxerr, int maxiter) {
		
		network = net;
		
		Double[] bl = new Double[network.getWeigthsCount()];
		Double[] bh = new Double[network.getWeigthsCount()];
		
		for(int i=0;i<bl.length;i++) {
			bl[i] = -1.0;
			bh[i] = 1.0;
		}
		
		population = new ArrayList<Double[]>();
		
		rand = new Random();
		
		for(int i = 0; i < n;i++) {
			Double [] t = new Double[network.getWeigthsCount()];
			for(int j=0;j<t.length;j++) {
				t[j] = rand.nextDouble()*(bh[j]-bl[j]) + bl[j];
			}
			population.add(t);
		}
		
		best = population.get(0);
		
		double err = Double.MAX_VALUE;
		
		
		
		int m = maxiter;
		
		Crp = rand.nextDouble()/2;
		
		while ( maxiter > 0 && err > maxerr) {
			
			Cr = Crp * maxiter/m + 0.1;
			F1 = (double) (1.5*maxiter/m+0.2);
			F2 = (double) (1.5*(m-maxiter)/m+0.5);
			
			maxiter--;
			
			for( Double[] s : population ) {
				s=cross(s);
				double e = network.eval(s); 
				if(e<err) {
					err = e;
					best = s;
				}
						
			}
			
			System.out.println(err);
			
		}
	}


	private Double[] cross(Double[] s) {
		ArrayList<Double[]> cand = new ArrayList<Double[]>();
		cand.add(s);
		
		while(cand.size()<4) {
			Double[] t = population.get(rand.nextInt(population.size()));
			if(cand.contains(t)) continue;
			cand.add(t);
		}
		
		Double[] mutant = new Double[s.length];
		
		for(int i=0;i<s.length;i++) {
			mutant[i] = cand.get(1)[i] + F1 *(cand.get(2)[i]-cand.get(3)[i]) + F2 *(best[i] -s[i]);
		}
		
		int start = rand.nextInt(s.length);
		
		Double[] test=s.clone();
		
		for(int i=0;i<s.length;i++) {
			if(rand.nextDouble()<Cr) test[i] = mutant[i];
		}
		
		if(network.eval(s)>network.eval(test)) {
			return test;
		}else return s; 
	}

}
