package hr.fer.zemris.optjava.dz3;

public class BitVectorSolution extends SingleObjectiveSolution {
	public boolean [] bits;
	
	public BitVectorSolution ( int numberOfBits) {
		bits = new boolean[numberOfBits];
	}
	
	public BitVectorSolution newLikeThis() {
		return new BitVectorSolution(bits.length);
	}
	
	public BitVectorSolution duplicate() {
		BitVectorSolution b = new BitVectorSolution(bits.length);
		b.bits = bits.clone();
		return b;
	}
	
	public void Randomize () {
		bits = RandomUtil.randomizeByteArray(bits.length);
	}

}
