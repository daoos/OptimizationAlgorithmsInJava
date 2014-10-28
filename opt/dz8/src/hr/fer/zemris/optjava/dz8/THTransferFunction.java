package hr.fer.zemris.optjava.dz8;

public class THTransferFunction implements ITransferFunction {

	public THTransferFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double value(double x) {
		return (1.0-Math.exp(-x))/(1.0+Math.exp(-x));
	}

}
