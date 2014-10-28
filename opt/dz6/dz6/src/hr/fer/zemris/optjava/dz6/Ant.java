package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;

public class Ant {

	public int[] cities =null;
	public ArrayList<ArrayList<Integer>> candidates=null;
	public int poz=0;
	@SuppressWarnings("unchecked")
	public Ant(int dimension,ArrayList<ArrayList<Integer>> c) {
		this.cities=new int[dimension];
		this.candidates = new ArrayList<ArrayList<Integer>>();
		if(c!=null)for(ArrayList<Integer>s:c) {
			this.candidates.add((ArrayList<Integer>) s.clone());
		}
	}
	
	public void addCity(int index) {
		this.cities[poz] = index;
		this.poz++;
		for(ArrayList<Integer>s:this.candidates) {
			int z= s.indexOf(index);
			if(z>-1) {
				s.remove(z);
			}
		}
	}
	
	public Ant clone(){
		Ant t = new Ant(this.cities.length,null);
		t.cities = this.cities.clone();
		return t;
	}

}
