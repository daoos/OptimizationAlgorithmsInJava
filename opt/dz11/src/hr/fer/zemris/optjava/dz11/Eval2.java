package hr.fer.zemris.optjava.dz11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.RNG;
import hr.fer.zemris.optjava.rng.provimpl.ThreadBoundRNGProvider;
import hr.fer.zemris.optjava.rng.provimpl.ThreadLocalRNGProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class Eval2<T extends Number> implements Runnable {

	ConcurrentLinkedQueue<GASolution<T>> population,children;
	IGAEvaluator<T> evaluator;
	int numberOfChildren;
	private int numberOfRows;
	Mutation mutation;
	GASolution<T> best = null;
	public boolean b = true;
	Integer[] pass;
	public Eval2(IGAEvaluator<T> e, ConcurrentLinkedQueue<GASolution<T>> population,
				int numberOfChildren,int numberOfRows,Mutation mutation,ConcurrentLinkedQueue<GASolution<T>> children,Integer[] pass2) {
		super();
		this.population = population;
		this.children = children;
		this.numberOfChildren = numberOfChildren;
		this.numberOfRows = numberOfRows;
		this.mutation = mutation;
		this.pass = pass2;
		evaluator = e;
	}
	@Override
	public void run() {

		int l= 0;
		GASolution<T> t;
		Crossing cross = new Crossing();
		while (true) {
			//System.out.println("Thread started");
			
			t = population.peek();
			//System.out.println("Done waiting");
			//if(t == null) continue;
			
			if( t.data == null) {
				break;
			}
			
			Object[] a = (population.toArray());
			Selection select = new Selection(a, numberOfRows);
			
			for(int i=0;i<numberOfChildren;i++) {
				t = (GASolution<T>) cross.getChild(select.getUnit(), select.getUnit());
				//eval
				evaluator.evaluate(t);
				if(i == 0) {
					best = t;
				}else if(t.fitness < best.fitness) {
					best = t;
				}
					children.add(t);
				}
				System.out.println(best.fitness);

				synchronized (children) {
					children.notify();
				}
				
				synchronized (population) {
					try {
						population.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
				
			}
//		System.out.println("ThreadDead");
		//System.out.println(l);
	}
}
