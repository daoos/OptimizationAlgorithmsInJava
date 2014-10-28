package hr.fer.zemris.optjava.dz8;

public interface INeuralNet {

	int getWeigthsCount();
	
	double eval(Double[] x);
	
}
