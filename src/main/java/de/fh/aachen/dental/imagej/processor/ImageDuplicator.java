package de.fh.aachen.dental.imagej.processor;

import ij.ImagePlus;

/**
 * Created by marcel on 21.05.15.
 */
public class ImageDuplicator implements Preprocessor {

    @Override
    public ImagePlus process(ImagePlus image) {
        return image.duplicate();
    }
}
