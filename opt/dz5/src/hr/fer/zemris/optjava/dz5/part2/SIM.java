package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

public class SIM implements IMutation {

	private Random rand=null;
	
	public SIM(Random r) {
		rand =r;
	}

	@Override
	public void mutate(PermSolution p) {
		int poc=rand.nextInt(p.p.length),k=rand.nextInt(p.p.length);
		if(poc>k) {
			int tmp=k;
			k=poc;
			poc = tmp;
		}
		for(int i=poc;i<k;i++) {
			int tmp = p.p[i];
			p.p[i] = p.p[k-i+poc];
			p.p[k-i+poc] = tmp;
		}

	}

}
