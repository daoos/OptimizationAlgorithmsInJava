package hr.fer.zemris.optjava.dz2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.math3.analysis.DifferentiableMultivariateFunction;
import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Prijenosna implements IHFunction {

	private double[][] podaci;
	private ErrorP[] e = null;
	private RealMatrix hes = null;
	
	public Prijenosna (double[][] m){
		podaci = m;
		e = new ErrorP[podaci.length];
		for(int i =0;i<podaci.length;i++) {
			e[i] = new ErrorP(podaci[i]);
		}
	}
	
	@Override
	public int dimension() {
		return 	this.podaci[0].length-1;
	}

	@Override
	public double valueAt(double[] point) {
		double rez=0;
		for(int i=0;i<this.e.length;i++) {
			rez += this.e[i].valueAt(point)*this.e[i].valueAt(point);
		}
		return rez;
	}

	@Override
	public double[] gradValueAt(double[] point) {
		double[] rez = new double[dimension()];
		for(int i=0;i<dimension();i++) {
			for(int j=0;j<this.e.length;j++) {
				rez[i] -= this.e[j].valueAt(point)*this.e[j].gradValueAt(point)[i];
			}
		}
		return rez;
	}

	@Override
	public Object hesseI(double[] point) {
		if(hes!=null) return hes;
		double[][] matrixData = new double[this.dimension()][this.dimension()];
		
		RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
		RealMatrix pInverse = new LUDecomposition(m).getSolver().getInverse();
		hes = pInverse;
		return pInverse;
	}

	/**
	 * @param args
	 */
	private static double[][] parseFile(String problemFileDir) throws FileNotFoundException {
		File test = new File(problemFileDir+"\\zad-prijenosna.txt" );
		int numberofvariables = 7;
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
			double[] startPoint = new double[6];
			Random rand = new Random();
			for (int i= 0;i<startPoint.length;i++) {
				startPoint[i] = rand.nextDouble();
			}
			Prijenosna f = new Prijenosna(m);
			NumOptAlgorithms.gradSpust(f,Long.parseLong(args[1]), startPoint);
		}else if (args[0].equalsIgnoreCase("newton")) {
			double[][] m = parseFile(args[2]);
			double[] startPoint = new double[10];
			Random rand = new Random();
			for (int i= 0;i<startPoint.length;i++) {
				startPoint[i] = rand.nextDouble();
			}
			//NumOptAlgorithms.newton(new FunctionError(m),Long.parseLong(args[1]), startPoint);
			}
	}

}
