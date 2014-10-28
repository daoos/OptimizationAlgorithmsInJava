package hr.fer.zemris.optjava.dz13;

import java.util.Random;

public class GAProgramming implements Runnable {

	public int index;
	public Node best;
	public Node[] population=null,next_gen=null;
	public boolean[][] map = null;
	private int max_number_of_generations,population_size,max_number_of_moves = 600,max_depth = 3,
			max_depth_of_children=9;
	@SuppressWarnings("unused")
	private double cross_chance = 0.84d,mutation_chance = 0.15d,reproduction_chance = 0.01d,fit_sum;
	public double min_fitness,best_fit;
	private double[] fitness=null,next_gen_fitness=null;
	private Random rand = null;
	private Evaluator evaluator=null;
	private TournamentSelection selection=null;
	
	public GAProgramming(boolean[][] map, int max_number_of_generations,
			int population_size, double min_fitness) {
		this.map = map;
		this.max_number_of_generations = max_number_of_generations;
		this.population_size = population_size;
		this.min_fitness = min_fitness;
		this.rand = new Random();
		this.evaluator = new Evaluator(map, max_number_of_moves);
	}
	
	@Override
	public void run(){
		int iter = max_number_of_generations;
		
		while (Double.compare(evaluator.evaluate(best), 0) < 0) {
			init_population();
			eval();
		}
		
		while( Double.compare(evaluator.evaluate(best), min_fitness) < 0 && iter > 0) {
			fill_next_gen();
			population = next_gen;
			fitness = next_gen_fitness;
			System.out.println((max_number_of_generations-iter)+". iteracija: best "+evaluator.evaluate(best));//+" "+best.get_depth()+" "+mutation_chance);
			iter--;
		}
	}

	private void fill_next_gen() {
		selection  = new TournamentSelection(3,fitness);
		
		next_gen = new Node[population_size];
		
		next_gen[0] = best.copy(null);
		next_gen_fitness = new double[population_size];
		next_gen_fitness[0] = evaluator.evaluate(next_gen[0]);
		
		fit_sum = next_gen_fitness[0];
		for(int i= 1;i<population_size;i++) {
			
			Node t = population[selection.select()];
			
			//System.out.println(evaluator.evaluate(t));
			
			double chance = rand.nextDouble();
			
			if(chance < reproduction_chance) {
				
				next_gen[i] = t.copy(null);
			
			}else if(chance < mutation_chance+reproduction_chance) {
			
				next_gen[i] = mutation(t);
			
				if(next_gen[i] == null) {
					i--;
					continue;
				}
				
			}else {
			
				next_gen[i] = cross();
			
			}
			
			next_gen_fitness[i] = evaluator.evaluate(next_gen[i]);
			if(next_gen_fitness[i] == evaluator.evaluate(t)) {
				next_gen_fitness[i] *= 0.9;
			}
			
			if (best_fit < next_gen_fitness[i]) {
				best = next_gen[i].copy(null);
				best_fit = next_gen_fitness[i];
				mutation_chance = 0.15;
			}else {
				mutation_chance +=0.00001;
			}
			
			fit_sum += next_gen_fitness[i];
		}
		
	}
	
	private Node cross() {
		
		Node parent1,parent2;
		
		parent1 = population[rand.nextInt(population_size)].copy(null);
		parent2 = population[selection.select()].copy(null);
		
		//System.out.println(evaluator.evaluate(parent1)+" "+evaluator.evaluate(parent2));
		
		int r1 = parent1.get_number_of_children();
		int r2 = parent2.get_number_of_children();
		
		Node place1 = parent1.get_Node(rand.nextInt(r1));
		Node place2 = parent2.get_Node(rand.nextInt(r2));
		
		if(place1.children!=null) {
			place1.children[rand.nextInt(place1.children.length)] = place2.copy(place1);
		}
		//System.out.println(evaluator.evaluate(parent1));
		
		if(parent1.get_depth() > max_depth_of_children ) {
			return cross();
		}
		
		return parent1;
	}

	private Node mutation(Node t1) {
		Node t = t1.copy(null);
		int size = t.get_number_of_children();
		int v =rand.nextInt(size);
		Node r = t.get_Node(v);

		if( r.children != null) {
			for (int i=0;i<r.children.length;i++) {
				r.children[i] = node_grow(r,3);
			}
		}
		
		if(t.get_depth() > max_depth_of_children) {
			return mutation(t1);
		}
		//System.out.println(evaluator.evaluate(t1)+" "+evaluator.evaluate(t));
		return t;
	}

	private void eval() {
		
		best_fit = evaluator.evaluate(best);
		
		fit_sum = 0;
		index = 0;
		
		for(int i = 0;i < population_size;i++) {
			
			fitness[i] = evaluator.evaluate(population[i]);
			
			fit_sum += fitness[i];
			
			if( Double.compare(best_fit, fitness[i]) < 0 ) {
				best_fit = fitness[i];
				best = population[i].copy(null);
				index = i;
			}
			
		}
		
		//System.out.println(best_fit+" "+fit_sum+" "+evaluator.evaluate(best));
		
	}

