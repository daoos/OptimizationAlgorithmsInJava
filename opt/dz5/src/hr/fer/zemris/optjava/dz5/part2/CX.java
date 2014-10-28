package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Random;

public class CX implements ICrossP {

	public CX() {
	}

	private int suma(ArrayList<ArrayList<Integer>> r){
		int s=0;
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] re = (ArrayList<Integer>[]) r.toArray();
		for(ArrayList<Integer> t:re) {
			s+=t.size();
		}
		return s;
	}
	
	private ArrayList<ArrayList<Integer>> cycle (int[] p1,int[] p2){
		int i=0;
		ArrayList<ArrayList<Integer>> rez = new ArrayList<ArrayList<Integer>>();
		boolean b;
		while (suma(rez)<p1.length*2) {
			b = false;
			for(int j=0;j<rez.size();j+=2) {
				if(rez.indexOf(p1[i])>-1) {
					b = true;
					i++;
					break;
				}
			}
			if(b) {
				continue;
			}
		}
		return null;
	}
	
	@Override
	public PermSolution[] cross(PermSolution p1, PermSolution p2) {
		
		return null;
	}

}
