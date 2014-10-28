package hr.fer.zemris.optjava.dz4;

public class BLXalphaCrossing implements ICrossing {

	@Override
	public DoubleArraySolution cross(DoubleArraySolution p1,DoubleArraySolution p2) {
		DoubleArraySolution child = new DoubleArraySolution(p1.values.length);
		double alpha = 10;
		for(int i = 0;i<p1.values.length;i++) {
			double cmin = Math.min(p1.values[i], p2.values[i]);
			double cmax = Math.max(p1.values[i], p2.values[i]);
			double I = cmax -cmin;
			double rez = RandomUtil.normalRandomize(cmin - I * alpha, cmax + I * alpha );
			child.values[i] = rez;
		}
		return child;
	}

}
