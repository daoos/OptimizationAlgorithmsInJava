package hr.fer.zemris.optjava.dz3;


public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution> {

	private double [] deltas;
	
	public DoubleArrayNormNeighborhood( double [] deltas) {
		this.deltas = deltas;
	}
	
	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution current) {
		current = current.duplicate();
		for (int i = 0; i < current.values.length; i++) {
			current.values[i] += RandomUtil.normalRandomize(-deltas[i], deltas[i]);
		}
		return current;
	}

}
