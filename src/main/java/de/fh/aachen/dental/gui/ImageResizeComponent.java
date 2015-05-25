package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.ImageResize;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageResizeComponent extends ConverterComponent {

    public ImageResizeComponent() {
        super("Image Resize");
    }

    @Override
    public Converter getConverter() {
        return new ImageResize(1000, 1000, 2);
    }
}
