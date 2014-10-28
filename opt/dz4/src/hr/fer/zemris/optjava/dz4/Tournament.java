package hr.fer.zemris.optjava.dz4;

import java.util.Random;

public class Tournament implements ISelection {

	private int numberOfTurns;
	private boolean b=true;
	
	public Tournament(int parseInt) {
		numberOfTurns = parseInt;
	}

	@Override
	public DoubleArraySolution Selection(DoubleArraySolution[] d,boolean b) {
		DoubleArraySolution tmp=d[0];
		Random rand=new Random();
		double c = rand.nextDouble();
		for(int i=0;i<numberOfTurns-1;i++) {
			for (DoubleArraySolution p:d) {
				c-=p.fit;
				if(c<0) {
					if(p.fit>tmp.fit) {
						tmp = p;
					}
					break;
				}
			}
		}
		return tmp;
	}

	@Override
	public DoubleArraySolution Selection(DoubleArraySolution[] d) {
		DoubleArraySolution p,tmp;
		RouletteWheel rou = new RouletteWheel();
		p = rou.Selection(d);
		for(int i=0;i<numberOfTurns-1;i++) {
			tmp = rou.Selection(d);
			if(tmp.fit>p.fit&&b) p = tmp;
			else if(tmp.fit<p.fit) p=tmp; 
		}
		return p;
	}

}
