package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

public class DisplacmentMutation implements IMutation {

	private Random rand=null;
	
	public DisplacmentMutation(Random r) {
		rand =r;
	}

	@Override
	public void mutate(PermSolution sol) {
		int p=rand.nextInt(sol.p.length),k=rand.nextInt(sol.p.length),u=rand.nextInt(sol.p.length);

		if(u>k) {
			int t = u;
			u = k;
			k = t;
		}
		if(p>k) {
			int t = p;
			p = k;
			k = t;
		}
		if(u>p) {
			int t = u;
			u = p;
			p = t;
		}
		
		for(int i=u;i<k-p;i++) {
			int t = sol.p[i];
			sol.p[i] = sol.p[p+(i-u)];
			sol.p[p+(i-u)] = t;
		}
	}

}
