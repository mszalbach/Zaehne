package de.fh.aachen.dental.imagej.converter;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.process.ImageProcessor;

import java.io.File;

/**
 * Created by marcel on 21.05.15.
 */
public class ImageResize implements Converter {

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
    public ImagePlus convert(ImagePlus image) {

        ImageProcessor ip = image.getProcessor();
        ip.setInterpolationMethod(ImageProcessor.BILINEAR);

        while (imageToLarge(ip)) {
            ip = ip.bin(factor);
        }

        return new ImagePlus(image.getTitle() + " resized", ip.getBufferedImage());
    }

    private boolean imageToLarge(ImageProcessor image) {
        return image.getHeight() > maxHeight || image.getWidth() > maxWidth;
    }
}
