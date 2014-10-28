package hr.fer.zemris.optjava.dz13;

public class IfFoodAhead extends Node {

	public IfFoodAhead(Node parent,Node[] children) {
		super(parent,children);
		this.type = "function";
	}

	@Override
	public void run(Ant ant) {
		if(ant.see_food) {
			children[0].run(ant);
		}else{
			children[1].run(ant);
		}

	}

	@Override
	public Node copy(Node par) {
		Node r = new IfFoodAhead(par, null);
		Node [] ch = new Node[2];
		ch[0] = children[0].copy(r);
		ch[1] = children[1].copy(r);
		r.children = ch;
		return r;
	}

	@Override
	public String toString() {
		return "IfFoodAhead"+"("+children[0]+","+children[1]+")";
	}

}
