package hr.fer.zemris.optjava.dz8;

import java.util.LinkedList;

public class Sample {

	Double[] input=null;
	Double next=null;

	public Sample(LinkedList<Double> t) {
		input = new Double[t.size()-1];
		
		for(int i = 0; i < input.length ; i++) {
			input[i] = t.get(i);
		}
		next = t.getLast();
	}

}
