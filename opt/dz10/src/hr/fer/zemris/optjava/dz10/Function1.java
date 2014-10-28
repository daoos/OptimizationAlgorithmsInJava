package hr.fer.zemris.optjava.dz10;

public class Function1 implements MOOProblem {

	double[] min=null,max=null;
	int size=4;
	
	public Function1() {
		min = new double[4];
		max = new double[4];
		for(int i=0;i<min.length;i++) {
			min[i] = -5.0;
			max[i] = 5.0;
		}
	}

	@Override
	public int getNumberOfObjectives() {
		return 4;
	}
	
	public int getDimension() {
		return 4;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		for(int i=0;i<size;i++) {
			objectives[i] = solution[i]*solution[i];
		}

	}

	@Override
	public int solutionSize() {
		return 4;
	}

	@Override
	public double min(int i) {
		return min[i];
	}

	@Override
	public double max(int i) {
		return max[i];
	}

}
