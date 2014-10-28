package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

public class OneBreakingPointCrossing implements ICrossing {

	public OneBreakingPointCrossing() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public BitVectorSolution[] cross(BitVectorSolution p1, BitVectorSolution p2) {
		Random rand = new Random();
		int breakingPoint= rand.nextInt(p1.bits.length);
		BitVectorSolution[] children = new BitVectorSolution[2];
		children[0] = new BitVectorSolution(p1.bits.length);
		children[1] = new BitVectorSolution(p1.bits.length);
		for(int i=0;i<breakingPoint;i++) {
			children[0].bits[i] = p1.bits[i];
			children[1].bits[i] = p2.bits[i];
		}
		for(int i=breakingPoint;i<p1.bits.length;i++) {
			children[0].bits[i] = p2.bits[i];
			children[1].bits[i] = p1.bits[i];
		}
		return children;
	}

}
