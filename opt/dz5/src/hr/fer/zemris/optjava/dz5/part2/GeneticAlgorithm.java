package hr.fer.zemris.optjava.dz5.part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class GeneticAlgorithm {

	private static Function parse(String st) throws FileNotFoundException{
		File test = new File("data\\"+st);
		Scanner input = new Scanner(test);
		n = input.nextInt();
		double[][] c = new double[n][n],d = new double[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				c[i][j] = input.nextInt();
			}
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++){
				d[i][j] = input.nextInt();
			}
		}
		input.close();
		return new Function(c,d);
	}
	
	static int minPop = 40;
	static int n,m=34;
	static int sigma = 5; 
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * otvori dat file iz danih args
		 * napravi funkciju
		 * odredi n poè. populacija
		 * pozovi n thredova
		 * do while n>0
		 */
		
		@SuppressWarnings("unused")
		long r = 1*2*3*4*5*6*7*8*9*10*11*12;
		
		Function f = parse(args[0]);
		HashSet<PermSolution> population = new HashSet<PermSolution>();
		while(population.size()<minPop) {
			PermSolution t = new PermSolution(n);
			t.randomize(sigma);
			t.value = f.value(t);
			population.add(t);
		}
		
		ArrayList<PermSolution> pop = new ArrayList<PermSolution>();
		pop.addAll(population);
		
		PermSolution e = pop.get(0);
		for(PermSolution t:pop) {
			if(t.value<e.value) {
				e = t;
			}
		}
		
		System.out.println(e.value);
		for(int i=0;i<e.p.length;i++) {
			System.out.print(e.p[i]+" ");
		}System.out.println();
		
		ICrossP[] crosses = new ICrossP[1];
		Random rand = new Random();
		crosses[0] = new OX1(rand);
		//crosses[1] = new OX2(rand);
		//crosses[2] = new PMX(rand);
		IMutation[] mut = new IMutation[4];
		mut[0] = new DisplacmentMutation(rand);
		mut[1] = new PositionBasedMutation(rand);
		mut[2] = new SIM(rand);
		mut[3] = new SwapMutation(rand);
		
		
		
		/*for(ICrossP o:crosses) {
			for(int i=0;i<100000;i++) {
				PermSolution p1 = pop.get(rand.nextInt(pop.size()-1));
				PermSolution p2 = pop.get(rand.nextInt(pop.size()-1));
				PermSolution[] ch = new PermSolution[2];
				ch[0] = new PermSolution(n);
				ch[1] = new PermSolution(n);
				ch = o.cross(p1, p2);
				pop.add(ch[0]);
				pop.add(ch[1]);
				long h =1;
				for(Integer g:ch[0].p) h*=g;
				if (h!=r) {
					System.out.println(o.getClass());
					for(int j=0;j<n;j++) {
						System.out.print(ch[0].p[j]+" ");
					}
					System.out.println();
				}
				for(Integer g:ch[0].p) h*=g;
			}
		}*/
		
		
		while(m>0) {
			System.out.println(m);
			RAPGA[] z = new RAPGA[m];
			for(int i=0;i<m;i++) {
				ArrayList<PermSolution> po = new ArrayList<PermSolution>();
				po.addAll( pop.subList(i*pop.size()/m,(i+1)*pop.size()/m));
				z[i] = new  RAPGA(po, f);
			}
			for(RAPGA o:z){
				try {
					o.u.join();
				} catch (InterruptedException y) {
					// TODO Auto-generated catch block
					y.printStackTrace();
				}
			}
			pop = new ArrayList<PermSolution>();
			for(RAPGA t:z){
				System.out.print(t.population.size()+" ");
				pop.addAll(t.population);
			}
			System.out.println();
			if(m==19) e = pop.get(0);
			for(PermSolution t:pop) {
				if(t.value<e.value) {
					e = t;
				}
			}
			
			System.out.println(e.value);
			for(int i=0;i<e.p.length;i++) {
				System.out.print(e.p[i]+" ");
			}
			System.out.println();
			m-=2;
		}
		
		for(PermSolution t:pop) {
			if(t.value<e.value) {
				e = t;
			}
		}
		
		System.out.println(e.value);
		for(int i=0;i<e.p.length;i++) {
			System.out.print(e.p[i]+" ");
		}
	}

}
