package hr.fer.zemris.optjava.dz7;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class IReadOnlyDataset {

	ArrayList<Double[]> uzorci = null;
	ArrayList<Byte[]> results = null;
	
	public IReadOnlyDataset(String name) throws FileNotFoundException {
		File test = new File(name);
		uzorci = new ArrayList<Double[]>();
		results = new ArrayList<Byte[]>();
		Scanner input = new Scanner(test);
		String st;
		
		while(input.hasNextLine()) {
			//(5.1,3.5,1.4,0.2):(1,0,0)
			Double[] u = new Double[4];
			Byte[] r = new Byte[3];
			st = input.nextLine();
			String[] val = st.split(",");
			u[0] = Double.parseDouble(val[0].substring(1));
			u[1] = Double.parseDouble(val[1]);
			u[2] = Double.parseDouble(val[2]);
			u[3] = Double.parseDouble(val[3].substring(0,val[3].indexOf(")")));
			uzorci.add(u);
			r[0] = Byte.parseByte(val[3].substring(val[3].indexOf("(")+1,val[3].indexOf("(")+2));
			r[1] = Byte.parseByte(val[4]);
			r[2] = Byte.parseByte(val[5].substring(0,1));
			results.add(r);
		}
		
		input.close();
	}
	
	public int numOfSets() {
		return this.uzorci.size();
	}
	
	public int numOfEntries() {
		return this.uzorci.get(0).length;
	}
	
	public int numOfExits() {
		return this.results.get(0).length;
	}
	
	public Double[] getEntry(int i) {
		return this.uzorci.get(i);
	}
	
	public Byte[] getExit(int i) {
		return this.results.get(i);
	}

}
