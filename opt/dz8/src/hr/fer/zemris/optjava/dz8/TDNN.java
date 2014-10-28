package hr.fer.zemris.optjava.dz8;

import Jama.Matrix;

public class TDNN implements INeuralNet {

	int[] composition = null;
	ITransferFunction[] function=null;
	IReadOnlyDataset dataset = null; 
	
	
	public TDNN(int [] com,ITransferFunction[] func,IReadOnlyDataset t) {
		composition = com;
		function = func;
		dataset =t;
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
				wM[j] = wCalc(x,n+1,this.composition[i-1]);
				w0[j] = x[n];
				n+=this.composition[i-1]+1;
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
	
	@Override
	public double eval(Double[] x) {
		double suma=0;
		int n=0;
		for(Sample s:this.dataset.samples) {
			double [] rez= new double[1];
			this.calcOutputs(s.input, x, rez);
			suma += (-rez[0]+s.next)*(-rez[0]+s.next);
			n++;
		}
		return suma/n;
	}

	@Override
	public int getWeigthsCount() {
		int r=0;
		
		for(int i=1;i<this.composition.length;i++) {
			r += (this.composition[i-1]+1)*this.composition[i];
		}
		
		return r;		
	}

	
}
