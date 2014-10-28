package hr.fer.zemris.optjava.dz4;

import java.util.Random;

public class BLXalphaBoxCrossing implements ICrossing {

	@Override
	public DoubleArraySolution cross(DoubleArraySolution p1,DoubleArraySolution p2) {
		DoubleArraySolution child = new DoubleArraySolution(p1.values.length);
		Random rand = new Random();
		double suma=0;
		child.values[0] = (p1.values[0]+p2.values[0])/2;
		return child;
	}

}
