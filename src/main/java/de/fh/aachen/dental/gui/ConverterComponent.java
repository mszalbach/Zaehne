package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public abstract class ConverterComponent implements IConverterComponent {

    private JCheckBox checkBox;
    private JPanel panel;

    public ConverterComponent(String name) {
        this.checkBox = new JCheckBox(name);
        this.panel = new JPanel();
        panel.add(checkBox);
    }

    @Override
    public boolean isActive() {
        return checkBox.isSelected();
    }

    @Override
    public void setActive(boolean b) {
        checkBox.setSelected(b);
    }

    @Override
    public abstract Converter getConverter();

    @Override
    public JComponent getComponent() {
        return panel;
    }


}
