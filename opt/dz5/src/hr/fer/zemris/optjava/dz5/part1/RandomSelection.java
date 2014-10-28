package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;
import java.util.Random;

public class RandomSelection implements ISelection<BitVectorSolution> {

	public RandomSelection() {
	}

	@Override
	public BitVectorSolution select(ArrayList<BitVectorSolution> population) {
		Random rand = new Random();
		BitVectorSolution p = population.get(rand.nextInt(population.size()-1));
		return p;
	}

}
