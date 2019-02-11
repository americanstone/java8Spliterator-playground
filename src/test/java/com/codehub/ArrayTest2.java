package com.codehub;

import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.Consumer;


import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
 * Test program for the Java 8 features in the Array class.
 */
public class ArrayTest2 {
	/**
	 * Maximum test size.
	 */
	private final int mMaxTestSize = 25;

	/**
	 * A list of mMaxTestSize random integers.
	 */
	private final List<Long> mRandomInput =
			new Random().longs(mMaxTestSize, 0, Long.MAX_VALUE)
					.boxed()
					.collect(toList());

	/**
	 * Test the Array implementation of spliterator().
	 */
	@Test
	public void testArraySpliterator() {
		runSpliteratorTest19(new Array<>());
		runSpliteratorTest20(new Array<>());
	}

	/**
	 * Factors out common code to test the spliterator() methods.
	 */
	private void runSpliteratorTest19(Array<Long> array) {
		Long factorialOf19 = 121645100408832000L;

		for (long i = 1; i <= 19; i++)
			array.add(i);

		Long f1 = array
				// Convert to a sequential stream.
				.stream()

				// Perform a reduction.
				.reduce(1L,
						(x, y) -> (x * y));

		assertEquals(factorialOf19, f1);

		Long f2 = array
				// Convert to a parallel stream.
				.parallelStream()

				// Perform a reduction.
				.reduce(1L,
						(x, y) -> (x * y));

		assertEquals(factorialOf19, f2);
	}

	/**
	 * Factors out common code to test the spliterator() methods.
	 */
	private void runSpliteratorTest20(Array<Long> array) {
		Long factorialOf20 = 2432902008176640000L;

		for (long i = 1; i <= 20; i++)
			array.add(i);

		Long f1 = array
				// Convert to a parallel stream.
				.stream()

				// Perform a reduction.
				.reduce(1L,
						(x, y) -> (x * y));

		assertEquals(factorialOf20, f1);

		Long f2 = array
				// Convert to a parallel stream.
				.parallelStream()

				// Perform a reduction.
				.reduce(1L,
						(x, y) -> (x * y));

		assertEquals(factorialOf20, f2);
	}

	/**
	 * Test the Array Collector implementation.
	 */
	@Test
	public void testArrayCollector() {
		Array<Long> result = mRandomInput
				.stream()
				.sorted()
				.collect(ArrayCollector.toArray());

		Collections.sort(mRandomInput);

		for (int i = 0; i < result.size(); i++)
			assertEquals(mRandomInput.get(i), result.get(i));
	}

	/**
	 * Test the Array implementation of forEach().
	 */
	@Test
	public void testArrayForEach() {
		Array<Long> a1 = new Array<>(mRandomInput);
		Array<Long> a2 = new Array<>();
		int originalSize = a1.size();

		a1.forEach(a2::add);

		assertEquals(originalSize, a2.size());
		assertEquals(a1.size(), a2.size());
	}

	/**
	 * Test the Array implementation of replaceAll().
	 */
	@Test
	public void testArrayReplaceAll() {
		Array<Long> array = new Array<>();

		// Add all the random input to the queue.
		array.addAll(mRandomInput);

		// Add one to each element.
		array.replaceAll(i -> i + 1);

		for (int i = 0; i < mRandomInput.size(); i++)
			assertEquals(mRandomInput.get(i),
					Long.valueOf(array.get(i) - 1));
	}

	@Test
	public void testArraySpliteratorTryAdvance(){
		Array<Long> array = new Array<>();
		array.addAll(List.of(1L,2L,3L,4L,5L,6L,7L));

		Spliterator<Long> sp = array.spliterator();

		sp.tryAdvance(validation(1L));


		sp.forEachRemaining(validation(List.of(2L,3L,4L,5L,6L,7L).iterator()));
	}

	private Consumer<Long> validation(Long y){

		return l -> assertEquals(l, y);
	}

	private Consumer<Long> validation(Iterator y){

		return l -> assertEquals(l, y.next());
	}
}

