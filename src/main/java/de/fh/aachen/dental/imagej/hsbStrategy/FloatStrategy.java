package de.fh.aachen.dental.imagej.hsbStrategy;

import ij.ImagePlus;
import ij.process.ColorProcessor;

import java.awt.*;

/**
 * Created by Marcel on 19.04.2015.
 */
public class FloatStrategy implements HSBStrategy {

    @Override
    public Object[] execute(ImagePlus image) {
        int dim = image.getHeight() * image.getWidth();
        float[] hue = new float[dim];
        float[] saturation = new float[dim];
        float[] brightness = new float[dim];
        int c, r, g, b;
        float[] hsb = new float[3];
        ColorProcessor colorProcessor = image.getProcessor().convertToColorProcessor();
        for (int i = 0; i < dim; i++) {
            c = colorProcessor.get(i);
            r = (c & 0xff0000) >> 16;
            g = (c & 0xff00) >> 8;
            b = c & 0xff;
            hsb = Color.RGBtoHSB(r, g, b, hsb);
            hue[i] = hsb[0];
            saturation[i] = hsb[1];
            brightness[i] = hsb[2];
        }
        return new Object[]{hue, saturation, brightness};
    }
}
