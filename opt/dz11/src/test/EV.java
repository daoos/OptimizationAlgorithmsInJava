package test;

import hr.fer.zemris.optjava.rng.RNG;

public class EV implements Runnable{
		public EV() {
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println(RNG.getRNG().nextBoolean());
		}
	}