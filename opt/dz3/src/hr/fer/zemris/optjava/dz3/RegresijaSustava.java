package hr.fer.zemris.optjava.dz3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RegresijaSustava {

	private static double[][] parseFile(String problemFileDir) throws FileNotFoundException {
		File test = new File(problemFileDir+"\\zad-prijenosna.txt" );
		Scanner input = new Scanner(test);
		int numberofrows = 20;
		double[][] m = new double[numberofrows][6];
		int i=0,j;
		
		while(input.hasNextLine()) {
			String in = input.nextLine();
			if(in.charAt(0)=='#') continue;
			if(in.length()<6) break;
			in = in.substring(1, in.length()-1);
			String[] st = in.split(",");
			for(j=0;j<st.length;j++) {
				m[i][j] = Double.parseDouble(st[j]);
			}
			i++;
			}
		input.close();
		return m;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length<2) {
			throw new IllegalArgumentException("");
		}
		if(args[0].equalsIgnoreCase("decimal")){
			double[][] m = parseFile(args[1]);
			Prijenosna f = new Prijenosna(m);
			DoubleArraySolution sol = new DoubleArraySolution(7);
			LinearTempSchedule plan = new LinearTempSchedule(200000.0d, 10.0d);
			double [] deltas = new double[7];
			double [] deltasm = new double[7];
			for(int i=0;i<7;i++) deltas[i] = 0.1d;
			for(int i=0;i<7;i++) deltas[i] = -0.1;
			sol.Randomize(deltasm, deltas);
			for(int i=0;i<7;i++) deltas[i] = 0.001d;
			SimulatedAnnealing<DoubleArraySolution> alg = new SimulatedAnnealing<DoubleArraySolution>(); 
			DoubleArrayNormNeighborhood susjedstvo = new DoubleArrayNormNeighborhood(deltas);
			DoubleArraySolutionDecoder dekoder = new DoubleArraySolutionDecoder();
			alg.run(f, true, sol, plan, susjedstvo , dekoder);
		}else if(args[0].equalsIgnoreCase("binary")) {
			double[][] m = parseFile(args[1]);
			Prijenosna f = new Prijenosna(m);
			BitVectorSolution sol = new BitVectorSolution(7);
			LinearTempSchedule plan = new LinearTempSchedule(200000.0d, 20000.0d);
			double [] deltas = new double[7];
			double [] deltasm = new double[7];
			for(int i=0;i<7;i++) deltas[i] = 0.1d;
			for(int i=0;i<7;i++) deltas[i] = -0.1d;
			sol.Randomize();
			for(int i=0;i<7;i++) deltas[i] = 0.1d;
			SimulatedAnnealing<BitVectorSolution> alg = new SimulatedAnnealing<BitVectorSolution>(); 
			BitVectorNeighborhood susjedstvo = new BitVectorNeighborhood();
			BitVectorDecoder dekoder = new BitVectorDecoder(deltas, deltasm, 0, 0);
			alg.run(f, true, sol, plan, susjedstvo , dekoder);
		}
	}

}
	
