package hr.fer.zemris.optjava.dz3;

public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution>{
	
	private double [] deltas;
	
	public DoubleArrayUnifNeighborhood(double [] deltas) {
		this.deltas = deltas;
	}

	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution current) {
		current = current.duplicate();
		for (int i = 0; i < current.values.length; i++) {
			current.values[i] += RandomUtil.uniformRandomize(-deltas[i], deltas[i]);
		}
		return current;
	}

}
