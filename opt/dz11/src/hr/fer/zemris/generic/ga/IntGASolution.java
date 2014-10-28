package hr.fer.zemris.generic.ga;

public class GASolution<T> extends GASolution<T> {

	public GASolution<T>(Integer[] n) {
		data = n;
	}

	@Override
	public GASolution<int[]> duplicate() {
		IntGASolution t = new IntGASolution(data.clone());
		t.fitness = fitness;
		return t;
	}

}
