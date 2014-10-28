package hr.fer.zemris.optjava.dz7;
import java.io.FileNotFoundException;


public class ANNTraniner {

	/**
	 * @param args
	 * file: datoteka koja sadrži skup podataka iris
	 * alg: oznaka algoritma koji je potrebno upogoniti
	 * n: željena velièina populacije
	 * merr: prihvatljivo srednje kvadratno odstupanje (tj. pogreška)
	 * maxiter: maksimalni broj generacija/epoha
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length!=5) {
			throw new IllegalArgumentException();
		}
		String test = args[0];
		int n = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxiter = Integer.parseInt(args[4]);
		IReadOnlyDataset dataset = new IReadOnlyDataset(test);
		
		FFANN ffann = new FFANN(
				new int[] {4,5,3,3},
				new ITransferFunction[] {
				new SigmoidTransferFunction(),
				new SigmoidTransferFunction(),
				new SigmoidTransferFunction(),
				new SigmoidTransferFunction() //f(x)=1.0/(1.0+e^(-x))
				//new LinearTransferFunction() // primjer: f(x)=x
				},
				dataset);
		
		if(args[1].contains("pso")) {
			@SuppressWarnings("unused")
			PSOAlg alg = new PSOAlg(args[1], n,merr,maxiter,ffann);
		}else {
			ClonAlg alg = new ClonAlg(n, maxiter, merr, ffann);
		}
	}

}
