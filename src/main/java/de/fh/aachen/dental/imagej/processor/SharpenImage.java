package de.fh.aachen.dental.imagej.processor;

import ij.ImagePlus;

/**
 * Created by marcel on 21.05.15.
 */
public class SharpenImage implements Preprocessor {


    @Override
    public ImagePlus process(ImagePlus image) {
        image.getProcessor().sharpen();
        return image;
    }
}
