package de.fh.aachen.zaehne;

import de.fh.aachen.zaehne.imagej.utils.*;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel on 16.04.2015.
 */
public class Zaehne implements PlugInFilter {

    private ImagePlus originalImage;
    private ImageStack hueStack;

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        this.originalImage = imagePlus;
        return DOES_ALL;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {

        List<HSBImage> images = new ArrayList<HSBImage>();
        images.add(new HSBImage("ImageJ HSB", originalImage, new ImageJStrategy()));
        images.add(new HSBImage("AWT HSB", originalImage, new FloatHSB()));
        images.add(new HSBImage("INT HSB", originalImage, new IntHSB()));

        for (HSBImage image : images) {
            ImagePlus hueImage = image.getHSBStack();
            hueImage.show();
            // hueImage.getProcessor().setAutoThreshold(AutoThresholder.Method.Moments, true);
            //hueImage.getProcessor().autoThreshold();
        }
        IJ.showMessage("Done");

    }
}
