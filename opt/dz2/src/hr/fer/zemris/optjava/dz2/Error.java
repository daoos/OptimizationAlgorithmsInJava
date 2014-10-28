package hr.fer.zemris.optjava.dz2;

public class Error {

	private double[] podaci;
	
	public Error(double[] m) {
		this.podaci = m;
	}
	public double valueAt(double[] point){
		double r=0d;
		for(int i=0;i<this.podaci.length-1;i++) {
			r += this.podaci[i]*point[i];
		}
		r -=this.podaci[this.podaci.length-1];
		return r;
	}
	
	public double getX(int index){
		return this.podaci[index];
	}
	
	public double[] gradValueAt(double[] point){
		double[] r = new double[this.podaci.length-1];
		for(int i=0;i<r.length;i++) {
			r[i] = this.podaci[i];
		}
		return r;
	}

}
