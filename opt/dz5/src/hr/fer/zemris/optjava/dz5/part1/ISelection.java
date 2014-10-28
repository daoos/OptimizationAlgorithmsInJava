package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;

public interface ISelection<T> {

	public T select(ArrayList<T> population);
	
}
