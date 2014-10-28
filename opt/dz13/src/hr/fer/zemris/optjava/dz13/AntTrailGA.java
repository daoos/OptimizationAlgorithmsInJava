package hr.fer.zemris.optjava.dz13;

import java.io.FileNotFoundException;

public class AntTrailGA {

	/**
	 * @param args
	 */
	
	static boolean[][] map= null;
	static String map_path = null;
	static int max_number_of_generations;
	static int population_size;
	static double min_fitness;
	static String best_unit_save_path=null;
	static Node best=null;
	
	public static void main(String[] args) throws FileNotFoundException {
		/*if(args.length != 5) {
			throw new IllegalArgumentException("map_path\n" +
					"max_number_of_generations\n" +
					"population_size\n" +
					"min_fitness\n" +
					"best_unit_save_path");
		}*/
		
		map_path = "res/13-SantaFeAntTrail.txt";
		max_number_of_generations = 100;
		population_size = 400;
		min_fitness = 90.0f;
		//best_unit_save_path = args[4];

		map = BooleanMatrixParser.parse(map_path);
		
		GAProgramming learn = new GAProgramming(map,max_number_of_generations,population_size,min_fitness);
		learn.run();
		//System.out.println(evaluator.evaluate(learn.best));
		@SuppressWarnings("unused")
		AntTrailVisual frame = new AntTrailVisual(learn);
		//NodeWriter.save_best(learn.best,best_unit_save_path);
		
	}


}
