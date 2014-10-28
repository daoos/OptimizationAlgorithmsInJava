package hr.fer.zemris.optjava.dz2;

public class ErrorP implements IHFunction {

	private double[] podaci=null;
	
	public ErrorP (double[] m){
		this.podaci = m;
	}
	
	@Override
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

	@Override
	public double[] gradValueAt(double[] point) {
		double[] rez = new double[dimension()];
		rez[0] = -podaci[0];
		rez[1] = -Math.pow(podaci[0], 3)*podaci[1];
		rez[2] = -Math.pow(Math.E ,point[3]*this.podaci[2])*(1+Math.cos(point[4]*this.podaci[3]));
		rez[3] = -point[2]*Math.pow(Math.E ,point[3]*this.podaci[2])*(1+Math.cos(point[4]*this.podaci[3]))*this.podaci[2];
		rez[4] = point[2]*Math.pow(Math.E ,point[3]*this.podaci[2])*Math.sin(point[4]*podaci[3])*podaci[3];
		rez [5] = -podaci[3]*Math.pow(podaci[4], 2);
		return rez;
	}

	@Override
	public Object hesseI(double[] point) {
		// TODO Auto-generated method stub
		return null;
	}

}
