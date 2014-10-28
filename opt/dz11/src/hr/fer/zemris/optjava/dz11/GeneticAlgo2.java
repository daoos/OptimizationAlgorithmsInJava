package hr.fer.zemris.optjava.dz11;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.omg.CORBA.INTERNAL;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.IGAEvaluatorProvider;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;


public class GeneticAlgo2 implements Runnable {

	private int populationSize = 0;
	private int numberOfRectangles = 0;
	private int maxIter = 0;
	private double minFit = 0;
	private String parametersFilePath = null;
	private String aproximationSavePath = null;
	private ConcurrentLinkedQueue<GASolution<Number>> population=null;
	private ConcurrentLinkedQueue<GASolution<Number>> children=new ConcurrentLinkedQueue<>();
	private GrayScaleImage image = null;
	private ConcurrentLinkedQueue<GASolution<Number>> in,out = null;
	private EVOThread[] workers = null;
	
	
	
	public GeneticAlgo2(int pop_size, int broj_kvadrata, int max_iter, double min_fit, 
							String parametersFilePath, String aprox_save_path, GrayScaleImage image) {
		this.populationSize = pop_size;
		this.numberOfRectangles = broj_kvadrata;
		this.maxIter = max_iter;
		this.minFit = min_fit;
		this.parametersFilePath = parametersFilePath;
		this.aproximationSavePath = aprox_save_path;
		this.image = image;
	}
	
	private Selection selection = null;
	private double bestUnitFitness = Double.MAX_VALUE;
	public boolean state = true;
	private GASolution<Number> best = null;
	private Evaluator<Number> evaluator;
	private Mutation[] mutations;
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		evaluator = new Evaluator<Number>(image);
		initPopulation();
		
		
		double range = mutations[0].range;
		
		for(int iter=0; iter<maxIter ;iter++) {
			
			for(int i = 0;i<mutations.length;i++) {
				mutations[i].range = 1+(int) ((int) range * Math.exp(-iter*4/(double)maxIter));
			}
			
			while(children.size() < populationSize) {
				synchronized (children) {
					try {
						children.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("Filling nextgen");
				if(best != null) {
					evaluator.evaluate(best);
					System.out.println("Current iteration "+iter+". best: "+best.fitness+" "+mutations[0].range);
				}
				
			for(EVOThread t :workers) {
				if(((Eval2)t.target).best != null && ((Eval2)t.target).best.fitness < best.fitness) {
					best = ((Eval2)t.target).best;
				}
			}
			
			population.clear();
			System.out.println(children.size());
			population.addAll(children);
			population.add(best);
			children.clear();
			
			for(EVOThread t:workers) {
				t.interrupt();
			}
		
		}
		
		image = evaluator.draw(best, null);
		try {
			image.save(new File(aproximationSavePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0;i<workers.length;i++) {
			GASolution<Number> t = new GASolution<>(5);
			t.data = null;
			population.add(t);
		}
		
		synchronized(population) {
			population.notifyAll();
		}
		
		
		
		
		
		
	}

	Integer[] pass={0};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initPopulation() {
		
		this.population = new ConcurrentLinkedQueue<GASolution<Number>>();
        
		//backgroundColor colorOfRectangle Coordinates of UpperLeftCorner LowerRightCorner
		int chromosomeLength = 1 + 5*numberOfRectangles;
		
		for(int i = 0;i<populationSize; i++) {

			GASolution<Number> t = new GASolution<Number>(chromosomeLength,true,
					image.getWidth(),image.getHeight());
		
			evaluator.evaluate(t);
			if(best == null) {
				best = t;
			}else if(t.fitness < best.fitness) {
				best = t;
			}
			
			population.add(t);
		}
		
		workers = new EVOThread[Runtime.getRuntime().availableProcessors()];
		
		mutations = new Mutation[workers.length];
		
		for(int i = 0;i<mutations.length;i++) {
			mutations[i] = new Mutation(parametersFilePath,
					population.peek().data.length,image.getWidth(),image.getHeight());
		}
		
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new EVOThread(new Eval2(evaluator, population, 
					populationSize/workers.length, 
					10, mutations[i],children,pass));
		}
		
		for(Thread t :workers) {
			t.start();
		}	
		
	}
}
