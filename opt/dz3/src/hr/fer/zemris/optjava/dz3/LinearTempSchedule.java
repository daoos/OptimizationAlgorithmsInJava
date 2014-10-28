package hr.fer.zemris.optjava.dz3;

public class LinearTempSchedule implements ITempSchedule {
	
	private double alpha;
	private double tCurrent;
	
	public LinearTempSchedule(double tInitial,double alpha) {
		this.alpha = alpha;
		this.tCurrent = tInitial;
	}

	@Override
	public double getNextTemperature() {
		tCurrent -= alpha;
		return tCurrent;
	}

	@Override
	public int getInnerLoopCounter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOuterLoopCounter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOuterLoopLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

}
