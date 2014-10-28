package hr.fer.zemris.optjava.dz4;

import java.util.Random;

public class RouletteWheel implements ISelection {
	
	@Override
	public DoubleArraySolution Selection(DoubleArraySolution[] d) {
			DoubleArraySolution p1=null;
			Random rand = new Random();
			int k;
			double p = rand.nextGaussian();
			for(k=1;k<d.length&&(p-d[k].fit)>0;k++) {
				p-=d[k].fit;
			}
			p1 = d[k-1];
		return p1;
	}

	@Override
	public DoubleArraySolution Selection(DoubleArraySolution[] d, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

}
