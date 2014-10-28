package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

public class BitMutation implements IMutation<BitVectorSolution> {

	private double chance=0;
	
	public BitMutation(double chance) {
		this.chance=chance;
	}

	@Override
	public void mutate(BitVectorSolution t) {
		int numberOfMutations=(int) Math.ceil(chance*t.bits.length);
		Random rand = new Random();
		for(int i=0;i<numberOfMutations;i++){
			int k=rand.nextInt(t.bits.length);
			t.bits[k] = !t.bits[k]; 
		}
	}
	

}
