package hr.fer.zemris.optjava.dz8;
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
		String net = args[1];
		int n = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxiter = Integer.parseInt(args[4]);
		
		INeuralNet network;

		String[] co = net.split("-")[1].split("x");
		int[] com = new int[co.length];
		for(int i=0;i<com.length;i++) {
			com[i] = Integer.parseInt(co[i]);
		}
		
		IReadOnlyDataset dataset = new IReadOnlyDataset(test,com[0],-1);
		
		
		ITransferFunction [] functions = new ITransferFunction[com.length];
		
		for(int i=0;i<functions.length;i++) {
			functions[i] = new THTransferFunction();
		}
		
		
		if(net.contains("tdnn")) {
			network = new TDNN(com,functions,dataset);
			int t = network.getWeigthsCount();
		}else {
			network = new ENN(com,functions,dataset);
			int t = network.getWeigthsCount();	
		}
		
		new DE(network,n,merr,maxiter);
	}

}
