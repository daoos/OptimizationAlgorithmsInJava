package hr.fer.zemris.optjava.dz13;

public class Right extends Node {

	public Right(Node parent, Node[] children) {
		super(parent, children);
		this.type = "terminal";
	}

	@Override
	public void run(Ant ant) {
		ant.rotate_rigth();
	}

	@Override
	public Node copy(Node par) {
		return new Right(par,null);
	}
	
	@Override
	public String toString() {
		return "Right";
	}

}
