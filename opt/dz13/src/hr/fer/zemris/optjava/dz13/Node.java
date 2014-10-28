package hr.fer.zemris.optjava.dz13;

public abstract class Node {

	public Node parent;
	public Node[] children;
	public String type=null;
	
	public Node(Node parent,Node[] children) {
		this.parent = parent;
		this.children = children;
	}
	
	public abstract void run(Ant ant);
	
	public abstract Node copy(Node par);
	
	public int get_depth(){
		if(children == null) {
			return 1;
		}else {
			int c=0;
			for(Node t :children) {
				int r = t.get_depth();
				if(r > c) {
					c = r;
				}
			}
			return c+1;
		}
	}
	
	public int get_number_of_children() {
		
		if(children == null) return 1;
		
		int r = 1;
		
		for(Node t :children) {
			r += t.get_number_of_children();
		}
		
		return r;
	}
	
	public void insertNode(int t,Node node) {
		if(children!=null) {
			int z = 0;
			for(int i= 0;i<children.length;i++) {
				if(t-z==0) {
					children[i] = node;
					node.parent = this;
					return;
				}
				children[i].insertNode(t-z, node);
				z++;
			}
		}
	}
	
	public Node get_Node(int t) {
		if (t==0) 
			return this;
		else {
			if(children == null) {
				return null;
			}else {
				int z = 1;
				for(Node g:children){
					z++;
					Node r = g.get_Node(t - z);
					if(r!=null) {
						return r;
					}
				}
			}
		}
		return this;
	}

	public abstract String toString();
}
