package hr.fer.zemris.optjava.dz10;

public interface MOOProblem {

	int getNumberOfObjectives();
	void evaluateSolution(double[] solution, double[] objectives);
	double min(int i);
	double max(int i);
	int solutionSize();
	public int getDimension();
	
}
