package hr.fer.zemris.optjava.dz3;

public class BitVectorDecoder implements IDecoder<BitVectorSolution> {
	double [] mins;
	double [] maxs;
	int totalBits;
	int n;
	
	public BitVectorDecoder (double[] maxs, double[] mins, int totalBits, int n) {
		this.maxs = maxs;
		this.mins = mins;
		this.totalBits = totalBits;
		this.n = n;
	}
	
	public BitVectorDecoder (double min, double max, int totalBits, int n) {
		this.mins = new double[totalBits];
		this.maxs = new double[totalBits];
		for(int i=0;i<totalBits;i++)this.mins[i] = min;
		for(int i=0;i<totalBits;i++)this.maxs[i] = max;
		this.totalBits = totalBits;
		this.n = n;
	}
	
	public int getTotalBits() {
		return totalBits;
	}
		
	
	@Override
	public double[] decode(BitVectorSolution input) {
		  double div = (double) (1 << n) - 1;
		  double ret[] = new double[totalBits / n];
		  
		  for(int i = 0; i < ret.length; i++) {
			  double x = (maxs[i] - mins[i]) / div;
			  ret[i] = mins[i] + x * calculate(input.bits, i * n, n);
		  }
		  return ret;
	}

	private double calculate(boolean[] bits, int i, int n) {
			double sol = 0.0;
				for(; i < i + n ; i++) {
					sol += bits[i]  ? 1 << (n - i) : 0;
				}
		  return sol;
	}

	@Override
	public void decode(BitVectorSolution t, double[] d) {
		// TODO Auto-generated method stub
		
	}
}
