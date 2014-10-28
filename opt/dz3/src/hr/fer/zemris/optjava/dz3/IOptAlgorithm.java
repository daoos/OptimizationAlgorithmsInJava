package hr.fer.zemris.optjava.dz3;

public interface IOptAlgorithm<T extends SingleObjectiveSolution> {

	void run(IFunction f, boolean min, T s,
			ITempSchedule plan, INeighborhood<T> susjedstvo, IDecoder<T> dekoder);

}
