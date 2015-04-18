package de.fh.aachen.zaehne;

import de.fh.aachen.zaehne.imagej.converter.HueConverter;
import de.fh.aachen.zaehne.imagej.utils.HSBImage;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.filter.PlugInFilter;
import ij.process.AutoThresholder;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

import java.awt.image.IndexColorModel;
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

        HSBImage hsbImage = new HSBImage(originalImage);

        ImagePlus hueImage = hsbImage.getHueImage();
       // hueImage.getProcessor().setAutoThreshold(AutoThresholder.Method.Moments, true);
        //hueImage.getProcessor().autoThreshold();

        hueImage.show();

        ImagePlus hueImageC = new HueConverter().convert(hsbImage);
        new ImageConverter(hueImageC).convertToGray8();
        hueImageC.show();

        IJ.showMessage("Done");

    }
}
