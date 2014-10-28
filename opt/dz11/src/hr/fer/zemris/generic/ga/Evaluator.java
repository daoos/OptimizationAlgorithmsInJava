package hr.fer.zemris.generic.ga;

import hr.fer.zemris.art.GrayScaleImage;

public class Evaluator<T extends Number> implements IGAEvaluator<T> {

	private GrayScaleImage template;
	private GrayScaleImage image=null;

	public Evaluator(GrayScaleImage template) {
		super();
		this.template = template;
	}

	public GrayScaleImage draw(GASolution<T> p, GrayScaleImage image) {
		if (image == null) {
			image = new GrayScaleImage(template.getWidth(), template.getHeight());
		}
	
		Number[] pdata = p.getData();
		byte backgroundColor = pdata[0].byteValue();
		image.clear(backgroundColor);
		int n = (pdata.length - 1) / 5;
		int index = 1;
		for (int i = 0; i < n; i++) {
			image.rectangle(pdata[index].byteValue(), pdata[index + 1].byteValue(), pdata[index + 2].byteValue(),
					pdata[index + 3].byteValue(), pdata[index + 4].byteValue());
			index += 5;
		}
		return image;
	}

	@Override
	public void evaluate(GASolution<T> p) {
		// Ovo nije višedretveno sigurno!
		if (image == null) {
			image = new GrayScaleImage(template.getWidth(), template.getHeight());
		}
		draw(p, image);
		
		byte[] data = image.getData();
		byte[] tdata = template.getData();
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		double error = 0;
		int index2 = 0;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				error += Math.abs(((int) data[index2] & 0xFF)
						- ((int) tdata[index2] & 0xFF));
				index2++;
			}
		}
		
		p.fitness = error;
	}
}
