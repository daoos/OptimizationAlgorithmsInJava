package hr.fer.zemris.optjava.dz2;

public interface IFunction {
	public int dimension();
	public double valueAt(double[] point);
	public double[] gradValueAt(double[] point);
}
