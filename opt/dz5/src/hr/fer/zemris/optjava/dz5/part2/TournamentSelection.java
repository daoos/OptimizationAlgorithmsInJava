package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Random;

public class TournamentSelection {

	private int k;
	private Random rand=null;
	
	public TournamentSelection(int z,Random r) {
		k=z;
		rand = r;
	}
	
	public PermSolution select(ArrayList<PermSolution> population){
		PermSolution p=population.get(rand.nextInt(population.size())),t =null;
		for(int i=0;i<k-1;i++) {
			t = population.get(rand.nextInt(population.size()));
			if(t.value<p.value) {
				p =t;
			}
		}
		return p;
	}

}
