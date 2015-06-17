package de.fh.aachen.dental.imagej.processor;

import de.fh.aachen.dental.imagej.imageEdge.ImageEdge;
import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by marcel on 03.06.15.
 */
public class ImageEdgeProcessor implements Preprocessor {

	private final double radius;
	private final float alpha;
	private final float upper;
	private final float lower;

	public ImageEdgeProcessor(double radius, float alpha, float upper, float lower) {

		this.radius = radius;
		this.alpha = alpha;
		this.upper = upper;
		this.lower = lower;
	}

	@Override
	public ImagePlus process(ImagePlus image) {
		ImagePlus dup = image.duplicate();
		ImageProcessor tmp1 = ImageEdge.areaEdge(dup.getProcessor(), radius, alpha, upper, lower);
		dup.setProcessor(tmp1);
		return dup;
	}
}
