package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.ImageTo8Bit;
import de.fh.aachen.dental.imagej.processor.Preprocessor;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageTo8BitComponent extends AbstractConverterComponent {

    public ImageTo8BitComponent() {
        super("8-bit");
    }

    @Override
    public Preprocessor getPreprocessor() {
        return new ImageTo8Bit();
    }
}
