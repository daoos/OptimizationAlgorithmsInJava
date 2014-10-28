package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Pokretac1 {

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
		
		GeneticAlgo algo = new GeneticAlgo(populationSize,numberOfRectangles,maximumNumberOfGenerations,
				minimumFitness,parametersFilePath,aproximationSavePath,image);
		
		EVOThread thread = new EVOThread(algo);
		thread.start();
		
	}
		/*n = 1 + broj_kvadrata * 5;

		in = new ConcurrentLinkedQueue<IntGASolution>();
		out = new ConcurrentLinkedQueue<IntGASolution>();

		workers = new EVOThread[Runtime.getRuntime().availableProcessors()];

		for (int i = 0; i < workers.length; i++) {
			workers[i] = new EVOThread((Runnable) new Eval(
					new Evaluator(image), in, out));
		}

		for (int i = 0; i < POP.length; i++) {
			POP[i] = new IntGASolution(get_field(n));
		}

		GrayScaleImage z = null;

		best = POP[0];
		for (int i = 0; i < max_iter; i++) {
			eval();
			System.out.println(best.fitness);
			n_size = 0;
			while (n_size < pop_size) {
				selection();
			}
			Evaluator r = new Evaluator(image);
			z = r.draw(best, z);
			try {
				z.save(new File(aprox_save_path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			POP = next_gen;
			n_size = 0;
		}

	}

	static double mutation = 0.1d;
	static int range_of_mutation = 13;

	private static void load_parameters(File param2) {
		Scanner scan = null;
		try {
			scan = new Scanner(param2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean b = scan.hasNext();
		// mutation = scan.nextDouble();
		// range_of_mutation = scan.nextInt();

		scan.close();
	}

	private static void selection() {
		IRNG rng = RNG.getRNG();
		double chance = rng.nextDouble() *(max_fit-min_fit)+(suma - min_fit*pop_size);
		IntGASolution p1 = POP[0], p2 = POP[0];
		for (int i = 0; i < pop_size; i++) {
			chance -= POP[i].fitness;
			if (chance < 0 || i == pop_size - 1) {
				p1 = POP[i];
			}
		}
		chance = rng.nextDouble() * (suma - min_fit * pop_size);
		for (int i = 0; i < pop_size; i++) {
			chance -= POP[i].fitness;
			if (chance < 0 || i == pop_size - 1) {
				p2 = POP[i];
			}
		}
		cross(p1, p2);
	}

	private static void cross(IntGASolution p1, IntGASolution p2) {
		int[] c = new int[p1.data.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = (p1.data[i] + p2.data[i]) / 2;
		}

		IntGASolution ch = new IntGASolution(c);
		next_gen[n_size++] = ch;
		mutate(ch);

	}

	private static void mutate(IntGASolution ch) {
		int number_of_mutations = (int) mutation * n;
		TreeSet<Integer> set = new TreeSet<>();
		for (int i = 0; i < number_of_mutations; i++) {
			int poz = rand.nextInt(0, n);
			if (!set.contains(poz)) {
				set.add(poz);
				ch.data[poz] += rand.nextInt(-range_of_mutation,
						range_of_mutation);
				if (poz == 0) {
					if (ch.data[poz] < 0 || ch.data[poz] > 255) {
						ch.data[poz] = rand.nextInt(0, 255);
					}
				} else if ((poz - 1) % 5 == 0) {
					if (!(ch.data[poz] > 0 && ch.data[poz] < image.getWidth())) {
						ch.data[poz] = rand.nextInt(0, image.getWidth()
								- ch.data[poz + 2]);
					}
				} else if ((poz - 1) % 5 == 1) {
					if (!(ch.data[poz] > 0 && ch.data[poz] < image.getHeight())) {
						ch.data[poz] = rand.nextInt(0, image.getHeight()
								- ch.data[poz + 2]);
					}
				} else if ((poz - 1) % 5 == 2) {
					if (ch.data[poz] > image.getWidth() - ch.data[poz - 2]) {
						ch.data[poz] = rand.nextInt(0, image.getWidth()
								- ch.data[poz - 2]);
					}
				} else if ((poz - 1) % 5 == 3) {
					if (ch.data[poz] > image.getHeight() - ch.data[poz - 2]) {
						ch.data[poz] = rand.nextInt(0, image.getHeight()
								- ch.data[poz - 2]);
					}
				} else if ((poz - 1) % 5 == 4) {
					if (ch.data[poz] < 0 || ch.data[poz] > 255) {
						ch.data[poz] = rand.nextInt(0, 255);
					}
				}
			} else {
				i--;
			}
		}

	}

	@SuppressWarnings("deprecation")
	private static void eval() {
		boolean v = true;

		for (IntGASolution t : POP) {
			in.add(t);
		}

		for (int i = 0; i < workers.length; i++) {
			Evaluator k = new Evaluator(image);
			workers[i] = new EVOThread(new Eval(k, in, out));
		}

		for (EVOThread h : workers) {
			h.run();
		}

		int[] j = new int[n];
		j[0] = 300;

		IntGASolution h = new IntGASolution(j);

		for (int i = 0; i < workers.length; i++) {
			in.add(h);
		}

		int index = 0;
		IntGASolution u;
		suma = 0.0d;
		while (index < pop_size) {
			u = out.poll();
			if (u != null) {
				u.fitness = Math.pow(-u.fitness, -1);
				POP[index++] = u;
				suma += u.fitness;
				if (u.fitness > max_fit) {
					max_fit = u.fitness;
					best = u;
				}
				if (u.fitness < min_fit) {
					min_fit = u.fitness;
				}
			}
		}
		in.clear();
		out.clear();
	}

	private static int[] get_field(int n) {
		int[] field = new int[n];
		field[0] = rand.nextInt(0, 255);
		for (int i = 1; i < n; i += 5) {
			field[i] = rand.nextInt(0, image.getWidth() - 1);
			field[i + 1] = rand.nextInt(0, image.getHeight() - 1);
			field[i + 2] = rand.nextInt(0, image.getWidth() - field[i] - 1);
			field[i + 3] = rand
					.nextInt(0, image.getHeight() - field[i + 1] - 1);
			field[i + 4] = rand.nextInt(0, 255);
		}
		return field;
	}*/

}
