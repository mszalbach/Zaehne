package de.fh.aachen.dental.imagej.hsbStrategy;

import ij.ImagePlus;
import ij.process.ColorProcessor;

import java.awt.*;

/**
 * Created by Marcel on 19.04.2015.
 */
public class IntegerStrategy implements HSBStrategy {

    public static final double maxValue = 255.0;


    @Override
    public Object[] execute(ImagePlus image) {
        int dim = image.getHeight() * image.getWidth();
        int[] hue = new int[dim];
        int[] saturation = new int[dim];
        int[] brightness = new int[dim];
        int c, r, g, b;
        float[] hsb = new float[3];
        ColorProcessor colorProcessor = image.getProcessor().convertToColorProcessor();
        for (int i = 0; i < dim; i++) {
            c = colorProcessor.get(i);
            r = (c & 0xff0000) >> 16;
            g = (c & 0xff00) >> 8;
            b = c & 0xff;
            hsb = Color.RGBtoHSB(r, g, b, hsb);
            hue[i] = (int) (hsb[0] * maxValue);
            saturation[i] = (int) (hsb[1] * maxValue);
            brightness[i] = (int) (hsb[2] * maxValue);
        }
        return new Object[]{hue, saturation, brightness};
    }
}
