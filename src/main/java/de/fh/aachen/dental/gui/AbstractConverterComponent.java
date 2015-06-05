package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public abstract class AbstractConverterComponent implements IConverterComponent {

    protected JCheckBox checkBox;
    protected JPanel panel;

    public AbstractConverterComponent(String name) {
        this.checkBox = new JCheckBox(name);
        this.panel = new JPanel(new MigLayout());
        panel.add(checkBox, "wrap");
    }

    protected JTextField addTextField(String name) {
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

    protected JCheckBox addCheckbox(String name) {
        JCheckBox checkBox = new JCheckBox(name);
        panel.add(checkBox);
        return checkBox;
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
