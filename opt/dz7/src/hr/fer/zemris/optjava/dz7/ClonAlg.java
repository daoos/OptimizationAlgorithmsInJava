package hr.fer.zemris.optjava.dz7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ClonAlg {

	ArrayList<Particle> particles=null;
	FFANN ffann = null;
	int N = 5,Nc=5;
	double ro = 2;
	double rt = 0.9;
	int d = 5;
	
	
	public ClonAlg(int n,int maxiter,double maxer,FFANN ffa) {
		particles=new ArrayList<Particle>();
		this.ffann = ffa;
		
		for(int i=0;i<n;i++) {
			this.particles.add(new Particle(this.ffann.getWeightsCount(), this.ffann));
		}
		
		while(maxiter > 0) {
			
			rt *=0.995;
			
			setaffinty();
			
			ArrayList<Particle> tmp = select(N);
			
			hypermutate(tmp);
			
			clonesaffinty(tmp);
			
			Particle[] p = null;
			p = tmp.toArray( new Particle[tmp.size()]);
			
			Arrays.sort(p);

			Particle[] f = null;
			
			f = this.particles.toArray(new Particle[this.particles.size()]);
 			
			Arrays.sort(f);
			
			Random rand = new Random();
			
			
			for(int i=0;i<d;i++) {
				f[f.length-i-1] = p[N+rand.nextInt(p.length-N)];
			}
			
			tmp.clear();
			
			int j=0;
			for(int i=0;i<f.length;i++) {
				if( j<N && p[j].fit<f[i].fit) {
					tmp.add(p[j]);
					j++;
				}else {
					tmp.add(f[i]);
				}
			}			
			
			this.particles=tmp;
			maxiter--;
			double g=2;
			for(Particle k:this.particles) if(k.fit<g) g = k.fit;
			System.out.println(g+" "+this.ffann.y+" "+rt);
		}
		
	}



	private void clonesaffinty(ArrayList<Particle> tmp) {
		double s=0;
		
		for(Particle z:tmp) {
			z.fit = z.ffann.eval(z.x);
			z.affinity=z.fit;
			s+=z.fit;
		}
		
		for(Particle z:tmp) {
			z.affinity/=s;
		}
	}


	private void hypermutate(ArrayList<Particle> tmp) {
		Random rand = new Random();
		
		for(Particle z:tmp) {
			for(int i=0;i<z.x.length;i++) {
				if(rand.nextDouble()>Math.exp(-z.fit*ro)) {
					z.x[i] += rt*rand.nextDouble()-rt/2;
				}
			}
			z.fit=z.ffann.eval(z.x);
		}
		
	}


	private ArrayList<Particle> select(int n2) {
		Random rand = new Random();
		
		ArrayList<Particle> ret = new ArrayList<Particle>();
		
		while(n2>0){
			double t = rand.nextDouble();
			boolean b = false;
			for(Particle z:this.particles) {
				
				t-=z.affinity;
				
				if(t<0) {
					Particle k = new Particle(z.x.length, ffann);
					k.x=z.x.clone();
					k.fit = z.fit;
					if(!ret.contains(k)){
					ret.add(k);
					n2--;
					b = true;
					break;
					}
				}
			}
			if(b) {
				Particle k = new Particle(ffann.getWeightsCount(), ffann);
				int h= rand.nextInt(this.particles.size());
				k.x=this.particles.get(h).x.clone();
				if(!ret.contains(k)) {
					k.fit=this.particles.get(h).fit;
					for(int u=0;u<Nc;u++) ret.add(k);
					n2--;
				}
			}
		}
		
		return ret;
	}


	private void setaffinty() {
		double s=0;
		for(Particle z:this.particles) {
			z.fit = this.ffann.eval(z.x);
			z.affinity = 1-z.fit;
			s += 1-z.fit;
		}
		for(Particle z:this.particles) {
			z.affinity/=s;
		}
	}

}
