package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class RAPGA implements Runnable {

	int minPop=65;
	int maxPop=150;
	static int maxIter=16,ActSelPress=1,k=140,t=10;
	static double maxSelPress=2;
	static double compFactor=0.9;
	static double SuccRatio=0.2,kp=0.17;
	private ICrossP[] crosses = null;
	private IMutation[] mut =null;
	ArrayList<PermSolution> population=null;
	private TournamentSelection selekcija=null;
	private Random rand=null;
	private Function f=null;
	Thread u;
	
	public RAPGA(ArrayList<PermSolution> pop,Function fu) {
		population = pop;
		k = (int) (kp*population.size());
		crosses = new ICrossP[1];
		rand = new Random();
		crosses[0] = new OX1(rand);
		//crosses[1] = new OX2(rand);
		//crosses[2] = new PMX(rand);
		mut = new IMutation[4];
		mut[0] = new DisplacmentMutation(rand);
		mut[1] = new PositionBasedMutation(rand);
		mut[2] = new SIM(rand);
		mut[3] = new SwapMutation(rand);
		selekcija = new TournamentSelection(k,rand);
		f = fu;
		u = new Thread(this,"Who cares");
		u.start();
	}

	@Override
	public void run() {
		
		int i=0;
		while(i<maxIter) {
			HashSet<PermSolution> nextgen= new HashSet<PermSolution>();
			HashSet<PermSolution> pool= new HashSet<PermSolution>();
			while ((nextgen.size() < (population.size()*SuccRatio)) && ((nextgen.size()+pool.size()) < (population.size()*maxSelPress))) {
				PermSolution p1=selekcija.select(population);
				PermSolution p2=selekcija.select(population);
				PermSolution[] children = crosses[rand.nextInt(crosses.length)].cross(p1, p2);
				
				mut[rand.nextInt(mut.length)].mutate(children[0]);
				mut[rand.nextInt(mut.length)].mutate(children[1]);
				mut[rand.nextInt(mut.length)].mutate(children[0]);
				mut[rand.nextInt(mut.length)].mutate(children[1]);
				
				children[0].value = f.value(children[0]);
				children[1].value = f.value(children[1]);
				
				
				if (children[0].value>(p2.value+Math.abs(p1.value-p2.value)*compFactor)) {
					if(!pool.contains(children[0])) {
						pool.add(children[0]);
					}
				}else {
					if(!nextgen.contains(children[0])) {
						nextgen.add(children[0]);
					}
				}
				
				if (children[1].value>(p2.value+Math.abs(p1.value-p2.value)*compFactor)) {
					if(!pool.contains(children[1])) {
						pool.add(children[1]);
					}
				}else {
					if(!nextgen.contains(children[1])) {
						nextgen.add(children[1]);
					}
				}
			}
			ActSelPress = nextgen.size()+pool.size();
			if(nextgen.size()<population.size()) {
				Iterator<PermSolution> pozi = pool.iterator();
				while(pozi.hasNext()&&nextgen.size()<minPop) {
					nextgen.add(pozi.next());
				}
			}
			while(nextgen.size()<=population.size()) {
				PermSolution p1=selekcija.select(population);
				PermSolution p2=population.get(population.size()-1);
				PermSolution[] children = crosses[rand.nextInt(crosses.length)].cross(p1, p2);
				
				mut[rand.nextInt(mut.length)].mutate(children[0]);
				mut[rand.nextInt(mut.length)].mutate(children[1]);
				mut[rand.nextInt(mut.length)].mutate(children[0]);
				mut[rand.nextInt(mut.length)].mutate(children[1]);
				
				children[0].value = f.value(children[0]);
				children[1].value = f.value(children[1]);

				if(!nextgen.contains(children[1])) {
					nextgen.add(children[1]);
				}
				if(!nextgen.contains(children[0])) {
					nextgen.add(children[0]);
				}
			}
			//compFactor = Math.pow(0.0001, 1/(0.1*i+1));
			//System.out.println(compFactor);
			//SuccRatio = Math.pow(0.001, 1/(0.1*i+1));
			population.clear();
			population.addAll(nextgen);
			Iterator<PermSolution> ip = nextgen.iterator();
			while(ip.hasNext()){
				PermSolution t = ip.next();
				if(f.value(t)<0.00001) {
					for(int j=0;j<t.p.length;j++) {
						System.out.print(t.p[j]+" ");
					}
					System.out.println();
				}
			}
			i++;
		}
	}

}
