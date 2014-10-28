package hr.fer.zemris.optjava.dz4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BoxFilling {

	private static int heigth;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length<7) throw new IllegalArgumentException("Insufficient number of parameters");
		String staza = args[0];
		int sizeOfPopulation = Integer.parseInt(args[1]);
		int n = Integer.parseInt(args[2]);
		int m = Integer.parseInt(args[3]);
		heigth = 20;
		boolean p = Boolean.parseBoolean(args[4]);
		int maxNumberOfIterations = Integer.parseInt(args[5]);
		int lengthOfContainer = Integer.parseInt(args[6]);
		
		
		File test = new File(staza);
		Scanner input = new Scanner(test);
		String in = input.nextLine();
		in = in.substring(1, in.length()-1);
		String[] st = in.split(",");
		input.close();
		int[] stapovi = new int[st.length];
		stapovi[0] = Integer.parseInt(st[0]);
		for(int i=0;i<stapovi.length;i++) {
			stapovi[i] = Integer.parseInt(st[i].substring(1,st[i].length()));
		}
		Arrays.sort(stapovi);
		Storage storage = new Storage(stapovi);
		
		
		Random rand = new Random();
		Tournament selAdd = new Tournament(n);
		Tournament selDelete = new Tournament(m);
		DoubleArraySolution[] population = new DoubleArraySolution [sizeOfPopulation];
		for(int i=0;i<population.length;i++) {
			population[i] = new DoubleArraySolution(2);
			population[i].values[0] = rand.nextDouble();
		}
		double sigma = 0.1;
		BLXalphaBoxCrossing cross = new BLXalphaBoxCrossing();
		
		
		for(int i=0;i<maxNumberOfIterations;i++) {
			for(int k=0;k<population.length;k++) {
				fitnees(population,storage);
				DoubleArraySolution parent1 = selAdd.Selection(population,true);
				DoubleArraySolution parent2 = selAdd.Selection(population,true);
				DoubleArraySolution child = cross.cross(parent1, parent2);
				DoubleArraySolution parent = selDelete.Selection(population, false);
				
				
				double percent = (rand.nextDouble()-0.5)*sigma;
				child.values[0] += percent;
				value(child,storage.clone());
				value(parent,storage.clone());
				if(p) {
					if(child.fit>parent.fit) {
						parent = child;
					}
				}else {
					parent = child;
				}
				fitnees(population,storage);
				if(best(population,0,population.length).fit<=lengthOfContainer) break;
			}
			DoubleArraySolution u = best(population,0,population.length);
			value(u,storage.clone());
			System.out.println(u.fit);
		}
	}

	private static void fitnees(DoubleArraySolution[] population,Storage storage) {
		double fMid=0,fMin=0,n=12000;
		for(DoubleArraySolution p:population){
			Storage s = storage.clone();
			for(int i=0;i<n;i++){	
			value(p,s);
			}
			p.fit/=n;
			fMid += p.fit;
			if (p.fit<fMin) fMin= p.fit;
		}
		for(DoubleArraySolution p:population) {
			p.fit = (p.fit-fMin)/(fMid/population.length-fMin)/population.length;
		}
	}
	
	private static DoubleArraySolution best(DoubleArraySolution[] p,int begin,int end){
		if(end - begin==1) return p[begin];
		if(end-begin==2) {
			if(p[begin].fit>p[begin+1].fit) return p[begin];
			else return p[begin+1];
		}
		DoubleArraySolution l,d;
		l = best(p,begin,(begin+end)/2);
		d = best(p,(begin+end)/2,end);
		if(l.fit>d.fit) return l;
		return d;
	}
	
	public static void value (DoubleArraySolution p,Storage storage){
		Random rand = new Random();
		int size = 0;
		while(true){	
			//System.out.println(++i);
			int h=0;
			StapArray t = storage.first;
			size++;
			
			
			while(true&&t!=null) {
				while(t!=null&&t.number==0) t = t.next;
				if(t==null) break;
				if(t.value>heigth-h){
					break;
				}
				boolean b = true;
				StapArray r = t;
				while(t.next!=null){
					if(t.next.number!=0){
						b = false;
						break;
					}else{
						t = t.next;
					}	
				}
				t = r;
				double  percentage = rand.nextDouble();
				if(t.next==null||b){
					h+=t.value;
					t.number--;
				}else if(p.values[0]<percentage){
					h+=t.value;
					t.number--;
				}else{
					t = t.next;
				}
			}
			if(h==0) {
				//System.out.println("Warning");
				size--;
			}
			int suma=0;
			t=storage.first;
			while(t!=null) {
				suma += t.number;
				t = t.next;
			}
			if(suma<1) {
				//System.out.println("Value"+size);
				p.fit += size;
				size = 0;
				break;
			}
			
		}
		//System.out.println(storage);
	}
}


