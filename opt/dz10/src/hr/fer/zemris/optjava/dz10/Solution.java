package hr.fer.zemris.optjava.dz10;

import java.util.ArrayList;
import java.util.Random;

public class Solution {

	double fit = 0;
	int size;
	double[] x;
	int dominated=0;
	MOOProblem f=null;
	double[] eval=null;
	ArrayList<Solution> domination=null;
	
	public Solution(MOOProblem fja) {
		domination = new ArrayList<Solution>();
		f = fja;
		x = new double[f.getDimension()];
		Random rand = new Random();
		for(int i=0;i<f.getDimension();i++) {
			x[i] = (f.max(i)-f.min(i))*rand.nextDouble()+f.min(i);
		}
	}

	public boolean equals(Object obj) {
		return x.equals(((Solution)obj).x);
	}
	
	public void setEval(){
		if(eval==null) eval = new double[f.getNumberOfObjectives()];
		f.evaluateSolution(x, eval);
	}
	
	public Solution clone() {
		return this;
	}
	
	public String toString() {
		String st = "";
		for(int i = 0;i<eval.length;i++) {
			st += eval[i] + " ";
		}
		
		return st;
	}

}
