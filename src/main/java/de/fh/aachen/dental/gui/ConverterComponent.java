package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public abstract class ConverterComponent implements IConverterComponent {

    protected JCheckBox checkBox;
    protected JPanel panel;

    public ConverterComponent(String name) {
        this.checkBox = new JCheckBox(name);
        this.panel = new JPanel(new MigLayout());
        panel.add(checkBox, "wrap");
    }

    protected JTextField generateTextField(String name) {
        JTextField textField = new JTextField();
        textField.setColumns(5);
        JLabel label = new JLabel(name);
        label.setLabelFor(textField);

        panel.add(label);
        panel.add(textField);

        return textField;
    }

    protected void addIndent() {
        panel.add(new JSeparator(),"gap 100px");
    }


    @Override
    public boolean isActive() {
        return checkBox.isSelected();
    }

    @Override
    public void clear() {
        checkBox.setSelected(false);
    }

    @Override
    public abstract Converter getConverter();

    @Override
    public JComponent getComponent() {
        return panel;
    }


}
