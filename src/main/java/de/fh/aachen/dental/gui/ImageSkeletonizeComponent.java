package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.ImageSkeletonize;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageSkeletonizeComponent extends AbstractConverterComponent {

    public ImageSkeletonizeComponent() {
        super("Skeletonize");
    }

    @Override
    public Converter getConverter() {
        return new ImageSkeletonize();
    }
}
