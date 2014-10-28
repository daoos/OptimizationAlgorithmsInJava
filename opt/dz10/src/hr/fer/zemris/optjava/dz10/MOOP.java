package hr.fer.zemris.optjava.dz10;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class MOOP {

	/**
	 * @param args
	 */
	
	static double sigmashare=2.0,alpha = 2.0;
	
	static double sumafit=0;
	
	static double mutation = 0.01;
	
	static MOOProblem f;
	
	static boolean decision_space;
	
	static ArrayList<ArrayList<Solution>> fronts=null;
	
	static ArrayList<Solution> population=null,prev_pop = null;
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		if(args.length!= 4) throw new IllegalArgumentException("Broj argumenata je 4");
	
		int fja = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		decision_space = true;
		if(args[2].equals("objective-space")) decision_space = false;
		int maxiter = Integer.parseInt(args[3]);
		
		if(fja==1) f = new Function1();
		else f = new Function2();
		
		population = new ArrayList<Solution>();
		
		while (population.size()!=n) {
			Solution t = new Solution(f);
			t.setEval();
			if(!population.contains(t)) {
				population.add(t);
			}
		}
		
		while (maxiter>0) {
			prev_pop = (ArrayList<Solution>) population.clone();
			non_dominated_sort();
			set_fit();
			
			ArrayList<Solution> q = new ArrayList<Solution>();
			
			while (q.size()<n) {
				Solution t = cross();
				t.setEval();
				repair(t);
				q.add(t);
			}
			
			population = new ArrayList<Solution>();
			population.addAll(prev_pop);
			population.addAll(q);
			
			non_dominated_sort();
			set_fit();
			
			int m=n;
			
			ArrayList<Solution> nextgen = new ArrayList<Solution>();
			
			for(ArrayList<Solution> o:fronts) {
				if(m-o.size()>0) {
					for(Solution s:o) {
						s.setEval();
						nextgen.add(s);
					}
					m -= o.size(); 
				}else {
					population = o;
					non_dominated_sort();
					set_fit();
					for(ArrayList<Solution> u:fronts) {
						if(m<0) break;
						for(Solution d:u) {
							d.setEval();
							nextgen.add(d);
							m--;
							if(m<0) break;
						}
					}
					break;
				}
			}
			
			population = nextgen;
			
			maxiter--;
		}
		
		non_dominated_sort();
		
		PrintWriter writer = new PrintWriter("izlaz-dec.txt", "UTF-8");
		PrintWriter writer1 = new PrintWriter("izlaz-obj.txt", "UTF-8");
		
		
		for(ArrayList<Solution> s:fronts) {
			System.out.println(s.size());
			for(Solution z:s) {
				for(int i=0;i<z.eval.length;i++) {
					writer1.print(z.eval[i]+" ");
				}
				writer1.println();
				for(int i=0;i<z.x.length;i++) {
					writer.print(z.x[i]+" ");
				}
				writer.println();
			}
		}
		writer.close();
		writer1.close();
	}

	private static void repair(Solution t) {
		for(int i=0;i<t.x.length;i++) {
			if(Double.compare(t.x[i], f.min(i))<0 || Double.compare(t.x[i], f.max(i))>0) {
				Random rand = new Random();
				t.x[i] = rand.nextDouble()*(f.max(i)-f.min(i)) + f.min(i);
			}
		}
		
	}

	private static Solution cross() {
		Random rand = new Random();
		
		Solution r = new Solution(f),p1 = null,p2 = null;
		
		double c1 = sumafit*rand.nextDouble(),c2 = sumafit*rand.nextDouble();
		
		for(Solution s:prev_pop) {
			c1 -= s.fit;
			if(Double.compare(c1, 0.0)<0) {
				p1 = s;
				break;
			}
		}
		
		for(Solution s:prev_pop) {
			c2 -= s.fit;
			if(Double.compare(c2, 0.0)<0) {
				p2 = s;
				break;
			}
		}
		
		for(int i=0;i<p1.x.length;i++) {
			if(rand.nextBoolean()) {
				r.x[i] = p1.x[i];
			}else {
				r.x[i] = p2.x[i];
			}
		}
		
		int l= rand.nextInt(r.x.length);
		
		r.x[l] += mutation*rand.nextDouble()-mutation/2;
		
		if(Double.compare(r.x[l],f.min(l))<0 || Double.compare(r.x[l],f.max(l))>0) {
			r.x[l] = rand.nextDouble()*(f.max(l)-f.min(l)) +f.min(l);
		}
		
		return r;
	}

	private static void set_fit() {
		double fit = fronts.get(0).size();
		double fitmin = Double.MAX_VALUE;
		sumafit = 0;
		
		for(ArrayList<Solution> fronta:fronts) {
			for(Solution s:fronta) {
				s.fit = fit / sh(s);
				if(Double.compare(s.fit, fitmin)>0) {
					fitmin = s.fit;
				}
				sumafit += s.fit;
			}
			fit = fitmin*0.999;
		}
	}

	private static double sh(Solution s) {
		double r=0;
		
		for(Solution p:prev_pop) {
			double d = calc_d(p,s);
			if(Double.compare(d,sigmashare)<0) {
				r += 1 - Math.pow(d/sigmashare, alpha);
			}
		}
		return r;
	}

	private static double calc_d(Solution p, Solution s) {
		double r = 0;
		if(decision_space) {
			double minx=Double.MAX_VALUE,maxx=Double.MIN_VALUE;
			for(int i=0;i<p.x.length;i++) {
				r += Math.pow(p.x[i]-s.x[i], 2);
				if (minx<p.x[i]) minx = p.x[i];
				if (minx<s.x[i]) minx = s.x[i];
				if (maxx>p.x[i]) maxx = p.x[i];
				if (maxx>s.x[i]) maxx = s.x[i];
			}
			r /= Math.pow(maxx-minx, 2);
		}else {
			double minx=Double.MAX_VALUE,maxx=Double.MIN_VALUE;
			for(int i=0;i<p.eval.length;i++) {
				r += Math.pow(p.eval[i]-s.eval[i], 2);
				if (minx<p.eval[i]) minx = p.eval[i];
				if (minx<s.eval[i]) minx = s.eval[i];
				if (maxx>p.eval[i]) maxx = p.eval[i];
				if (maxx>s.eval[i]) maxx = s.eval[i];
			}
			r /= Math.pow(maxx-minx, 2);
		}
		return r;
	}

	private static void non_dominated_sort() {

		fronts = new ArrayList<ArrayList<Solution>>();
		
		fronts.add(new ArrayList<Solution>());
		
		for(Solution p:population) {
			
			p.domination.clear();
			p.dominated=0;
			
			for(Solution q:population) {
				
				if(p!=q) {
					if(dominates(p,q)) {
						p.domination.add(q);
					}else if(dominates(q,p)) {
						p.dominated++;
					}
				}
			}
			
			if(p.dominated==0) {
				fronts.get(0).add(p);
			}
			
		}
		
		for(Solution s:fronts.get(0)) {
			population.remove(s);
		}
		
		while (population.size()>0) {
			fronts.add(new ArrayList<Solution>());
			for(Solution p:fronts.get(fronts.size()-2)) {
				for(Solution q:p.domination) {
					q.dominated--;
					if(q.dominated==0) {
						fronts.get(fronts.size()-1).add(q);
						population.remove(population.lastIndexOf(q));
					}
				}
			}
		}
		
	}

	private static boolean dominates(Solution p, Solution q) {
		for(int i=0;i<p.eval.length;i++) {
			if(p.eval[i] > q.eval[i]) return false;
		}
		return true;
	}
	

}
