package hr.fer.zemris.optjava.dz8;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;



public class IReadOnlyDataset {

	ArrayList<Sample> samples = null;
	
	public IReadOnlyDataset(String name,int sizeOfSample,int numberOfSamples) throws FileNotFoundException {
		File test = new File(name);
		Scanner input = new Scanner(test);
		this.samples = new ArrayList<Sample>();
		
		LinkedList<Double> t = new LinkedList<Double>();
		
		double min = Double.MAX_VALUE,max = Double.MIN_VALUE;
		
		while( (numberOfSamples>0 || numberOfSamples == -1 ) && input.hasNextDouble() ) {
			
			if(numberOfSamples>0)numberOfSamples--;
			
			t.add(input.nextDouble());
			
			if(t.getLast()<min) 
				min = t.getLast();
			
			if(t.getLast()>max) 
				max = t.getLast();
			
			if(t.size()>sizeOfSample+1) {
				t.removeFirst();
			}
			
			if(t.size()==sizeOfSample+1){
				this.samples.add(new Sample(t));
			}
		}
		
		for(Sample s:this.samples) {
			for(int i = 0 ; i < s.input.length ; i++) {
				s.input[i] -= min;
				s.input[i] /=(max-min);
				s.input[i] -= 0.5;
				s.input[i] *=2;
			}
			s.next -= min;
			s.next /=(max-min);
			s.next -= 0.5;
			s.next *=2;
		}
		
		input.close();
	}
	
	

}
