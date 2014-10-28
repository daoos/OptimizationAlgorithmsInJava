package hr.fer.zemris.optjava.dz9;

public class Function2 implements MOOProblem {

	double[] min=null,max=null;
	
	public Function2() {
		min = new double[2];
		max = new double[2];
		min[0] = 0.1;
		max[0] = 1.0;
		min[1] = 0.0;
		max[1] = 5.0;
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}
	
	public int getDimension() {
		return 2;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		objectives[0] = solution[0];
		objectives[1] = (solution[1]+1)/solution[0];
	}

	@Override
	public double min(int i) {
		return min[i];
	}

	@Override
	public double max(int i) {
		return max[i];
	}

	@Override
	public int solutionSize() {
		return 2;
	}

}
