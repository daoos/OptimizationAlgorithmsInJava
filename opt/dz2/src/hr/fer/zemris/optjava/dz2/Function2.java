package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Function2 implements IHFunction {

	@Override
	public int dimension() {
		return 2;
	}

	@Override
	public double valueAt(double[] point) {
		return (point[0]-1)*(point[0]-1)+10*(point[1]-2)*(point[1]-2);
	}

	@Override
	public double[] gradValueAt(double[] point) {
		double[] grad = new double[2];
		grad[0]= 2*(point[0]-1);
		grad[1]= 20*(point[1]-2);
		return grad;
	}

	@Override
	public Object hesseI(double[] p) {
		double[][] matrixData = { {2d,0d}, {0d,20d}};
		RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
		RealMatrix pInverse = new LUDecomposition(m).getSolver().getInverse();
		return pInverse;
	}

}
