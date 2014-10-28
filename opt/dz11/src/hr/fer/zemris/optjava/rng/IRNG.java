package hr.fer.zemris.optjava.rng;

public interface IRNG {

	/**
	* Vra�a decimalni broj iz intervala [0,1) prema uniformnoj distribuciji.
	*
	* @return slu�ajno generirani decimalni broj
	*/
	public double nextDouble();
	
	/**
	* Vra�a decimalni broj iz intervala [min,max) prema uniformnoj distribuciji.
	*
	* @param min donja granica intervala (uklju�iva)
	* @param max gornja granica intervala (isklju�iva)
	*
	* @return slu�ajno generirani decimalni broj
	*/
	public double nextDouble(double min, double max);
	/**
	* Vra�a decimalni broj iz intervala [0,1) prema uniformnoj distribuciji.
	*
	* @return slu�ajno generirani decimalni broj
	*/
	public float nextFloat();
	/**
	* Vra�a decimalni broj iz intervala [min,max) prema uniformnoj distribuciji.
	*
	* @param min donja granica intervala (uklju�iva)
	* @param max gornja granica intervala (isklju�iva)
	*
	* @return slu�ajno generirani decimalni broj
	*/
	public float nextFloat(float min, float max);
	/**
	* Vra�a cijeli broj iz intervala svih mogu�ih cijelih brojeva prema uniformnoj distribuciji.
	*
	* @return slu�ajno generirani cijeli broj
	*/
	public int nextInt();
	/**
	* Vra�a cijeli broj iz intervala [min,max) prema uniformnoj distribuciji.
	*
	* @param min donja granica intervala (uklju�iva)
	* @param max gornja granica intervala (isklju�iva)
	*
	* @return slu�ajno generirani cijeli broj
	*/
	public int nextInt(int min, int max);
	/**
	* Vra�a slu�ajno generiranu boolean vrijednost. Vrijednosti se izvla�e
	* iz uniformne distribucije.
	*
	* @return slu�ajno generirani boolean
	*/
	public boolean nextBoolean();
	/**
	* Vra�a decimalni broj iz normalne distribucije s parametrima (0,1).
	*
	* @return slu�ajno generirani decimalni broj
	*/
	public double nextGaussian();
	
}
