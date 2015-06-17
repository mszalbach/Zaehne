package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.processor.Preprocessor;
import de.fh.aachen.dental.imagej.processor.ReflectionFilter;

public class ReflectionFilterComponent extends AbstractConverterComponent {

	public ReflectionFilterComponent() {
		super("Filter Reflections");
	}

	@Override
	public Preprocessor getPreprocessor() {
		return new ReflectionFilter();
	}

}
