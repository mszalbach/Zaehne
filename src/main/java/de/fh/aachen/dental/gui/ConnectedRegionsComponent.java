package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.ConnectedRegions;
import de.fh.aachen.dental.imagej.converter.Converter;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public class ConnectedRegionsComponent extends AbstractConverterComponent {

    private JCheckBox keepOnlyLargestRegionCheckbox;

    public ConnectedRegionsComponent() {
        super("Find Connected Regions");
        addIndent();

        keepOnlyLargestRegionCheckbox = addCheckbox("keep only largest Region");


        setDefaults();
    }

    @Override
    public void clear() {
        super.clear();
        setDefaults();
    }

    private void setDefaults() {
        keepOnlyLargestRegionCheckbox.setSelected(false);
    }

    @Override
    public Converter getConverter() {
        return new ConnectedRegions(keepOnlyLargestRegionCheckbox.isSelected());
    }
}
