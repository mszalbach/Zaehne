package de.fh.aachen.dental.imagej.converter;

import edu.mines.jtk.dsp.Conv;
import ij.ImagePlus;
import ij.plugin.Duplicator;
import ij.process.ImageConverter;

/**
 * Created by marcel on 21.05.15.
 */
public class ImageDuplicator implements Converter {

    @Override
    public ImagePlus convert(ImagePlus image) {
        Duplicator duplicator = new Duplicator();
        return duplicator.run(image);
    }
}
