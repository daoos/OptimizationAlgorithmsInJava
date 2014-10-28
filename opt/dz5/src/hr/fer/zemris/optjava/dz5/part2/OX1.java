package hr.fer.zemris.optjava.dz5.part2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class OX1 implements ICrossP {

	private Random rand=null;
	
	public OX1(Random r) {
		rand = r ;
	}

	@Override
	public PermSolution[] cross(PermSolution p1, PermSolution p2) {
		int p=rand.nextInt(p1.p.length),k=rand.nextInt(p1.p.length);
		if(p>k) {
			int tmp =p;
			p =k;
			k = tmp;
		}
		PermSolution[] ch = new PermSolution[2];
		ch[0] = new PermSolution(p1.p.length);
		ch[1] = new PermSolution(p1.p.length);
		
		Set<Integer> ch1 = new HashSet<Integer>();
		Set<Integer> ch2 = new HashSet<Integer>();
		
		for(int i=p;i<=k;i++) {
			ch[0].p[i] = p1.p[i];
			ch1.add(p1.p[i]);
			ch[1].p[i] = p2.p[i];
			ch2.add(p2.p[i]);
		}
		int c1 = k+1,c2=k+1;
		for(int i=k+1;i<p1.p.length;i++) {
			if(!ch1.contains(p2.p[i])) {
				ch[0].p[c1]=p2.p[i];
				c1++;
				ch1.add(p2.p[i]);
			}
			if(!ch2.contains(p1.p[i])) {
				ch[1].p[c2]=p1.p[i];
				c2++;
				ch2.add(p1.p[i]);
			}
		}
		if(c1==p1.p.length) c1 =0;
		if(c2==p1.p.length) c2 =0;
		
		for(int i=0;i<p1.p.length&&ch1.size()<=p1.p.length&&ch2.size()<=p1.p.length;i++) {
			if(c1==p1.p.length) c1 =0;
			if(c2==p1.p.length) c2 =0;
			if(!ch1.contains(p2.p[i])) {
				ch[0].p[c1]=p2.p[i];
				c1++;
				ch1.add(p2.p[i]);
			}
			if(!ch2.contains(p1.p[i])) {
				ch[1].p[c2]=p1.p[i];
				c2++;
				ch2.add(p1.p[i]);
			}
		}
		
		return ch;
	}

}
