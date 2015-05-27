package de.fh.aachen.dental.imagej.filter;

import de.fh.aachen.dental.imagej.converter.Converter;
import ij.IJ;
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
	int changedPixelCount = 0;

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
		for (int y = 0; y < originalProcessor.getHeight(); y++) {
			for (int x = 0; x < originalProcessor.getWidth(); x++) {

				originalProcessor.getPixel(x, y, rgb);

				int[] rgbAdapted = adaptivLocalRgbValues(x, y, rgb, originalProcessor);

				if (rgbAdapted != null) {
					clonedProcessor.putPixel(x, y, rgbAdapted);
				}

			}
		}

		if (changedPixelCount != 0) {
			changedPixelCount = 0;
			this.originalImage = clonedImage;
			removeReflections();
		} else {

			clonedImage.setTitle(originalImage.getTitle() + " without reflections");
			// IJ.log("Changed Pixels: " + changedPixelCount + "\n\nDone");
			clonedImage.show();
		}

		return clonedImage;
	}

	/**
	 * Generates a binary image. All reflections are white, the others black
	 * pixels.
	 * 
	 * @return binary image-represenation of reflections
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

		IJ.log("created binary image with reflections");
		binaryImage.show();
		return binaryImage;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param rgb
	 * @param ip
	 * @return
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

			changedPixelCount++;

			return rgbAdapted;
		}

		return null;
	}

	boolean pixelIsOutOfBounds(int[] arr) {
		return arr[0] == 0 && arr[1] == 0 && arr[2] == 0;
	}

	boolean isReflection(int[] rgb) {
		boolean oneGreaterEqualsThanUpperBound = rgb[0] >= UPPERBOUND_OF_REFLECTION
				|| rgb[1] >= UPPERBOUND_OF_REFLECTION || rgb[2] >= UPPERBOUND_OF_REFLECTION;

		return (oneGreaterEqualsThanUpperBound);
	}

	@Override
	public ImagePlus convert(ImagePlus image) {
		this.originalImage = image;

		return removeReflections();
	}

}
