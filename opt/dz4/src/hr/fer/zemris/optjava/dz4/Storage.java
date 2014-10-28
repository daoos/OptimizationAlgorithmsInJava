package hr.fer.zemris.optjava.dz4;

public class Storage {

	public static StapArray first=null;
	private int[] podaci = null;
	
	public String toString(){
		String st="";
		StapArray t= first;
		while(t!=null) {
			st+=t+" ";
			t = t.next;
		}
		return st;
	}
	
	public Storage(int[] stapovi) {
		this.podaci = stapovi;
		int n=0,i=stapovi.length-1;
		boolean b=true,c=true;
		StapArray next=null;
		while(i>0){
			n =1;
			while(i>1&&stapovi[i]==stapovi[i-1]) {
				n++;
				i--;
			}
			if(b) {
				b = false;
				first = new StapArray(stapovi[i],n);
			}else{
				if(c){
					first.next = new StapArray(stapovi[i],n);
					c = false;
					next = first.next;
				}else {
					next.next = new StapArray(stapovi[i],n);
					next = next.next;
				}
				
			}
			i--;
		}
	}
	
	public Storage clone(){
		return new Storage(this.podaci);
	}

}
