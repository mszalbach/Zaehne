package de.fh.aachen.zaehne;

import de.fh.aachen.zaehne.imagej.utils.AwtHSBImage;
import de.fh.aachen.zaehne.imagej.utils.HSBImageInterface;
import de.fh.aachen.zaehne.imagej.utils.ImageJHSBImage;
import de.fh.aachen.zaehne.imagej.utils.IntHSBImage;
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

        List<HSBImageInterface> images = new ArrayList<HSBImageInterface>();
        images.add(new ImageJHSBImage(originalImage));
        images.add(new AwtHSBImage(originalImage));
        images.add(new IntHSBImage(originalImage));

        for (HSBImageInterface image : images) {
            ImagePlus hueImage = image.getHSBStack();
            hueImage.show();
            // hueImage.getProcessor().setAutoThreshold(AutoThresholder.Method.Moments, true);
            //hueImage.getProcessor().autoThreshold();
        }
        IJ.showMessage("Done");

    }
}
