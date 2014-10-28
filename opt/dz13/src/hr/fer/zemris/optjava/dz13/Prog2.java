package hr.fer.zemris.optjava.dz13;

public class Prog2 extends Node {

	public Prog2(Node parent,Node[] children) {
		super(parent,children);
		this.type = "function";
	}

	@Override
	public void run(Ant ant) {
		children[0].run(ant);
		children[1].run(ant);
	}

	@Override
	public Node copy(Node par) {
		Node r = new Prog2(par,null);
		Node[] ch = new Node[2];
		ch[0] = children[0].copy(r);
		ch[1] = children[1].copy(r);
		r.children = ch;
		return r;
	}
	
	@Override
	public String toString() {
		return "Prog2"+"("+children[0]+","+children[1]+")";
	}

}
