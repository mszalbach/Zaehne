package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.Preprocessor;
import de.fh.aachen.dental.imagej.processor.SharpenImage;

/**
 * Created by foobar on 25.05.15.
 */
public class SharpenComponent extends AbstractConverterComponent {

    public SharpenComponent() {
        super("Sharpen");
    }

    @Override
    public Preprocessor getPreprocessor() {
        return new SharpenImage();
    }
}
