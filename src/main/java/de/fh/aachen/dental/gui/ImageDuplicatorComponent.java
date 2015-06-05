package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.ImageDuplicator;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageDuplicatorComponent extends AbstractConverterComponent {

    public ImageDuplicatorComponent() {
        super("Image Duplicator");
    }

    @Override
    public Converter getConverter() {
        return new ImageDuplicator();
    }
}
