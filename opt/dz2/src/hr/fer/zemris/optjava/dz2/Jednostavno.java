package hr.fer.zemris.optjava.dz2;

import java.util.Random;

public class Jednostavno {
	
	public static void main(String[] args) throws IllegalArgumentException {
		if(args[0].equalsIgnoreCase("1a")){
			double[] startPoint = new double[2];
			if(args.length>2) {
				startPoint[0] = Double.parseDouble(args[2]);
				startPoint[1] = Double.parseDouble(args[3]);
				
			}else{
				Random rand = new Random();
				for(int i=0;i<2;i++) {
					startPoint[i] = rand.nextDouble()/Double.MAX_VALUE*5 ;
				}

			}
			NumOptAlgorithms.gradSpust(new Function1(), Long.parseLong(args[1]),startPoint);
		}else if(args[0].equalsIgnoreCase("1b")){
			double[] startPoint = new double[2];
			if(args.length>2) {
				startPoint[0] = Double.parseDouble(args[2]);
				startPoint[1] = Double.parseDouble(args[3]);
				
			}else{
				Random rand = new Random();
				for(int i=0;i<2;i++) {
					startPoint[i] = rand.nextDouble()/Double.MAX_VALUE*5 ;
				}

			}
			NumOptAlgorithms.newton(new Function1(), Long.parseLong(args[1]),startPoint);
		}else if(args[0].equalsIgnoreCase("2a")){
			double[] startPoint = new double[2];
			if(args.length>2) {
				startPoint[0] = Double.parseDouble(args[2]);
				startPoint[1] = Double.parseDouble(args[3]);
				
			}else{
				Random rand = new Random();
				for(int i=0;i<2;i++) {
					startPoint[i] = rand.nextDouble()/Double.MAX_VALUE*5 ;
				}

			}
			NumOptAlgorithms.gradSpust(new Function2(), Long.parseLong(args[1]),startPoint);
		}else if(args[0].equalsIgnoreCase("2b")){
			double[] startPoint = new double[2];
			if(args.length>2) {
				startPoint[0] = Double.parseDouble(args[2]);
				startPoint[1] = Double.parseDouble(args[3]);
				
			}else{
				Random rand = new Random();
				for(int i=0;i<2;i++) {
					startPoint[i] = rand.nextDouble()/Double.MAX_VALUE*5 ;
				}

			}
			NumOptAlgorithms.newton(new Function2(), Long.parseLong(args[1]),startPoint);
		}else{
			throw new IllegalArgumentException("Available for first argument are:1a,1b,2a,2b");
		}

	}

}
