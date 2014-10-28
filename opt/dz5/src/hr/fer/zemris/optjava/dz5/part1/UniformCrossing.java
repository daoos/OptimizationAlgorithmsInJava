package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

public class UniformCrossing implements ICrossing {

	public UniformCrossing() {
	}

	@Override
	public BitVectorSolution[] cross(BitVectorSolution p1, BitVectorSolution p2) {
		BitVectorSolution[] children = new BitVectorSolution[2];
		children[0] = new BitVectorSolution(p1.bits.length);
		children[1] = new BitVectorSolution(p1.bits.length);
		Random rand = new Random();
		for(int i=0;i<p1.bits.length;i++) {
			if(rand.nextBoolean()) {
				children[0].bits[i] = p1.bits[i];
				children[1].bits[i] = p2.bits[i];
			}else {
				children[1].bits[i] = p1.bits[i];
				children[0].bits[i] = p2.bits[i];
			}
		}
		return children;
	}

}
