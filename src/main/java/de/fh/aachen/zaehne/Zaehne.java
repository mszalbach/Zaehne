package de.fh.aachen.zaehne;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.filter.PlugInFilter;
import ij.process.AutoThresholder;
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

        int dim = originalImage.getWidth() * originalImage.getHeight();
        //(range -180 => +180
        //TODO convert hue to 0-255 and create image
        byte[] hue = new byte[dim];
        byte[] saturation = new byte[dim];
        byte[] brightness = new byte[dim];

        originalImage.getProcessor().convertToColorProcessor().getHSB(hue, saturation, brightness);

        ImagePlus hueImage = new ImagePlus("Test", getSaturationStack());
        hueImage.getProcessor().setAutoThreshold(AutoThresholder.Method.Moments, true);
        hueImage.getProcessor().autoThreshold();
        //hueImage.getProcessor().filter(ImageProcessor.MAX);

        hueImage.show();

        IJ.showMessage("Done");

    }

    private List<ImageStack> getHSBStacks() {

        List<ImageStack> hsvStacks = new ArrayList<ImageStack>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        byte[] H = new byte[width * height];
        byte[] S = new byte[width * height];
        byte[] B = new byte[width * height];
        originalImage.getProcessor().convertToColorProcessor().getHSB(H, S, B);
        IndexColorModel cm = originalImage.getProcessor().convertToColorProcessor().getDefaultColorModel();

        ImageStack hueStack = new ImageStack(width, height, cm);
        hueStack.addSlice("Hue", H);
        hsvStacks.add(hueStack);

        ImageStack saturationStack = new ImageStack(width, height, cm);
        saturationStack.addSlice("Saturation", S);
        hsvStacks.add(saturationStack);

        ImageStack brightnessStack = new ImageStack(width, height, cm);
        brightnessStack.addSlice("Brightness", B);
        hsvStacks.add(brightnessStack);



        return hsvStacks;
    }

    private ImageStack getHueStack() {
        return getHSBStacks().get(0);
    }

    private ImageStack getSaturationStack() {
        return getHSBStacks().get(1);
    }

    private ImageStack getBrightnessStack() {
        return getHSBStacks().get(2);
    }
}
