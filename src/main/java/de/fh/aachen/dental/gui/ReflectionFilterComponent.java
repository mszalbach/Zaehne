package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.filter.ReflectionFilter;

public class ReflectionFilterComponent extends ConverterComponent  {

	public ReflectionFilterComponent() {
		super("Filter Reflections");
	}

	@Override
	public Converter getConverter() {
		return new ReflectionFilter();
	}

}
