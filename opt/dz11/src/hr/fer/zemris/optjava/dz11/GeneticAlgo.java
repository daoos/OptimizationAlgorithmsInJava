package hr.fer.zemris.optjava.dz11;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.IGAEvaluatorProvider;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.RNG;


public class GeneticAlgo implements Runnable {

	private int populationSize = 0;
	private int numberOfRectangles = 0;
	private int maxIter = 0;
	private double minFit = 0;
	private String parametersFilePath = null;
	private String aproximationSavePath = null;
	private GASolution<Number>[] population=null;
	private GrayScaleImage image = null;
	private ConcurrentLinkedQueue<GASolution<Number>> in,out = null;
	private EVOThread[] workers = null;
	
	public GeneticAlgo(int pop_size, int broj_kvadrata, int max_iter, double min_fit, 
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

	@Override
	public void run() {
		Evaluator evaluator = new Evaluator<Integer>(image);
		initPopulation();
		
		Mutation mutation = new Mutation(parametersFilePath, population[0].data.length, image.getWidth(), image.getHeight() );
		
		Crossing cross = new Crossing(); 
		
		double range = mutation.range;
		
		for(int i=0; i<maxIter ;i++) {
			eval();
			
		
			mutation.range = 1+(int) ((int) range * Math.exp(-i*4/(double)maxIter));
			if(best != null) {
				evaluator.evaluate(best);
				System.out.println("Current iteration "+i+". best: "+best.fitness+" "+mutation.range);
			}
			selection = new Selection(population,1);
			
			GASolution<Number>[] nextGeneration;
			
			{
				GASolution<Number> t = new GASolution<>(0);
				@SuppressWarnings("unchecked")
				final GASolution<Number>[] a = (GASolution<Number>[]) Array.newInstance(t.getClass(), populationSize);
		        nextGeneration = a;
			}
			
			for(int j = 0; j < nextGeneration.length; j++) {
				nextGeneration[j] = cross.getChild(selection.getUnit(),selection.getUnit());
				mutation.mutate(nextGeneration[j]);
			}
			
			population = nextGeneration;
			if(best != null)
			population[0] = best;
			
			in.clear();
			out.clear();
			
			//initPopulation();
			
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
			in.add(t);
		}
		
		synchronized(in) {
			in.notifyAll();
		}
		
		
		
		
		
		
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initPopulation() {
		
		{
		GASolution<Number> t = new GASolution<>(0);
		final GASolution<Number>[] a = (GASolution<Number>[]) Array.newInstance(t.getClass(), populationSize);
        this.population = a;
		}
        
		//backgroundColor colorOfRectangle Coordinates of UpperLeftCorner LowerRightCorner
		int chromosomeLength = 1 + 5*numberOfRectangles;

		in = new ConcurrentLinkedQueue<GASolution<Number>>();
		out = new ConcurrentLinkedQueue<GASolution<Number>>();

		
		for(int i = 0;i<populationSize; i++) {

			population[i] = new GASolution<Number>(chromosomeLength,true,
					image.getWidth(),image.getHeight());
		}
		
		workers = new EVOThread[Runtime.getRuntime().availableProcessors()];

		for (int i = 0; i < workers.length; i++) {
			workers[i] = new EVOThread((Runnable) new Eval(
					new Evaluator<Integer>(image), in, out));
		}
		
		for(EVOThread t :workers) {
			t.start();
		}
		
		//for(GASolution<Number> p:population) {
		//	System.out.println(p.fitness);
		//}
		
		
	}

	
	
	private void eval() {
		//bestUnitFitness = Double.MAX_VALUE;
		for(GASolution<Number> t:population) {
			in.add(t);
		}
		
		synchronized(in) {
			in.notifyAll();
		}
		
		int index=0;
		while(out.size() != populationSize) {
			synchronized(out) {
				try {
					out.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(GASolution<Number> t:out){
			//if(t!=null){
				if(t.fitness < bestUnitFitness) {
					bestUnitFitness = t.fitness;
					best = t.duplicate();
				} 
				//System.out.println(t.fitness);
				t.fitness = 1 / t.fitness;
				population[index++] = t;
				//System.out.println(t.fitness);
			//}
			
			if(index == population.length) {
				break;
			}
		}
		
	}

}
