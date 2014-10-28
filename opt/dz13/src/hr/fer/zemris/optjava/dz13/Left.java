package hr.fer.zemris.optjava.dz13;

public class Left extends Node {

	public Left(Node parent, Node[] children) {
		super(parent, children);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(Ant ant) {
		ant.rotate_left();
	}

	@Override
	public Node copy(Node par) {
		return new Left(par,null);
	}

	@Override
	public String toString() {
		return "Left";
	}
	
}
