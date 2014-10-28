package hr.fer.zemris.optjava.dz5.part2;

public class Function {

	private double[][] cost =null,dis=null;
	
	public Function(double[][] c,double[][] d) {
		cost =c;
		dis = d;
	}
	
	public long value(PermSolution sol) {
		long rez =0;
		for(int i=0;i<sol.p.length;i++) {
			for(int j=0;j<sol.p.length;j++) {
				rez+=cost[i][j]*dis[sol.p[i]-1][sol.p[j]-1];
			}
		}
		//System.out.println(rez);
		return rez;
	}

}
