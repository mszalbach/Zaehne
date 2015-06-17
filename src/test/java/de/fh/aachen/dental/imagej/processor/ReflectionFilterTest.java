package de.fh.aachen.dental.imagej.processor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ReflectionFilterTest {

	ReflectionFilter filter;

	@Before
	public void setUp() {
		filter = new ReflectionFilter();
	}

	@Test
	public void shouldBeAReflection() {
		int[] reflection = { 230, 180, 220 };

		assertTrue(filter.isReflection(reflection));
	}

	@Test
	public void shouldBeOutOfBounds() {
		int[] outOfBound = { 0, 0, 0 };

		assertTrue(filter.pixelIsOutOfBounds(outOfBound));
	}

}
