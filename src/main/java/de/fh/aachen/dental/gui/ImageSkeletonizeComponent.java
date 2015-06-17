package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.ImageSkeletonize;
import de.fh.aachen.dental.imagej.processor.Preprocessor;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageSkeletonizeComponent extends AbstractConverterComponent {

    public ImageSkeletonizeComponent() {
        super("Skeletonize");
    }

    @Override
    public Preprocessor getPreprocessor() {
        return new ImageSkeletonize();
    }
}
