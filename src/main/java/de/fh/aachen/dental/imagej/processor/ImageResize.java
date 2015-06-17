package de.fh.aachen.dental.imagej.processor;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by marcel on 21.05.15.
 */
public class ImageResize implements Preprocessor {

    private final int maxHeight;
    private final int maxWidth;
    private int factor;

    public ImageResize(int maxHeight, int maxWidth, int factor) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;

        if(factor <= 1 ) {
            throw new IllegalArgumentException("Factor must be >1 to resize anything.");
        }
        this.factor = factor;
    }

    @Override
    public ImagePlus process(ImagePlus image) {

        ImageProcessor ip = image.getProcessor();
        ip.setInterpolationMethod(ImageProcessor.BILINEAR);

        while (imageToLarge(ip)) {
            ip = ip.bin(factor);
        }

        image.setProcessor(ip);
        image.setTitle(image.getTitle() + " Resize");
        return image;
    }

    private boolean imageToLarge(ImageProcessor image) {
        return image.getHeight() > maxHeight || image.getWidth() > maxWidth;
    }
}
