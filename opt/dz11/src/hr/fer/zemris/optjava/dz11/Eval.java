package hr.fer.zemris.optjava.dz11;

import java.util.Queue;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.rng.RNG;

public class Eval<K extends GASolution<T>, T extends Number> implements Runnable {

	Queue<K> in;
	Queue<K> out;
	IGAEvaluator<T> evaluator;

	public Eval(IGAEvaluator<T> e, Queue<K> in2,
			Queue<K> out2) {
		in = in2;
		out = out2;
		evaluator = e;
	}

	@Override
	public void run() {
		int l= 0;
		K t;
		//System.out.println();
		while (true) {
			//l++;
			t = in.poll();
			if (t != null) {
				if (t.data == null) {
					break;
				}
				evaluator.evaluate(t);
				out.add(t);
				//l++;
			}else {
				synchronized(out) {
					out.notify();
				}
				synchronized(in) {
					try {
						in.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		//System.out.println(l);

	}

}
