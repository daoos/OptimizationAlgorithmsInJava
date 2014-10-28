package hr.fer.zemris.optjava.dz6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TSPSolver {

	/**
	 * @param args
	 */
	static double a=30;
	static double tautamax = 1;
	static double tautamin=tautamax/a;
	static double ro=0.99; 
	static double alpha = 1,beta = 3;
	static String format=null;
	static int n=0;
	static int ncand=0;
	static int nants=0;
	static int maxIter=0;
	static Node[] cities= null;
	static int[][] matrix=null;
	static double[][] pheromones=null;
	static double chancebestsofar=1;
	
	static int err_fun(Node n1,Node n2) {
		if(format.equalsIgnoreCase("ATT")) {
			double xd = n1.values[0] -n2.values[0];
			double yd = n1.values[1] -n2.values[1];
			double r = Math.sqrt((xd*xd+yd*yd)/10);
			int t = (int) Math.round(r);
			if (t<r) t++;
			return t;
		}else if (format.equalsIgnoreCase("EUC_2D")) {
			double xd = n1.values[0] -n2.values[0];
			double yd = n1.values[1] -n2.values[1];
			double r = Math.sqrt((xd*xd+yd*yd)/10);
			return (int) r;
		}
		throw new IllegalArgumentException("No valid format for function present");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length!=4) throw new IllegalArgumentException("4 parameters:filename #candidates #ants #iterations");
		parse(args[0]);
		ncand = Integer.parseInt(args[1]);
		nants = Integer.parseInt(args[2]);
		maxIter = Integer.parseInt(args[3]);
		ArrayList<ArrayList<Integer>> candidates = new ArrayList<ArrayList<Integer>>();
		if(!format.equalsIgnoreCase("EXPLICIT")) {
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					matrix [i][j] = err_fun(cities[i],cities[j]);
				}
			}
		}
		a = 0.8*(n-1)/0.2;
		for(int i=0;i<n;i++) {
			ArrayList<Integer> tmp_city = new ArrayList<Integer>();
			for(int j=0;j<n;j++) {
				if(j!=i){
					if(tmp_city.size()<ncand) {
						tmp_city.add(j);
					}else {
						for(int s=0;s<tmp_city.size();s++) {
							if(matrix[i][tmp_city.get(s)]>matrix[i][j]) {
								tmp_city.remove(s);
								tmp_city.add(j);
								break;
							}
						}
					}
				}
			}
			candidates.add(tmp_city);
		}
		double tauta0=tautamax;
		pheromones = new double[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(j!=i)
				pheromones [i][j] = tauta0;
			}
		}
		int [] fvalue= new int[nants];
		Ant best = null;
		int bvalue=0;
		Random rand = new Random();
		while(maxIter>0) {
			int min=100000000,ibest=0;
			Ant [] ants = new Ant[nants];
			for(int i=0;i<nants;i++) {
				ants[i]=new Ant(n,candidates);
				ants[i].addCity(rand.nextInt(n));
			}
			for(int i=0;i<nants;i++) {
				for(int j=1;j<n;j++) {
					fvalue[i] += select(ants[i]);
				}
				fvalue[i]+=matrix[ants[i].cities[0]][ants[i].cities[n-1]];
				if(fvalue[i]<min) {
					min = fvalue[i];
					ibest = i;
				}
			}
			//ispari feromonske tragove
			int nmin=0;
			for(int i=0;i<pheromones.length;i++){
				for(int j=0;j<pheromones.length;j++){
					if(j!=i){
						pheromones[i][j]= pheromones[i][j]*ro;
						if(pheromones[i][j]<tautamin) {
							pheromones[i][j]=tautamin;
							nmin++;
						}
					}
				}
			}
			if(nmin>(n*n-2*n)) {
				for(int i=0;i<pheromones.length;i++){
					for(int j=0;j<pheromones.length;j++){
						pheromones[i][j]=tautamax;
					}
				}
			}
			if(best==null) {
				best = ants[ibest].clone();
				bvalue = min;
			}else if (bvalue>min) {
				best = ants[ibest].clone();
				bvalue = min;
			}
			//azuriraj feromonske tragove
			Ant p=best;
			for(int i=0;i<p.cities.length;i++) {
				if(i==p.cities.length-1){
					pheromones[p.cities[i]][p.cities[0]] +=(double)n/(double)bvalue*10.0;
					if(pheromones[p.cities[i]][p.cities[0]]>tautamax) {
						pheromones[p.cities[i]][p.cities[0]]=tautamax;
					}
				}else{
					pheromones[p.cities[i]][p.cities[i+1]] +=(double)n/(double)bvalue*10.0;
					if(pheromones[p.cities[i]][p.cities[i+1]]>tautamax) {
						pheromones[p.cities[i]][p.cities[i+1]]=tautamax;
					}
				}
			}
			//System.out.println(nmin+" "+min);
			for(int i=0;i<fvalue.length;i++) {
				fvalue[i] =0;
			}
			//System.out.println(bvalue);
			//for(int i=0;i<n;i++) {
				//System.out.print(best.cities[i]+" ");
			//}
			//System.out.println();
			maxIter--;
		}

		System.out.println(bvalue);
		/*
		ponavljaj dok nije kraj
ponovi za svakog mravca
stvori rješenje
vrednuj rješenje
kraj ponovi
ispari feromonske tragove
ponovi za sve_ili_neke mrave
azuriraj feromonske tragove
kraj ponovi
kraj ponavljanja
		*/
	}

	private static int select(Ant ant) {
		//get candidates
		Random rand = new Random();
		ArrayList<Integer> cand = ant.candidates.get(ant.cities[ant.poz]);
		// sum pheromones
		if(ant.candidates.get(ant.cities[ant.poz]).size()==0) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			for(int i=0;i<ant.poz;i++) {
				t.add(ant.cities[i]);
			}
			for(int i=0;i<n;i++) {
				if(!t.contains(i)) {
					cand.add(i);
				}
			}
		}
		double sumP=0;
		double[] chance = new double[cand.size()];
		for(int i=0;i<cand.size();i++) {
			chance[i] =Math.pow(pheromones[ant.poz][cand.get(i)],alpha)*Math.pow(1/(double)matrix[ant.poz][cand.get(i)],beta);
			sumP+=chance[i];
		}
		int j=ant.cities[ant.poz];
		// detect chance for every candidate
		for(int i=0;i<chance.length;i++) {
			chance[i] = chance[i]/sumP;
		}
		//choose candidate
		double vr = rand.nextDouble();
		int i;
		for(i=0;i<chance.length;i++) {
			vr -=chance[i];
			if(vr<0) break;
		}
		if (i==chance.length) i--;
		//eliminate candidate
		i = cand.get(i);
		ant.addCity(i);
		//calculate dis
		return matrix[i][j];
	}

	private static void parse(String name) throws FileNotFoundException {
		File test = new File(name);
		Scanner input = new Scanner(test);
		int m_r=0;
		while(input.hasNextLine()) {
			if(!input.hasNextDouble()) {
				String st = input.nextLine();
				if(st.indexOf("EOF")>=0) {
					break;
				}
				if(st.indexOf("DIMENSION")>=0) {
					String[] ar = st.split(" ");
					n = Integer.parseInt(ar[ar.length-1]);
					cities = new Node[n];
					matrix=new int[n][n];
				}
				if(st.indexOf("ATT")>=0) {
					format = "ATT";
				}
				if(st.indexOf("EUC_2D")>=0) {
					format = "EUC_2D";
				}
				if(st.indexOf("EXPLICIT")>=0) {
					format = "EXPLICIT";
				}
			}else if(format.equalsIgnoreCase("ATT")||format.equalsIgnoreCase("EUC_2D")) {
				double d1,d2;
				int i = input.nextInt();
				if(!input.hasNextDouble()) continue;
				d1 = input.nextDouble();
				if(!input.hasNextDouble()) continue;
				d2 = input.nextDouble();
				cities[i-1]= new Node(d1, d2);
			}else if(format.equalsIgnoreCase("EXPLICIT")) {
				for(int i=0;i<n;i++) {
					matrix[m_r][i] = input.nextInt();
				}
				m_r++;
				if(m_r==n) break;
			}
			
		}
		
		input.close();
		
	}

}
