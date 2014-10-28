package hr.fer.zemris.optjava.dz8;

import Jama.Matrix;

public class ENN implements INeuralNet {

	int[] composition = null;
	ITransferFunction[] function=null;
	IReadOnlyDataset dataset = null;
	Double[] context = null;
	
	public ENN(int [] com,ITransferFunction[] func,IReadOnlyDataset t) {
		composition = com;
		function = func;
		dataset =t;
		context = new Double[com[1]];
	}
	
	private double[] wCalc(Double[] x,int start,int n) {
		double [] r = new double[n];
		
		for(int i=0;i<n;i++) {
			r[i] = x[i+start];
		}
		
		return r;
	}
	
	public void calcOutputs(Double[] input , Double[] x, double[] outputs) {
		double[][] inputs = new double[1+this.composition[1]][1];
		
		for(int i=0; i<this.composition[1];i++) {
			this.context[i] = x[x.length-this.context.length+i-1];
		}
		
		inputs[0][0] = input[0];
		
		for(int i=1;i<inputs.length;i++) {
			inputs[i][0] = this.context[i-1];
		}
		
		int n=0;
		
		for(int i = 1 ; i < this.composition.length ; i++) {
			Matrix ul = new Matrix(inputs);
			
			double[][] wM= new double[this.composition[i]][inputs.length];
			double [] w0= new double[this.composition[i]];
			
			for(int j=0;j<this.composition[i];j++) {
				wM[j] = wCalc(x,n+1,inputs.length);
				w0[j] = x[n];
				n += inputs.length + 1;
			}
			
			Matrix w0M = new Matrix(w0,w0.length);
			
			Matrix w = new Matrix(wM);
			
			Matrix t = w.times(ul);
			
			t.plus(w0M);
			
			double[][] tzm = new double [w0.length][1];
			
			for(int j=0;j<tzm.length;j++) {
				tzm[j][0] = this.function[i-1].value(t.get(j, 0));
			}
			
			if(i==1) {
				for(int j = 0;j < this.context.length; j++) {
					this.context [j] = tzm[j][0];
				}
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
		
		r += (this.context.length+this.composition[0]+1)*this.composition[1];
		
		for(int i=2;i<this.composition.length;i++) {
			r += (this.composition[i-1]+1)*this.composition[i];
		}
		
		return r;		
	}

	
}
