package hr.fer.zemris.optjava.dz2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Sustav {
	
	
	private static double[][] parseFile(String problemFileDir) throws FileNotFoundException {
		File test = new File(problemFileDir+"\\zad-sustav.txt" );
		int numberofvariables = 10;
		Scanner input = new Scanner(test);
		double[][] m = new double[numberofvariables][numberofvariables+1];
		int i=0,j;
		
		while(input.hasNextLine()) {
			String in = input.nextLine();
			if(in.charAt(0)=='#') continue;
			if(in.length()<10) break;
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
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length<3) {
			throw new IllegalArgumentException("");
		}
		if(args[0].equalsIgnoreCase("grad")){
			double[][] m = parseFile(args[2]);
			double[] startPoint = new double[10];
			Random rand = new Random();
			for (int i= 0;i<startPoint.length;i++) {
				startPoint[i] = rand.nextDouble();
			}
			NumOptAlgorithms.gradSpust(new FunctionError(m),Long.parseLong(args[1]), startPoint);
		}else if (args[0].equalsIgnoreCase("newton")) {
			double[][] m = parseFile(args[2]);
			double[] startPoint = new double[10];
			Random rand = new Random();
			for (int i= 0;i<startPoint.length;i++) {
				startPoint[i] = rand.nextDouble();
			}
			NumOptAlgorithms.newton(new FunctionError(m),Long.parseLong(args[1]), startPoint);
			}
	}
}
