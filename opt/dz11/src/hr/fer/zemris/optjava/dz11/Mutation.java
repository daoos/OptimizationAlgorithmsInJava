package hr.fer.zemris.optjava.dz11;

import java.util.TreeSet;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Mutation {

	public double chance=0.05;
	public int range = 5;
	public int numberOfMutations,maxX,maxY;
	private IRNG rand = null;
	
	
	public Mutation(String parametersFilePath, double lengthOfParameters,int maxX,int maxY) {
		ParametersLoader.loadParameters(parametersFilePath,this);
		//System.out.println(chance+" "+range);
		rand = RNG.getRNG();
		numberOfMutations = (int) (chance*lengthOfParameters)+1;
		this.maxX = maxX;
		this.maxY = maxY;
		
	}
	
	/*
	 * TreeSet<Integer> set = new TreeSet<>();
		for (int i = 0; i < number_of_mutations; i++) {
			int poz = rand.nextInt(0, n);
			if (!set.contains(poz)) {
				set.add(poz);
				ch.data[poz] += rand.nextInt(-range_of_mutation,
						range_of_mutation);
				if (poz == 0) {
					if (ch.data[poz] < 0 || ch.data[poz] > 255) {
						ch.data[poz] = rand.nextInt(0, 255);
					}
				} else if ((poz - 1) % 5 == 0) {
					if (!(ch.data[poz] > 0 && ch.data[poz] < image.getWidth())) {
						ch.data[poz] = rand.nextInt(0, image.getWidth()
								- ch.data[poz + 2]);
					}
				} else if ((poz - 1) % 5 == 1) {
					if (!(ch.data[poz] > 0 && ch.data[poz] < image.getHeight())) {
						ch.data[poz] = rand.nextInt(0, image.getHeight()
								- ch.data[poz + 2]);
					}
				} else if ((poz - 1) % 5 == 2) {
					if (ch.data[poz] > image.getWidth() - ch.data[poz - 2]) {
						ch.data[poz] = rand.nextInt(0, image.getWidth()
								- ch.data[poz - 2]);
					}
				} else if ((poz - 1) % 5 == 3) {
					if (ch.data[poz] > image.getHeight() - ch.data[poz - 2]) {
						ch.data[poz] = rand.nextInt(0, image.getHeight()
								- ch.data[poz - 2]);
					}
				} else if ((poz - 1) % 5 == 4) {
					if (ch.data[poz] < 0 || ch.data[poz] > 255) {
						ch.data[poz] = rand.nextInt(0, 255);
					}
				}
			} else {
				i--;
			}
	 * */
	
	public void mutate(GASolution<Number> child) {
		
		TreeSet<Integer> set = new TreeSet<>();
		
		for(int h = 0;h < numberOfMutations; h++) {
			int poz = rand.nextInt(0, child.data.length-1);
			
			child.data[poz] = rand.nextInt()%256; 
			
			/*int poz = rand.nextInt(0, nextGeneration.data.length - 1);
			if (!set.contains(poz)) {
				set.add(poz);
				nextGeneration.data[poz] = nextGeneration.data[poz].intValue() + rand.nextInt(-range,
						range);
				if (poz == 0) {
					if (nextGeneration.data[poz].intValue() < 0 || nextGeneration.data[poz].intValue() > 255) {
						nextGeneration.data[poz] = rand.nextInt(0, 255);
					}
				} else if ((poz - 1) % 5 == 0) {
					if (!(nextGeneration.data[poz].intValue() > 0 && nextGeneration.data[poz].intValue() < maxX)) {
						nextGeneration.data[poz] = rand.nextInt(0, maxX)
								- nextGeneration.data[poz + 2].intValue();
					}
				} else if ((poz - 1) % 5 == 1) {
					if (!(nextGeneration.data[poz].intValue() > 0 && nextGeneration.data[poz].intValue() < maxY)) {
						nextGeneration.data[poz] = rand.nextInt(0, maxY
								- nextGeneration.data[poz + 2].intValue());
					}
				} else if ((poz - 1) % 5 == 2) {
					if (nextGeneration.data[poz].intValue() > maxX - nextGeneration.data[poz - 2].intValue()) {
						nextGeneration.data[poz] = rand.nextInt(0, maxX
								- nextGeneration.data[poz - 2].intValue());
					}
				} else if ((poz - 1) % 5 == 3) {
					if (nextGeneration.data[poz].intValue() > maxY - nextGeneration.data[poz - 2].intValue()) {
						nextGeneration.data[poz] = rand.nextInt(0, maxY
								- nextGeneration.data[poz - 2].intValue());
					}
				} else if ((poz - 1) % 5 == 4) {
					if (nextGeneration.data[poz].intValue() < 0 || nextGeneration.data[poz].intValue() > 255) {
						nextGeneration.data[poz] = rand.nextInt(0, 255);
					}
				}
			} else {
				h--;
			}*/
			
		}
		
	}

}
