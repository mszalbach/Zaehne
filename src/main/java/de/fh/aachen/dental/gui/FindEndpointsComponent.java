package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.FindEndpoints;
import de.fh.aachen.dental.imagej.converter.Converter;

import javax.swing.*;

/**
 * Created by foobar on 04.06.15.
 */
public class FindEndpointsComponent extends AbstractConverterComponent {

    private JCheckBox shouldConnectCheckbox;

    public FindEndpointsComponent() {
        super("Find Endpoints");

        addIndent();

        shouldConnectCheckbox = addCheckbox("Should connect Endpoints");
        setDefaults();
    }


    @Override
    public void clear() {
        super.clear();
        setDefaults();
    }

    private void setDefaults() {
        shouldConnectCheckbox.setSelected(false);
    }

    @Override
    public Converter getConverter() {
        return new FindEndpoints(shouldConnectCheckbox.isSelected());
    }
}
