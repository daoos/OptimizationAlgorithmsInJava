package hr.fer.zemris.optjava.dz3;

public class BitVectorNeighborhood implements INeighborhood<BitVectorSolution>{
	
	public BitVectorNeighborhood () {
	}

	@Override
	public BitVectorSolution randomNeighbor(BitVectorSolution current) {
		current = new BitVectorSolution(current.bits.length);
		current.bits[((int)(Math.random()*1000))%current.bits.length] ^= true;
		return current;
	}

}
