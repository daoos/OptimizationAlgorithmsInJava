package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

public class ThreadBoundRNGProvider extends EVOThread implements IRNGProvider {

	public ThreadBoundRNGProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IRNG getRNG() {
		IRNGProvider t = (IRNGProvider) Thread.currentThread();
		return t.getRNG();
	}

}
