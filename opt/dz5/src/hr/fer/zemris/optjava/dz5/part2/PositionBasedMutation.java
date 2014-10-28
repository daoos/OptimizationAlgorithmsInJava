package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

public class PositionBasedMutation implements IMutation {

	private Random rand=null;
	
	public PositionBasedMutation(Random r) {
		rand =r;
	}

	@Override
	public void mutate(PermSolution p) {
		int el=rand.nextInt(p.p.length),poz=rand.nextInt(p.p.length);
		if(el<poz) {
			int tmp=el;
			el = poz;
			poz=tmp;
			tmp = p.p[el];
			p.p[el] = p.p[poz];
			p.p[poz] = tmp;
		}
		for(int i=poz+1;i<el;i++) {
			int tmp = p.p[i];
			p.p[i] = p.p[el-i+poz];
			p.p[el-i+poz] = tmp;
		}
	}

}
