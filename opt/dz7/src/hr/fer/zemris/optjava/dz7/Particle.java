package hr.fer.zemris.optjava.dz7;

import java.util.Random;

public class Particle implements Comparable<Particle> {

	Integer yb = 0;
	Integer y = 0;
	Double[] x=null;
	Double[] v=null;
	Double[] pbest=null;
	Double[] lbest=null;
	Random rand=null;
	double fit;
	double affinity;
	FFANN ffann=null;
	
	double vmin=-0.5,vmax=0.5,xmin=-1,xmax=1;
	
	public Particle(int n,FFANN ffa) {
		this.ffann=ffa;
		this.x=new Double[n];
		rand = new Random();
		this.v = new Double[this.x.length];
		for(int i=0;i<this.x.length;i++) {
			this.v[i] = (vmax-vmin)*rand.nextDouble()+vmin;
			this.x[i] = (xmax-xmin)*rand.nextDouble()+xmin;
		}
		this.pbest = this.x.clone();
		this.lbest = this.x.clone();
	}
	
	public int hashCode() {
		return this.x.hashCode();
	}
	
	public void updateVel(Double[] v2, double w) {
		for(int i=0;i<v2.length;i++) {
			this.v[i] = this.v[i]*w+ v2[i];
			if(this.v[i]<vmin||this.v[i]>vmax||Math.abs(this.v[i])<0.0001) {
				this.v[i] = (vmax-vmin)*rand.nextDouble()+vmin;
			}
		}
	}
	
	public Double[] getX() {
		return this.x.clone();
	}
	
	public void updatePos() {
		for(int i=0;i<this.x.length;i++) {
			this.x[i] += this.v[i];
		}
		double a = this.ffann.eval(x);
		this.y=this.ffann.y;
		double b = this.ffann.eval(pbest),c =this.ffann.eval(lbest);
		if(a<b) {
			this.pbest=this.x.clone();
		}
		if(a<c) {
			this.lbest=this.x.clone();
		}
		this.fit=a;
	}

	public Double[] getPbest() {
		return this.pbest.clone();
	}
	
	public void updatePbest(Double[] t) {
		this.pbest=t;
	}

	@Override
	public int compareTo(Particle arg0) {
		if((this.fit-arg0.fit)< 0) 
			return -1;
		else if((this.fit-arg0.fit)< 0.000000001)
			return 0;
		else return -1;
	}
	
}
