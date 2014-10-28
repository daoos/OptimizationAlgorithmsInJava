package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class NumOptAlgorithms {

	private static double lambda(double[] startPoint,IFunction function){
		double lambdalower = 0.0d,lambdaupper=15d;
		double[] newPointLower,newPointUpper,grad = function.gradValueAt(startPoint);
		newPointLower = startPoint.clone();
		newPointUpper = startPoint.clone();
		for(int i = 0;i<function.dimension();i++) {
			newPointUpper[i] = startPoint[i]- lambdaupper*function.gradValueAt(startPoint)[i];
		}
		while (Double.compare(function.valueAt(startPoint),function.valueAt(newPointUpper))>0) {
			lambdaupper *=2;
			for(int i = 0;i<function.dimension();i++) {
				newPointUpper[i] = startPoint[i] - lambdaupper*grad[i];
			}
		}
		while(Double.compare(Math.abs(lambdaupper-lambdalower),precision)>0){
			for(int i = 0;i<function.dimension();i++) {
				newPointUpper[i] = startPoint[i]- lambdaupper*grad[i];
				newPointLower[i] = startPoint[i]- lambdalower*grad[i];
			}
			if(Double.compare(function.valueAt(newPointUpper),function.valueAt(newPointLower))>0) {
				lambdaupper -= (lambdaupper-lambdalower) / 2; 
			}else {
				lambdalower += (lambdaupper-lambdalower) / 2;
			}
		}
		return lambdaupper;
	}
	
	public static double precision = 10E-20;

	public static boolean gradZero(double[] grad){
		for(int i=0;i<grad.length;i++){
			if(Double.compare(Math.abs(grad[i]), precision) > 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void newton(IHFunction function,long n,double[] startPoint){
		double f1,f2;
		while(n-->0&&!gradZero(function.gradValueAt(startPoint))){
			f1 = function.valueAt(startPoint);
			newPointNewton(startPoint,function);
			f2 = function.valueAt(startPoint);
			if(Double.compare(Math.abs(f1-f2), precision)<0) {
				break;
			}
		}
		for(int i=0;i<function.dimension();i++){
			System.out.print(startPoint[i]+" ");
		}
		System.out.println(n);
	}
	
	public static void gradSpust(IFunction function,long n,double[] startPoint){
		double f1,f2;
		while(n-->0&&!gradZero(function.gradValueAt(startPoint))){
			f1 = function.valueAt(startPoint);
			newPoint(startPoint,function);
			f2 = function.valueAt(startPoint);
			if(Double.compare(Math.abs(f1-f2), 0.1)<0) {
				break;
			}
			for(int i=0;i<function.dimension();i++){
				System.out.print(startPoint[i]+" ");
			}
			System.out.println();
			System.out.println(function.valueAt(startPoint));
		}
		for(int i=0;i<function.dimension();i++){
			System.out.print(startPoint[i]+" ");
		}
		System.out.println();
		System.out.println(function.valueAt(startPoint));
	}
	
	private static void newPointNewton(double[] startPoint,IHFunction function) {
		double[] r = function.gradValueAt(startPoint);
		RealMatrix	m = (RealMatrix) function.hesseI(startPoint);
		RealMatrix rez = m.multiply(MatrixUtils.createColumnRealMatrix(r));
		r = rez.getColumn(0);
		for(int i=0;i<function.dimension();i++) {
			startPoint[i] -= r[i];
		}
	}

	private static void newPoint(double[] startPoint,IFunction function) {
		for(int i = 0;i<startPoint.length-1;i++) {
			startPoint[i] +=0.00000001*function.gradValueAt(startPoint)[i];
		}
	}
	
}
