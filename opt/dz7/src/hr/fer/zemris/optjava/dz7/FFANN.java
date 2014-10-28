package hr.fer.zemris.optjava.dz7;

import Jama.Matrix;

public class FFANN {

	int[] composition = null;

	Integer y=0;
	ITransferFunction[] function=null;
	IReadOnlyDataset dataset = null;
	
	
	public FFANN(int [] com,ITransferFunction[] func,IReadOnlyDataset t) {
		composition = com;
		function = func;
		dataset =t;
	}
	
	public int getWeightsCount() {
		int r=0;
		
		for(int i=1;i<this.composition.length;i++) {
			r += (this.composition[i-1]+1)*this.composition[i];
		}
		
		return r;
	}
	
	private double[] wCalc(Double[] x,int start,int n) {
		double [] r = new double[n];
		
		for(int i=0;i<n;i++) {
			r[i] = x[i+start];
		}
		
		return r;
	}
	
	public void calcOutputs(Double[] input, Double[] x, double[] outputs) {

		
		double[][] inputs = new double[input.length][1];
		
		for(int i=0;i<input.length;i++) {
			inputs[i][0] = input[i];
		}
		
		int n=0;
		
		for(int i=1;i<this.composition.length;i++) {
			Matrix ul = new Matrix(inputs);
			
			double[][] wM= new double[this.composition[i]][inputs.length];
			double [] w0= new double[this.composition[i]];
			
			for(int j=0;j<this.composition[i];j++) {
				wM[j] = wCalc(x,n+1,inputs.length);
				w0[j] = x[n];
				n+=this.composition[i]+1;
			}
			
			Matrix w0M = new Matrix(w0,w0.length);
			
			Matrix w = new Matrix(wM);
			
			Matrix t = w.times(ul);
			
			t.plus(w0M);
			
			double[][] tzm = new double [w0.length][1];
			
			for(int j=0;j<tzm.length;j++) {
				tzm[j][0] = this.function[i-1].value(t.get(j, 0));
			}
			
			inputs = tzm;
		}
		for(int j=0;j<outputs.length;j++) {
			outputs[j] = inputs[j][0];
		}
	}

	double eval(Double[] x) {
		double suma=0;
		y = 0;
		for(int i=0;i<this.dataset.uzorci.size();i++) {
			double[] val= new double[this.dataset.numOfExits()];
			calcOutputs(this.dataset.uzorci.get(i),x,val);
			int t=0;
			for(int j=0;j<3;j++) {
				if(val[j]<0.5 && this.dataset.results.get(i)[j]==0) t++;
				if(val[j]>0.5 && this.dataset.results.get(i)[j]==1) t++;
			}
			if(t==3) {
				++y;
			}
			for(int j=0;j<val.length;j++) {
				suma+=Math.pow(val[j]-this.dataset.results.get(i)[j], 2);
			}
		}

		suma /= this.dataset.numOfSets();
		return suma;
	}
	
}
