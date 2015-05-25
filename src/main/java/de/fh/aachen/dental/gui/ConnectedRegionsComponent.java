package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.ConnectedRegions;
import de.fh.aachen.dental.imagej.converter.Converter;

/**
 * Created by foobar on 25.05.15.
 */
public class ConnectedRegionsComponent extends ConverterComponent {

    public ConnectedRegionsComponent() {
        super("Find Connected Regions");
    }

    @Override
    public Converter getConverter() {
        return new ConnectedRegions();
    }
}
