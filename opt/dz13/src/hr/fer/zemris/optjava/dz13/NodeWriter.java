package hr.fer.zemris.optjava.dz13;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class NodeWriter {

	public static void save_best(Node best, String best_unit_save_path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(best_unit_save_path,"UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(best.toString());
		writer.close();
	}

}
