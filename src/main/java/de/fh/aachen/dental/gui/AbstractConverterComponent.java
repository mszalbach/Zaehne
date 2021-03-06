package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.Preprocessor;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by foobar on 25.05.15.
 */
public abstract class AbstractConverterComponent implements PreprocessorComponent {

    protected JCheckBox activationCheckBox;
    protected JPanel panel;

    public AbstractConverterComponent(String name) {
        this.activationCheckBox = new JCheckBox(name);
        this.panel = new JPanel(new MigLayout());
        panel.add(activationCheckBox, "wrap");
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
        panel.add(new JSeparator(), "gap 100px");
    }

    protected JCheckBox addCheckbox(String name) {
        final JCheckBox checkBox = new JCheckBox(name);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    activationCheckBox.setSelected(true);
                }
            }
        });
        panel.add(checkBox);
        return checkBox;
    }

    protected <T> JComboBox<JComboBoxEntry<T>> addComboBox(JComboBoxEntry<T>[] entries) {
        JComboBox<JComboBoxEntry<T>> comboBox = new JComboBox<JComboBoxEntry<T>>(entries);
        panel.add(comboBox);
        return comboBox;
    }


    @Override
    public boolean isActive() {
        return activationCheckBox.isSelected();
    }


    @Override
    public void clear() {
        activationCheckBox.setSelected(false);
    }

    @Override
    public abstract Preprocessor getPreprocessor();

    @Override
    public JComponent getComponent() {
        return panel;
    }


    public static class JComboBoxEntry<T> {
        private String name;
        private T object;

        public JComboBoxEntry(String name, T object) {
            this.name = name;
            this.object = object;
        }

        public String getName() {
            return name;
        }

        public T getObject() {
            return object;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }


}
