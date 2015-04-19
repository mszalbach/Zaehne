package de.fh.aachen.dental;

import de.fh.aachen.dental.imagej.HSBImage;
import de.fh.aachen.dental.imagej.hsbStrategy.FloatStrategy;
import de.fh.aachen.dental.imagej.hsbStrategy.ImageJStrategy;
import de.fh.aachen.dental.imagej.hsbStrategy.IntegerStrategy;
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
        images.add(new HSBImage("AWT HSB", originalImage, new FloatStrategy()));
        images.add(new HSBImage("INT HSB", originalImage, new IntegerStrategy()));

        for (HSBImage image : images) {
            ImagePlus hueImage = image.getHSBStack();
            hueImage.show();
            // hueImage.getProcessor().setAutoThreshold(AutoThresholder.Method.Moments, true);
            //hueImage.getProcessor().autoThreshold();
        }
        IJ.showMessage("Done");

    }
}
