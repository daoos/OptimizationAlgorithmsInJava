package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

public class BitVectorSolution {

	public boolean[] bits=null;
	public double value,fit;
	public int k=-1;
	
	public BitVectorSolution(int n) {
		bits = new boolean[n];
	}
	
	public int getOnes(){
		k =0;
		for(boolean b:bits) {
			if(b) k++;
		}
		return k;
	}
	
	public void randomize(){
		Random rand = new Random();
		for(int i=0;i<bits.length;i++) {
			bits[i]=rand.nextBoolean();
		}
	}

}
