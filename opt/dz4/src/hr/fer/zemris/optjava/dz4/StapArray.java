package hr.fer.zemris.optjava.dz4;

public class StapArray {

	public int value;
	public int number;
	public StapArray next=null;
	public StapArray(int value,int number) {
		this.value = value;
		this.number = number;
	}
	
	public String toString(){
		String st="";
		st +=Integer.toString(value)+" ";
		st +=Integer.toString(number)+" ";
		return st;
	}
	
	public boolean has(){
		return number>0;
	}
	
	public void takeOne(){
		this.number--;
	}

}
