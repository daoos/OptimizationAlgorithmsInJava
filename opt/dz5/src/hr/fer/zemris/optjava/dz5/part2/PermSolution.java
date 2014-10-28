package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

public class PermSolution {

	public int[] p;
	public long value;
	
	public PermSolution(int n) {
		p = new int[n];
		for(int i=0;i<n;i++) {
			p[i] = i+1;
		}
	}
	
	public void randomize(int n) {
		Random rand = new Random();
		
		while(n>1) {
			int r=rand.nextInt(p.length),t=rand.nextInt(p.length),tmp;
			
			tmp = p[r];
			p[r] = p[t];
			p[t] = tmp;
			
			n--;
		}
	}
	
	public int hashCode(){
		return p.hashCode();
	}

}
