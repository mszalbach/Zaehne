package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public interface IConverterComponent {
    boolean isActive();

    void setActive(boolean b);

    Converter getConverter();

    JComponent getComponent();
}
