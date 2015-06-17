package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.ImageResize;
import de.fh.aachen.dental.imagej.processor.Preprocessor;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageResizeComponent extends AbstractConverterComponent {

    public ImageResizeComponent() {
        super("Image Resize");
    }

    @Override
    public Preprocessor getPreprocessor() {
        return new ImageResize(1000, 1000, 2);
    }
}
