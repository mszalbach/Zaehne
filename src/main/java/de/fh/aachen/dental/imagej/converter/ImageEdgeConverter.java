package de.fh.aachen.dental.imagej.converter;

import de.fh.aachen.dental.imagej.imageEdge.ImageEdge;
import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by marcel on 03.06.15.
 */
public class ImageEdgeConverter implements Converter {

    private final double radius;
    private final float alpha;
    private final float upper;
    private final float lower;

    public ImageEdgeConverter(double radius,
                              float alpha,
                              float upper,
                              float lower) {

        this.radius = radius;
        this.alpha = alpha;
        this.upper = upper;
        this.lower = lower;
    }


    @Override
    public ImagePlus convert(ImagePlus image) {
        ImageProcessor tmp1 = ImageEdge.areaEdge(image.getProcessor(), radius, alpha, upper, lower);
        image.setProcessor(tmp1);
        return image;
    }
}
