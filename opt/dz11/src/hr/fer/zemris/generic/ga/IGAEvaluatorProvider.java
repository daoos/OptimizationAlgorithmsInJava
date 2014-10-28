package hr.fer.zemris.generic.ga;

public interface IGAEvaluatorProvider<T extends Number> {

	public IGAEvaluator<T> getEvaluator();
	
}
