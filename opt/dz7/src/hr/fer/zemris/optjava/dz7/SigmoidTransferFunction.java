package hr.fer.zemris.optjava.dz7;

public class SigmoidTransferFunction implements ITransferFunction {

	public SigmoidTransferFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double value(double x) {
		return 1/(1+Math.pow(Math.E, -x));
	}

}
