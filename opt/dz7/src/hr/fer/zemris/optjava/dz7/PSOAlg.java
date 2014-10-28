package hr.fer.zemris.optjava.dz7;

import java.util.ArrayList;
import java.util.Random;

public class PSOAlg {

	ArrayList<Particle> particles=null;
	
	int y=0;
	boolean typea=false;
	int local_d;
	Double[] global_best=null;
	double global_fit=2;
	Random rand=null;
	double w,wmin=0.1,wmax=0.9;
	double c1=2.5,c2=2.5;
	double wtmax=0;
	
	public PSOAlg(String st,int n, double merr, int maxiter, FFANN ffann) {
		this.particles = new ArrayList<Particle>();
		rand = new Random();
		
		if(st.equals("pso-a")) {
			this.typea = true;
		}else {
			st = st.split("-")[2];
			this.local_d = Integer.parseInt(st);
		}
		this.wtmax=maxiter;
		createPop(n,ffann);
		
		for(int i=0;i<maxiter;i++) {
			if(this.typea) {
				evalglobalbest();
				updatespeed();
				updatew(i);
				for(Particle z:this.particles) {
					z.updatePos();
				}
			}else {
				evalglobalbest();
				updatew(i);
				updatespeedl();
				for(Particle z:this.particles) {
					z.updatePos();
				}
			}
			//System.out.println(this.global_fit+" "+this.y);
		}
		System.out.println(this.global_fit);
		}
		
	
	void updatespeedl() {
		Double [] v = new Double[this.particles.get(0).x.length];
		for(int i=0;i<this.particles.size();i++) {
			Double [] x=this.particles.get((this.local_d+i)%this.particles.size()).lbest;
			for(int j=-this.local_d;j<this.local_d;j++) {
				if(this.particles.get(0).ffann.eval(
						this.particles.get((i+j+this.particles.size())%this.particles.size()).lbest)<
							this.particles.get(0).ffann.eval(x)) {
					x = this.particles.get((i+j+this.particles.size())%this.particles.size()).lbest;
				}
			}
			
			for(int j=-this.local_d;j<this.local_d;j++) {
				this.particles.get((i+j+this.particles.size())%this.particles.size()).lbest = x;
			}
			
			for(int j=0;j<x.length;j++) {
			v[j] = -this.c1*rand.nextDouble()*(this.particles.get(i).x[j]-x[j])-
					this.c2*rand.nextDouble()*(this.particles.get(i).x[j]-this.particles.get(i).pbest[j]);
			}
			this.particles.get(i).updateVel(v, w);
		}
	}


	private void updatespeed() {
		for(Particle z:this.particles) {
			Double[] v = new Double[z.x.length];
			for(int i=0;i<v.length;i++) {
				v[i] = this.c1*rand.nextDouble()*(z.pbest[i]-z.x[i])+this.c2*rand.nextDouble()*(this.global_best[i]-z.x[i]);
			}
			z.updateVel(v,this.w);
		}
		
	}

	private void updatew(int i) {
		this.w = (this.wtmax-i)/(wtmax)*(wmax-wmin)+this.wmin;
	}

	private void evalglobalbest() {
		this.global_best=null;
		for(Particle z:this.particles) {
			if(this.global_best==null) {
				this.global_best = z.x;
				this.global_fit = z.fit;
				this.y = z.ffann.y;
			}else if(this.global_fit>z.fit) {
				this.global_best = z.x;
				this.y = z.y;
				this.global_fit = z.fit;
				this.y = z.ffann.y;
			}
		}
		
	}

	private void createPop(int n,FFANN ffann) {
		while (n>0) {
			Particle p = new Particle(ffann.getWeightsCount(),ffann);
			if(!this.particles.contains(p)) {
				p.fit = ffann.eval(p.x);
				this.particles.add(p);
				n--;
			}
		}
		
	}

}
