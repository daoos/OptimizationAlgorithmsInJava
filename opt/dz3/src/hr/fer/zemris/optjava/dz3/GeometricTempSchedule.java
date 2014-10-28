package hr.fer.zemris.optjava.dz3;

public class GeometricTempSchedule implements ITempSchedule {
	private double alpha;
	private double tempInitial;
	private double tempCurrent;
	private int innerLimit;
	private int outerLimit;
	private int innerLoopCounter = 0;
	private int outerLoopCounter = 0;
	
	GeometricTempSchedule ( double tempInitial, double tempEnding, int innerLimit, int outerLimit ) {
		this.tempInitial = tempInitial;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
		
		alpha = Math.pow( tempEnding/tempInitial, 1/(outerLimit-1));
	}
	
	public double getNextTemperature() {
		if ( !(innerLoopCounter < innerLimit && outerLoopCounter < outerLimit) ) {
			innerLoopCounter = 0;
			outerLoopCounter = 0;
		}
		
		tempCurrent = Math.pow(alpha, innerLoopCounter) * tempInitial;
		
		innerLoopCounter = (innerLoopCounter + 1) % innerLimit;
		outerLoopCounter = (outerLoopCounter + 1) % outerLimit;
		
		return tempCurrent;
	}
	
	public int getInnerLoopCounter() {
		return innerLoopCounter;
	}

	public int getOuterLoopCounter() {
		return outerLoopCounter;
	}
	
	public int getOuterLoopLimit() {
		return outerLimit;
	}
}
