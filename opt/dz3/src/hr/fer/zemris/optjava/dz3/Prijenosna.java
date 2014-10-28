package hr.fer.zemris.optjava.dz3;

public class Prijenosna implements IFunction {

	private double[][] podaci;
	private ErrorP[] e = null;
	
	public Prijenosna (double[][] m){
		podaci = m;
		e = new ErrorP[podaci.length];
		for(int i =0;i<podaci.length;i++) {
			e[i] = new ErrorP(podaci[i]);
		}
	}

	@Override
	public double valueAt(double[] point) {
		double rez=0;
		for(int i=0;i<this.e.length;i++) {
			rez += this.e[i].valueAt(point)*this.e[i].valueAt(point);
		}
		return rez;
	}

}
