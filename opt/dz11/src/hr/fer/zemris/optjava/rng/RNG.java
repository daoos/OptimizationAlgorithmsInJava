package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class RNG {
	
	private static IRNGProvider rngProvider;
	
	
	static {
		// Stvorite primjerak razreda Properties;
		Properties prop = new Properties();
	
	// Nad Classloaderom razreda RNG tražite InputStream prema resursu rng-config.properties
		InputStream inStream = ClassLoader.getSystemResourceAsStream("rng-config.properties");
	// recite stvorenom objektu razreda Properties da se uèita podatcima iz tog streama.
		try {
			prop.load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	// Dohvatite ime razreda pridruženo kljuèu "rng-provider"; zatražite Classloader razreda
		String name = prop.getProperty("rng-provider");
	// RNG da uèita razred takvog imena i nad dobivenim razredom pozovite metodu newInstance()
		ClassLoader cls = ClassLoader.getSystemClassLoader();
		Class<IRNG> someClass = null;
		try {
			someClass = (Class<IRNG>) cls.loadClass(name);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	// kako biste dobili jedan primjerak tog razreda; castajte ga u IRNGProvider i zapamtite.
		try {
			rngProvider = (IRNGProvider)someClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}
