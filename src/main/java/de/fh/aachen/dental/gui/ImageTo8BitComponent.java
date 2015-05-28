package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.ImageTo8Bit;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageTo8BitComponent extends ConverterComponent {

    public ImageTo8BitComponent() {
        super("8-bit");
    }

    @Override
    public Converter getConverter() {
        return new ImageTo8Bit();
    }
}
