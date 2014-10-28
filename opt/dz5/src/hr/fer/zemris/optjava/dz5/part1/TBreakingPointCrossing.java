package hr.fer.zemris.optjava.dz5.part1;

import java.util.Arrays;
import java.util.Random;

public class TBreakingPointCrossing implements ICrossing {

	private int t=0;
	
	public TBreakingPointCrossing(int t) {
		this.t = t;
	}

	@Override
	public BitVectorSolution[] cross(BitVectorSolution p1, BitVectorSolution p2) {
		Random rand = new Random();
		int n=t;
		int[] breaks = new int[n];
		for(int i=0;i<n;i++) {
			breaks[i] = rand.nextInt(p1.bits.length);
		}
		Arrays.sort(breaks);
		BitVectorSolution[] children = new BitVectorSolution[2];
		children[0] = new BitVectorSolution(p1.bits.length);
		children[1] = new BitVectorSolution(p1.bits.length);
		int i=0,j=0,k=1,l=0;
		while(i<p1.bits.length) {
			if(i==breaks[l]) {
				if(l<breaks.length-1) l++;
				int h=k;
				k=j;
				k=h;
			}
			children[j].bits[i] = p1.bits[i];
			children[k].bits[i] = p1.bits[i];
			i++;
		}
		return children;
	}

}
