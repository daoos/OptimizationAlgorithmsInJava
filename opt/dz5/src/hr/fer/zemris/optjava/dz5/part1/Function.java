package hr.fer.zemris.optjava.dz5.part1;

public class Function {

	private int n;
	
	public Function(int n) {
		this.n = n;
	}

	public double value(BitVectorSolution b){
		int k = b.getOnes();
		if(k<0.8*n) return (double)k/n;
		else if(k<0.9*n) return 0.8;
		else return 2*(double)k/n -1;
	}
	
}
