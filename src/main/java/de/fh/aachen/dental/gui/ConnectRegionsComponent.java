package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.ConnectRegions;
import de.fh.aachen.dental.imagej.converter.Converter;

/**
 * Created by foobar on 25.05.15.
 */
public class ConnectRegionsComponent extends ConverterComponent {

    public ConnectRegionsComponent() {
        super("Connect Regions");
    }

    @Override
    public Converter getConverter() {
        return new ConnectRegions();
    }
}
