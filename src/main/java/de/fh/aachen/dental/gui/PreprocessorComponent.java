package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.Preprocessor;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public interface PreprocessorComponent {
    boolean isActive();

    Preprocessor getPreprocessor();

    JComponent getComponent();

    void clear();
}
