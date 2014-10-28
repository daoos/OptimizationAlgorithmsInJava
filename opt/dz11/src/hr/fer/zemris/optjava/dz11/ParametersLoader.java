package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class ParametersLoader {

	public static void loadParameters(String parametersPath, Mutation mutation) {
		File parameters = new File(parametersPath);
		Scanner scan = null;
		try {
			scan = new Scanner(parameters);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		{
			String st = scan.nextLine();
			mutation.chance = Double.parseDouble(st);
			//System.out.println(mutation.chance);
		}
			
		
		{
			String st = scan.nextLine();
			mutation.range = Integer.parseInt(st);
			//System.out.println(mutation.range);
		}
			

		scan.close();
	}
	
}
