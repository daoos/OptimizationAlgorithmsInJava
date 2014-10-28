package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

	static /*
	 *Podsjetnik na RAPGA: algoritam kao parametre treba ogradu za minimalnu populaciju, ogradu za 
maksimalnu populaciju, maksimalni selekcijski pritisak te mehanizam koji određuje kako će se 
mijenjati (i hoće li) CompFactor temeljem kojeg se određuju uspješna djeca. Obratite pažnju da 
algoritam u populaciju djece ne smije stavljati duplikate: u okviru ove zadaće radite provjeru na 
razini genotipa, i pokušajte to napraviti efikasno. 
	 */
	
	int minPop=250;
	int maxPop=10000;
	static int maxIter=10000,ActSelPress=1,k=50,t=10;
	static double maxSelPress=1000;
	static double compFactor=0.41;
	static double SuccRatio=0.3,sigma=0.001;
	
	public GeneticAlgorithm() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		if(args.length!=1) 
			throw new IllegalArgumentException("Program prima jednu pozitivnu cjelobrojnu vrijednost");
		int n = Integer.parseInt(args[0]);
		
		Function f = new Function(n);
		Random rand = new Random();
		t = rand.nextInt(n);
		ArrayList<BitVectorSolution> population = new ArrayList<BitVectorSolution>();
		
		ICrossing[] crosses = new ICrossing[3];
		crosses[0] = new OneBreakingPointCrossing();
		crosses[1] = new TBreakingPointCrossing(t);
		crosses[2] = new UniformCrossing();
		TournamentSelection selekcija = new TournamentSelection(k);
		
		BitMutation mutacija= new BitMutation(sigma);
		
		BitVectorSolution tmp;
		int i=0;
		for(i=0;i<minPop;i++) {
			tmp = new BitVectorSolution(n);
			tmp.randomize();
			population.add(tmp);
		}
		i=0;
		boolean c=true;
		while(i<maxIter&&c) {
			ArrayList<BitVectorSolution> nextgen= new ArrayList<BitVectorSolution>();
			ArrayList<BitVectorSolution> pool= new ArrayList<BitVectorSolution>();
			
			while ((nextgen.size() < (population.size()*SuccRatio)) && ((nextgen.size()+pool.size()) < (population.size()*maxSelPress))) {
				BitVectorSolution p1=selekcija.select(population);
				BitVectorSolution p2=population.get(population.size()-1);
				BitVectorSolution[] children = crosses[rand.nextInt(crosses.length)].cross(p1, p2);
				
				mutacija.mutate(children[0]);
				mutacija.mutate(children[1]);
				
				children[0].value = f.value(children[0]);
				if(Math.abs(children[0].value-1)<0.001) {
					for(boolean a:children[0].bits) {
						c = false;
						if(a) System.out.print(1);
						else System.out.print(0);
					}
					System.out.println();
					break;
				}
				
				children[1].value = f.value(children[1]);
				if(Math.abs(children[1].value-1)<0.00001) {
					for(boolean a:children[1].bits) {
						c = false;
						if(a) System.out.print(1);
						else System.out.print(0);
					}
					System.out.println();
					break;
				}
				System.out.println(children[0].value+" "+children[0].getOnes()+" "+children[1].value+" "+children[1].getOnes());
				if (children[0].value<=(p2.value+Math.abs(p1.value-p2.value)*compFactor)) {
					pool.add(children[0]);
				}else {
					boolean r =true;
					for(BitVectorSolution z:population) {
						if(children[0].k ==z.k) {
							for(int d=0;d<z.bits.length;d++) {
								if(children[0].bits[d]!=z.bits[d]) {
									r = true;
									break;
								}
							}
						}
					}
					if(r) nextgen.add(children[0]);
				}
				
				if (children[1].value<=(p2.value+Math.abs(p1.value-p2.value)*compFactor-0.001)) {
					pool.add(children[1]);
				}else {
					boolean r =false;
					for(BitVectorSolution z:population) {
						if(children[1].k ==z.k) {
							for(int d=0;d<z.bits.length;d++) {
								if(children[1].bits[d]!=z.bits[d]) {
									r = true;
									break;
								}
							}
						}else {
							r =true;
						}
					}
					if(r) nextgen.add(children[1]);
				}
			}
			ActSelPress = nextgen.size()+pool.size();
			for(int j=0;j<n*100&&nextgen.size()<=population.size()&&pool.size()>1;j++) {
				int v=rand.nextInt(pool.size()-1);
				BitVectorSolution p1 = pool.get(v);
				if(Math.abs(p1.value-1)<0.001) {
					for(boolean a:p1.bits) {
						c = false;
						if(a) System.out.print(1);
						else System.out.print(0);
					}
					System.out.println();
					break;
				}
				boolean r =false;
				for(BitVectorSolution z:population) {
					if(p1.k ==z.k) {
						for(int d=0;d<z.bits.length;d++) {
							if(p1.bits[d]!=z.bits[d]) {
								r = true;
								break;
							}
						}
					}else {
						r = true;
					}
				}
				if(r) {
					nextgen.add(p1);
					pool.remove(v);
				}
			}
			while(nextgen.size()<=population.size()) {
				BitVectorSolution p1=selekcija.select(population);
				BitVectorSolution p2=population.get(population.size()-1);
				BitVectorSolution[] children = crosses[rand.nextInt(crosses.length)].cross(p1, p2);
				
				mutacija.mutate(children[0]);
				mutacija.mutate(children[1]);
				
				children[0].value = f.value(children[0]);
				children[1].value = f.value(children[1]);
				if(Math.abs(children[0].value-1)<0.001) {
					for(boolean a:children[0].bits) {
						c = false;
						if(a) System.out.print(1);
						else System.out.print(0);
					}
					System.out.println();
					break;
				}
				if(Math.abs(children[1].value-1)<0.001) {
					for(boolean a:children[1].bits) {
						c = false;
						if(a) System.out.print(1);
						else System.out.print(0);
					}
					System.out.println();
					break;
				}
				nextgen.add(children[rand.nextInt(2)]);
			}
			//compFactor = Math.pow(0.0001, 1/(0.1*i+1));
			//System.out.println(compFactor);
			//SuccRatio = Math.pow(0.001, 1/(0.1*i+1));
			population = nextgen;
			i++;
		}
		for(BitVectorSolution p1:population) {
			if(Math.abs(p1.value-1)<0.00001) {
				for(boolean a:p1.bits) {
					if(a) System.out.print(1);
					else System.out.print(0);
				}
				System.out.println();
			}
		}
	}

}
