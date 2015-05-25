package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.FJEdgeDetection;

/**
 * Created by foobar on 25.05.15.
 */
public class FJEdgeDetectionComponent extends ConverterComponent {
    public FJEdgeDetectionComponent() {
        super("FeatureJ Edge Detection");
    }

    @Override
    public Converter getConverter() {
        return new FJEdgeDetection();
    }
}
