package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;
import java.util.Random;

public class TournamentSelection implements ISelection<BitVectorSolution> {

	private int n;
	
	public TournamentSelection(int n) {
		this.n = n;
	}

	@Override
	public BitVectorSolution select(ArrayList<BitVectorSolution> population) {
		Random rand = new Random();
		BitVectorSolution p = population.get(rand.nextInt(population.size()-1)),t=null;
		for(int i=0;i<n-1;i++) {
			t =population.get(rand.nextInt(population.size()-1));
			if(t.value>p.value) p =t;
		}
		return p;
	}

}
