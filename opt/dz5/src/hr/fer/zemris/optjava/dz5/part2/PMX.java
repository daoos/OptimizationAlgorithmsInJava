package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Random;

public class PMX implements ICrossP {

	private Random rand=null;
	
	public PMX(Random r) {
		rand = r;
	}

	@Override
	public PermSolution[] cross(PermSolution p1, PermSolution p2) {
		int p=rand.nextInt(p1.p.length),k=rand.nextInt(p1.p.length);
		PermSolution[] children = new PermSolution[2];
		children[0] = new PermSolution(p1.p.length);
		children[1] = new PermSolution(p1.p.length);
		if(p>k) {
			int tmp=p;
			p=k;
			k=tmp;
		}
		ArrayList<Integer> p1seg = new ArrayList<Integer>();
		ArrayList<Integer> p2seg = new ArrayList<Integer>();
		ArrayList<Integer> ch1seg = new ArrayList<Integer>();
		ArrayList<Integer> ch2seg = new ArrayList<Integer>();
		
		for(int i=p;i<=k;i++) {
			children[0].p[i] = p1.p[i];
			p1seg.add(p1.p[i]);
			children[1].p[i] = p2.p[i];
			p2seg.add(p2.p[i]);
		}
		
		for(int i=k;i<p1.p.length;i++) {
			if(!p1seg.contains(p2.p[i])) {
				ch1seg.add(p2.p[i]);
			}
			if(!p2seg.contains(p1.p[i])) {
				ch2seg.add(p1.p[i]);
			}
		}
		
		for(int i=0;i<k;i++) {
			if(!p1seg.contains(p2.p[i])) {
				ch1seg.add(p2.p[i]);
			}
			if(!p2seg.contains(p1.p[i])) {
				ch2seg.add(p1.p[i]);
			}
		}
		
		for(int i=k+1;i<p1.p.length;i++) {
			children[0].p[i] = ch1seg.get(0);
			ch1seg.remove(0);
			children[1].p[i] = ch2seg.get(0);
			ch2seg.remove(0);
		}
		
		return children;
	}

}
