package hr.fer.zemris.optjava.dz13;

public class Evaluator {

	private boolean[][] map = null;
	private int number_of_moves;
	
	public Evaluator(boolean[][] map,int number_of_moves) {
		
		this.map = new boolean[map.length][map[0].length];
		this.number_of_moves = number_of_moves;
		
		for(int i = 0; i < map.length ; i++) {
			for(int j = 0;j < map[0].length;j++ ) {
				this.map[i][j] = map[i][j];
			}
		}
	}
	
	public double evaluate(Node root) {
		Ant ant = new Ant(map,number_of_moves);
		
		if ( root == null ) {
			return -1;
		}
		
		while(ant.number_of_moves > 0) {
			root.run(ant);
		}
		
		return ant.number_of_collected_food;
	}

}