	private void init_population() {
		
		population = new Node[population_size];
		fitness = new double[population_size];
		
		for(int i = 0 ; i < max_depth - 1;i++) {
			for(int j =  0; j < population_size/(max_depth-1);j++) {
				if( j % 2 == 1) {
					population[j+i*population_size/(max_depth-1)] = generate_node_full(i+1, i+1);
				}else{
					population[j+i*population_size/(max_depth-1)] = generate_node_grow(i+1);
				}
			}
		}
		
		for(int i=0;i<population_size;i++) {
			if(population[i] == null) {
				population[i] = generate_node_full(max_depth, max_depth);
			}
		}
		
	}

	private Node generate_node_grow(int depth) {
		Node root = null;
		
		int z = rand.nextInt(3);
		
		if(z == 0) {
			Node[] ch = new Node[2];
			root = new IfFoodAhead(null,ch);
			ch[0] = node_grow(root,depth -1);
			ch[1] = node_grow(root,depth -1);
			root.children = ch;
		}else if(z == 1) {
			Node[] ch = new Node[2];
			root = new Prog2(null,ch);
			ch[0] = node_grow(root,depth -1);
			ch[1] = node_grow(root,depth -1);
			root.children = ch;
		}else {
			Node[] ch = new Node[3];
			root = new Prog3(null,ch);
			ch[0] = node_grow(root,depth -1);
			ch[1] = node_grow(root,depth -1);
			ch[2] = node_grow(root,depth -1);
			root.children = ch;
		}
		return root;
	}
	
	private Node node_grow(Node parent,int depth) {
		Node r = null;
		int z;
		if(depth > 0) {
			z = rand.nextInt(6);
		}else {
			z = rand.nextInt(3) + 3;
		}
		if(z == 0) {
			Node[] ch = new Node[2];
			r = new IfFoodAhead(parent,null);
			ch[0] = node_grow(r,depth -1);
			ch[1] = node_grow(r,depth -1);
			r.children = ch;
		}else if(z == 1) {
			Node[] ch = new Node[2];
			r = new Prog2(parent,ch);
			ch[0] = node_grow(r,depth -1);
			ch[1] = node_grow(r,depth -1);
			r.children = ch;
		}else if (z == 2) {
			Node[] ch = new Node[3];
			r = new Prog3(parent,ch);
			ch[0] = node_grow(r,depth -1);
			ch[1] = node_grow(r,depth -1);
			ch[2] = node_grow(r,depth -1);
			r.children = ch;
		}else if ( z == 3) {
			r = new Left(parent,null);
		}else if ( z == 4) {
			r = new Right(parent,null);
		}else if ( z == 5) {
			r = new Mov(parent,null);
		}
		
		return r;
	}
	
	private Node generate_node_full(int depth,int max) {
		Node root = null;
		
		int z = rand.nextInt(3);
		
		if(z == 0) {
			Node[] ch = new Node[2];
			root = new IfFoodAhead(null,ch);
			ch[0] = node_full(root,depth -1,max);
			ch[1] = node_full(root,depth -1,max);
			root.children = ch;
		}else if(z == 1) {
			Node[] ch = new Node[2];
			root = new Prog2(null,ch);
			ch[0] = node_full(root,depth -1,max);
			ch[1] = node_full(root,depth -1,max);
			root.children = ch;
		}else {
			Node[] ch = new Node[3];
			root = new Prog3(null,ch);
			ch[0] = node_full(root,depth -1,max);
			ch[1] = node_full(root,depth -1,max);
			ch[2] = node_full(root,depth -1,max);
			root.children = ch;
		}
		
		return root;
	}

	private Node node_full(Node parent,int depth,int max) {
		Node r = null;
		
		int z;
		
		if(depth < max/2 && depth != 0) {
			z = rand.nextInt(6);
		}else if (depth > 0) {
			z = rand.nextInt(3);
		}else {
			z = rand.nextInt(3) + 3;
		}
		if(z == 0) {
			Node[] ch = new Node[2];
			r = new IfFoodAhead(parent,null);
			ch[0] = node_full(r,depth -1,max);
			ch[1] = node_full(r,depth -1,max);
			r.children = ch;
		}else if(z == 1) {
			Node[] ch = new Node[2];
			r = new Prog2(parent,ch);
			ch[0] = node_full(r,depth -1,max);
			ch[1] = node_full(r,depth -1,max);
			r.children = ch;
		}else if (z == 2) {
			Node[] ch = new Node[3];
			r = new Prog3(parent,ch);
			ch[0] = node_full(r,depth -1,max);
			ch[1] = node_full(r,depth -1,max);
			ch[2] = node_full(r,depth -1,max);
			r.children = ch;
		}else if ( z == 3 ) {
			r = new Left(parent,null);
		}else if ( z == 4 ) {
			r = new Right(parent,null);
		}else if ( z == 5 ) {
			r = new Mov(parent,null);
		}
		
		return r;
	}
	
}
