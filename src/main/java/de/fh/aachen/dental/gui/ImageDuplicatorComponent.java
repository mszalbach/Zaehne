package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.ImageDuplicator;
import de.fh.aachen.dental.imagej.processor.Preprocessor;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageDuplicatorComponent extends AbstractConverterComponent {

    public ImageDuplicatorComponent() {
        super("Image Duplicator");
    }

    @Override
    public Preprocessor getPreprocessor() {
        return new ImageDuplicator();
    }
}
