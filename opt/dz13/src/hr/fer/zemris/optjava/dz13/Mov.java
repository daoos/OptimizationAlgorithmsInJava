package hr.fer.zemris.optjava.dz13;

public class Mov extends Node {

	public Mov(Node parent, Node[] children) {
		super(parent, children);
		this.type = "terminal";
	}

	@Override
	public void run(Ant ant) {
		ant.mov();
	}

	@Override
	public Node copy(Node par) {
		return new Mov(par,null);
	}
	
	@Override
	public String toString() {
		return "Mov";
	}

}
