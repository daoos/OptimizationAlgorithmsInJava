package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<T> {
	
	@Override
	public void run(IFunction f,boolean min,T s,ITempSchedule plan,INeighborhood<T> susjedstvo,IDecoder<T> dekoder) {
		double temperatura =plan.getNextTemperature();
		long k =0;
		double x = 0.65,a1=0.007;
		Random rand = new Random();
		while (temperatura>0) {
			double p = a1*Math.pow(x,k/1000);
			k++;
			int h = (int)Math.pow(100.8,k/10000);
			for(int i=0;i<h;i++) {
				double f1 = f.valueAt(dekoder.decode(s));
				T s_ = susjedstvo.randomNeighbor(s);
				double f2 = f.valueAt(dekoder.decode(s_));
				double delta = f2 - f1;
				if(!min){
					delta *= -1;
				}
				if(delta < 0) {
					s = s_;
				}else{
					if(Double.compare(rand.nextDouble(), p)<0){
						s = s_;
					}
				}
			}
			temperatura = plan.getNextTemperature();
			System.out.println(f.valueAt(dekoder.decode(s)));
		}
		double[] rez = dekoder.decode(s);
		for(int i =0;i<rez.length;i++) {
			System.out.print(rez[i]+" ");
		}
		System.out.println();
		System.out.println(f.valueAt(rez));
	}

	
	
}
