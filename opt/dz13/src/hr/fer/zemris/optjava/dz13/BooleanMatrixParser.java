package hr.fer.zemris.optjava.dz13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BooleanMatrixParser{
	
	static public boolean[][] parse (String file_path) {
		File map_file = new File(file_path);
		Scanner reader = null;
		try {
			reader = new Scanner(map_file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String configuration = reader.nextLine();
		
		int width = Integer.parseInt(configuration.split("x")[0]);
		int heigth = Integer.parseInt(configuration.split("x")[1]);
		
		boolean [][] map = new boolean[heigth][width];
		String st=null;
		
		for(int i = 0;i<width;i++) {
			st = reader.next();
			
			for(int j = 0;j<heigth;j++) {
		
				if(st.charAt(j)== '0' || st.charAt(j)== '.') {
					map[i][j] = false;
				}else{
					map[i][j] = true;
				}
			}
		}
		
		reader.close();
		
		return map;
	}

}
