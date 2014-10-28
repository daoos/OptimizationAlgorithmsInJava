package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class ThreadLocalRNGProvider implements IRNGProvider {

	private ThreadLocal<IRNG> threadLocal = null;
	
	public ThreadLocalRNGProvider() {
		threadLocal = new ThreadLocal<>();
		threadLocal.set(new RNGRandomImpl());
	}

	
	@Override
	public IRNG getRNG() {
		if(threadLocal.get()==null) {
			threadLocal.set(new RNGRandomImpl());
		}
		return (IRNG) threadLocal.get();
	}

}
