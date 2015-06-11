package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.SharpenImage;

/**
 * Created by foobar on 25.05.15.
 */
public class SharpenComponent extends AbstractConverterComponent {

    public SharpenComponent() {
        super("Sharpen");
    }

    @Override
    public Converter getConverter() {
        return new SharpenImage();
    }
}
