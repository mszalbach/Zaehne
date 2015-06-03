package de.fh.aachen.dental.imagej.filter;

import de.fh.aachen.dental.imagej.converter.Converter;
import ij.ImagePlus;
import ij.process.BinaryProcessor;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

/**
 * 
 * @author Daniel Wirtz
 *
 */
public class ReflectionFilter implements Converter {

	private static final int STEPWIDTH = 10;
	private static final int STEPHEIGHT = 10;
	private static final int UPPERBOUND_OF_REFLECTION = 225;
	ImagePlus originalImage;
	int pixelWithReflectionCount = 0;

	public ReflectionFilter() {

	}

	/**
	 * Duplicates the original image and removes all reflections.
	 * 
	 * @return new image without reflections.
	 */
	public ImagePlus removeReflections() {
		ImagePlus clonedImage = this.originalImage.duplicate();

		ImageProcessor originalProcessor = this.originalImage.getProcessor();
		ImageProcessor clonedProcessor = clonedImage.getProcessor();

		int[] rgb = new int[3];

		// dummy to enter while-loop
		pixelWithReflectionCount = 1;

		// stop when there are no reflections anymore
		while (pixelWithReflectionCount != 0) {
			pixelWithReflectionCount = 0;

			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				for (int x = 0; x < originalProcessor.getWidth(); x++) {

					originalProcessor.getPixel(x, y, rgb);

					int[] rgbAdapted = adaptivLocalRgbValues(x, y, rgb, originalProcessor);

					if (rgbAdapted != null) {
						clonedProcessor.putPixel(x, y, rgbAdapted);
					}

				}
			}

			this.originalImage = clonedImage;
			originalProcessor = this.originalImage.getProcessor();
			clonedProcessor = clonedImage.getProcessor();

		}

		clonedImage.setTitle(originalImage.getTitle() + " without reflections");
		return clonedImage;
	}

	/**
	 * Generates a binary image. All reflections are white, the others are black
	 * pixels.
	 * 
	 * @return binary image-representation of reflections
	 */
	public ImagePlus getBinaryImageWithReflections() {
		ImagePlus image = this.originalImage.duplicate();

		ImageProcessor ip = image.getProcessor();

		int[] rgb = new int[3];
		for (int y = 0; y < ip.getHeight(); y++) {
			for (int x = 0; x < ip.getWidth(); x++) {

				ip.getPixel(x, y, rgb);

				if (isReflection(rgb)) {
					rgb[0] = 255;
					rgb[1] = 255;
					rgb[2] = 255;
				} else {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
				}

				ip.putPixel(x, y, rgb);
			}
		}

		ip = ip.convertToByte(false);
		BinaryProcessor bp = new BinaryProcessor(new ByteProcessor(ip.createImage()));
		bp.autoThreshold();
		ImagePlus binaryImage = new ImagePlus("Binary Image with reflections", bp);

		return binaryImage;
	}

	/**
	 * Creates a new image in which are only the reflections. All other pixel
	 * are black.
	 * 
	 * @return image with reflection
	 */
	@Deprecated
	public ImagePlus getReflectionImage() {
		ImagePlus reflectionImage = originalImage.duplicate();

		ImageProcessor ip = reflectionImage.getProcessor();

		int[] rgb = new int[3];
		for (int y = 0; y < ip.getHeight(); y++) {
			for (int x = 0; x < ip.getWidth(); x++) {

				ip.getPixel(x, y, rgb);

				// only keep reflections
				if (!isReflection(rgb)) {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
				}

				ip.putPixel(x, y, rgb);
			}
		}

		return reflectionImage;
	}

	/**
	 * 
	 * @param x
	 *            - x-coordinate of image
	 * @param y
	 *            - y-coordinate of image
	 * @param rgb
	 *            - rgb-values at coordinate (x,y)
	 * @param ip
	 *            - imageprocessor of current image
	 * @return averaged rgb-values at coordinate (x,y)
	 */
	private int[] adaptivLocalRgbValues(int x, int y, int[] rgb, ImageProcessor ip) {

		int rgbAdapted[] = new int[3];
		int meanValue = 0;
		int rgbSum[] = new int[3];

		// when current pixel is a reflection
		if (isReflection(rgb)) {

			// local adaption
			for (int y_local = y - 5; y_local < (y - 5) + STEPHEIGHT; y_local++) {

				for (int x_local = x - 5; x_local < (x - 5) + STEPWIDTH; x_local++) {

					int[] tmpArray = ip.getPixel(x_local, y_local, rgbAdapted);

					// ignore pixel which are out of bounds
					if (!pixelIsOutOfBounds(tmpArray)) {

						if (!isReflection(rgbAdapted)) {

							// summarize rgb values
							for (int i = 0; i < 3; i++) {
								rgbSum[i] += rgbAdapted[i];
							}
							meanValue++;
						}

					}
				}
			}

			if (meanValue == 0)
				return null;

			for (int i = 0; i < 3; i++) {
				rgbAdapted[i] = rgbSum[i] / meanValue;
			}

			pixelWithReflectionCount++;

			return rgbAdapted;
		}

		return null;
	}

	/**
	 * Checks whether the given rgb-values have coordinates (x,y) that are out
	 * of the image. In this case the rgb-values are zero.
	 * 
	 * @param rgb
	 *            - rgb-values
	 * @return true, if rgb-values are zero, otherwise false
	 */
	boolean pixelIsOutOfBounds(int[] rgb) {
		return rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0;
	}

	/**
	 * Checks whether the given rgb-values at coordinates (x,y) is a reflection.
	 * A pixel is a reflection when there is at least one value (r,g,b) greater
	 * equals than {@link ReflectionFilter#UPPERBOUND_OF_REFLECTION}.
	 * 
	 * @param rgb
	 *            - rgb-values
	 * @return true, if this rgb-values represents a reflection, otherwise false
	 */
	boolean isReflection(int[] rgb) {
		boolean oneGreaterEqualsThanUpperBound = rgb[0] >= UPPERBOUND_OF_REFLECTION
				|| rgb[1] >= UPPERBOUND_OF_REFLECTION || rgb[2] >= UPPERBOUND_OF_REFLECTION;

		return (oneGreaterEqualsThanUpperBound);
	}

	@Override
	public ImagePlus convert(ImagePlus image) {
		this.originalImage = image;

		// getBinaryImageWithReflections().show();

		return removeReflections();
	}

}
