package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class FunctionError implements IHFunction {
	
	private double[][] podaci;
	private Error[] e;
	private Object hes=null;
	
	public FunctionError(double[][] m){
		this.podaci = m;
		this.e = new Error[this.podaci[0].length-1];
		for(int i=0;i<this.e.length;i++) {
			this.e[i] = new Error(this.podaci[i]);
		}
	}

	@Override
	public int dimension() {
		return this.podaci[0].length-1;
	}

	@Override
	public double valueAt(double[] point) {
		double r=0d;
		for(int i=0;i<this.e.length;i++) {
			r += this.e[i].valueAt(point)*this.e[i].valueAt(point);
		}
		return r;
	}

	@Override
	public double[] gradValueAt(double[] point) {
		double[] r = new double[dimension()];
		for(int i=0;i<this.e.length;i++) {
			for(int j=0;j<this.e.length;j++){
				r[i] += 2*this.e[j].valueAt(point)*this.e[j].getX(i);
						
			}
		}
		return r;
	}

	@Override
	public Object hesseI(double[] point) {
		if(hes!=null) return hes;
		double[][] matrixData = new double[this.dimension()][this.dimension()];
		for(int i=0;i<this.dimension();i++) {
			for(int j=0;j<this.dimension();j++) {
				for(int k=0;k<this.dimension();k++){
					matrixData[i][j] += this.podaci[k][i]*this.podaci[k][j]*2;
				}
			}
		}
		RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
		RealMatrix pInverse = new LUDecomposition(m).getSolver().getInverse();
		hes = pInverse;
		return pInverse;
	}

}
