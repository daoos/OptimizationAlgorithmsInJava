package hr.fer.zemris.optjava.dz4;

public class ErrorP implements IFunction {

	private double[] podaci=null;
	
	public ErrorP (double[] m){
		this.podaci = m;
	}
	
	public int dimension() {
		return this.podaci.length;
	}

	@Override
	public double valueAt(double[] point) {
		double a,b,c,d;
		a = podaci[0] * point[0];
		b = Math.pow(podaci[0], 3)*podaci[1]*point[1];
		c = point[2]*Math.pow(Math.E ,point[3]*this.podaci[2])*(1+Math.cos(point[4]*this.podaci[3]));
		d = point[5]*podaci[3]*Math.pow(podaci[4], 2);
		return  podaci[podaci.length-1] -a-b-c-d;
	}
}
