package hr.fer.zemris.optjava.dz4;

import java.util.Random;

public class GeneticAlgorithm {

	/**
	 * @param args
	 * Napišite razred hr.fer.zemris.optjava.dz4.part1.GeneticAlgorithm
	 *  koji rješava četvrti zadatak (traženje koeficijenata a do f iz 
	 *  druge domaće zadaće). Algoritam treba biti generacijski elitistički. 
	 *  Neka kao reprezentaciju rješenja koristi polje decimalnih brojeva. 
	 *  Operator križanja izvedite kao BLX- a operator mutacije prema pseudokodu 3.2 
	*/
	
	private static void sort(DoubleArraySolution[] d,int start,int finish) {
		if(finish-start<3){
			return;
		}
		if(finish-start==3) {
			if(d[start].fit<d[start+1].fit) {
				DoubleArraySolution s = d[start];
				d[start] = d[start+1];
				d[start+1] = s;
			}
		}
		if(finish-start>3) {
			sort(d,start,finish-(finish-1-start)/2);
			sort(d,finish-(finish-1-start)/2,finish);
		}
		for(int i=start;i<finish;i++) {
			for(int j=finish-1;j>i;j--){
				if(d[i].fit<d[j].fit){
					DoubleArraySolution s = d[i];
					d[i] = d[j];
					d[j] = s;
				}
			}
		}
	}
	
	static int sizeOfPopulation;
	static double minError;
	static int maxNumberOfGenerations;
	static String selectionType;
	static double sigma;
	
	public static void main(String[] args) {
		double[][] m = new double[] [] {
			{-0.160,2.320,-3.500,-2.660,4.620,-171.36699979479872},
			{-0.870,  2.380, -2.770, -4.620, 2.350, -77.77052193414312},
			{1.380, -4.120, -4.530,  2.480, -0.760, -37.81539134581623},
			{4.570,  0.050,  2.240,  2.300, -1.460, 66.49285583480545},
			{0.190, -1.200, -0.900, -4.930, -3.120, -142.29787708092266},
			{ 1.260, -0.520,  2.280, -4.750, -1.100, 12.049860325702245},
			{ 1.650,  3.510, -0.050,  0.560, -1.640, -29.538432212221274},
			{-1.400, -1.690, -0.470,  1.340, -2.030, -6.694032679028332},
			{-0.760,  1.300,  1.240,  0.020,  3.190, 10.81258978219227},
			{ 2.320, -1.010,  1.990, -0.820,  3.870, 20.501960308630863},
			{ 2.260, -3.440, -3.510, -2.830,  2.550, 79.76362779005981},
			{ 2.350,  2.500, -1.790,  2.450, -3.820, 26.865253294441743},
			{-4.140, -2.230, -1.220, -4.650,  4.290, -759.72550949069},
			{-0.040, -2.300, -3.980,  3.280, -2.600, 66.24113395811048},
			{ 3.340, -3.600,  4.540, -3.320, -4.460, 253.86877150367545},
			{-4.770,  4.610,  2.250,  1.050,  0.080, 1467.6191660121754},
			{-1.660, -2.550, -0.220, -3.030,  4.510, -231.41576698683608},
			{-0.940, -0.580,  3.370,  1.810, -2.510, 122.58468874880538},
			{ 4.100, -2.470, -2.600, -1.430,  1.020, 535.0289456402061},
			{-1.380, -3.780, -1.240, -1.150,  4.190, -100.00357415185884}};
		Prijenosna f = new Prijenosna(m);
		
		
		
		if (args.length<5) throw new IllegalArgumentException("Nedovoljan broj parametara");
		sizeOfPopulation=Integer.parseInt(args[0]);
		DoubleArraySolution[] population  = new DoubleArraySolution[sizeOfPopulation];
		double[] min = new double[6];
		double[] max = new double[6];
		for(int i=0;i<6;i++) {
			min[i] = -3.0d;
			max[i] = 6.0d;
		}
		for(int i=0;i<population.length;i++){
			population[i] = new DoubleArraySolution(6,min,max);
		}
		minError = Double.parseDouble(args[1]);
		maxNumberOfGenerations = Integer.parseInt(args[2]);
		selectionType=args[3];
		sigma = Double.parseDouble(args[4]);
		ISelection selekcija = null;
		if(selectionType.equals("rouletteWheel")) {
			selekcija = new RouletteWheel();
		}else if(selectionType.substring(0, 10).equals("tournament")){
			int o =Integer.parseInt(selectionType.substring(11, selectionType.length()));
			selekcija = new Tournament(o);
		}
		
		
		//double sigma1 = sigma;
		BLXalphaCrossing cross = new BLXalphaCrossing();
		
		for(int g=0;g<maxNumberOfGenerations;g++) {
			if(f.valueAt(population[0].values)<minError) break;
			//sigma = sigma1*(Math.pow(0.999999,g));
			DoubleArraySolution[] nextgen = new DoubleArraySolution[sizeOfPopulation];
			
			
			double fitMin=0,tmp;
			double fMid=0;
			Random rand = new Random();
			for(int i=0;i<population.length;i++) {
				tmp = -f.valueAt(population[i].values);
				fMid += tmp;
				if(tmp<fitMin) fitMin= tmp;
				population[i].fit = tmp;
			}
			for(int i=0;i<population.length;i++) {
				population[i].fit = (population[i].fit-fitMin)/(fMid/population.length-fitMin)/population.length;
			}
			sort(population,0,population.length);
			nextgen[0] = population[0];
			
			
			for(int k=1;k<population.length;k++){
				nextgen[k] = cross.cross(selekcija.Selection(population),selekcija.Selection(population));
				for(int j=0;j<nextgen[k].values.length;j++) {
					double r = (rand.nextDouble()-0.5)*2*sigma;
					nextgen[k].values[j] += r;
				}
			}
			for(int k=0;k<population[0].values.length;k++) System.out.print(population[0].values[k]+" ");
			System.out.println(f.valueAt(population[0].values));
			population = nextgen;
		}
		
		
		System.out.println(f.valueAt(population[0].values));
		for(int i=0;i<population[0].values.length;i++)System.out.print(population[0].values[i]+" ");
	}

}
