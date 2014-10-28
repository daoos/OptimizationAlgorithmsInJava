package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

public class SwapMutation implements IMutation {

	Random rand=null;
	
	public SwapMutation(Random r) {
		rand = r;
	}

	@Override
	public void mutate(PermSolution p) {
		int r = rand.nextInt(p.p.length),t=rand.nextInt(p.p.length);
		int tmp = p.p[r];
		p.p[r] = p.p[t];
		p.p[t] = tmp;
	}

}
