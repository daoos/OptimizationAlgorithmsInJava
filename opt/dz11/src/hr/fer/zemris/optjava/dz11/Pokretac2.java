package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.IOException;
import java.util.Queue;
import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.EVOThread;

public class Pokretac2 {

	/**
	 * @param args
	 */

	static int numberOfRectangles;
	static int populationSize;
	static int maximumNumberOfGenerations;
	//static //double stop_fit, best_fit;
	//File param;
	static String aproximationSavePath;
	static String parametersFilePath;
	//static IntGASolution[] POP, next_gen;
	static GrayScaleImage image;
	static EVOThread[] workers;
	static int n, n_size = 0;
	static Queue<GASolution<Integer>> in;
	static Queue<GASolution<Integer>> out;
	static double suma, sc;
	static double minimumFitness, maximumFitness;
	//static IntGASolution best;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		if (args.length != 7)
			throw new IllegalArgumentException("Program prima 7 argumenta!");

		try {
			image = GrayScaleImage.load(new File(args[0]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		numberOfRectangles = Integer.parseInt(args[1]);
		populationSize = Integer.parseInt(args[2]);
		//POP = new IntGASolution[pop_size];
		//next_gen = new IntGASolution[pop_size];
		maximumNumberOfGenerations = Integer.parseInt(args[3]);
		minimumFitness = Double.parseDouble(args[4]);
		String parametersFilePath = args[5];
		aproximationSavePath = args[6];

		//load_parameters(new File(parametersFilePath));
		
		GeneticAlgo2 algo = new GeneticAlgo2(populationSize,numberOfRectangles,maximumNumberOfGenerations,
				minimumFitness,parametersFilePath,aproximationSavePath,image);
		
		EVOThread thread = new EVOThread(algo);
		thread.start();
		
	}

}
