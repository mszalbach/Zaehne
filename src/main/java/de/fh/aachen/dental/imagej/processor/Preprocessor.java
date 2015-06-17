package de.fh.aachen.dental.imagej.processor;

import ij.ImagePlus;

/**
 * Created by marcel on 21.05.15.
 */
public interface Preprocessor {
    ImagePlus process(ImagePlus image);
}