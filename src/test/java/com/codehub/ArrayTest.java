package com.codehub;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test program for the Java 7 features of the Array class.
 */
public class ArrayTest {
	/**
	 * An exception.
	 */
	@Rule
	public ExpectedException mException = ExpectedException.none();

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
	 * Test Array constructor that takes an initial capacity and
	 * add/remove elements to/from it.
	 */
	@Test
	public void testConstructorAddRemove() {
		Array<Integer> tmp = new Array<>(10);
		assertEquals(0, tmp.size());

		for (int i = 0; i < (10 + 1); i++)
			tmp.add(i);

		assertEquals(11, tmp.size());

		for (int i = 0; i < tmp.size(); i++)
			assertEquals(i, (int) tmp.get(i));

		for (int i = 0; i < tmp.size(); i++)
			assertEquals(i, (int) tmp.remove(0));

		mException.expect(IllegalArgumentException.class);
		tmp = new Array<>(-1);
	}

	/**
	 * Test for the IndexOutOfBounds exception for get().
	 */
	@Test
	public void test_GetExceptionPositive() {
		Array<Character> tmp = new Array<>(1);
		mException.expect(IndexOutOfBoundsException.class);
		tmp.get(1);
	}

	/**
	 * Another test for the IndexOutOfBounds exception for get().
	 */
	@Test
	public void test_GetExceptionNegative() {
		Array<Character> tmp = new Array<>(1);
		mException.expect(IndexOutOfBoundsException.class);
		tmp.get(-1);
	}

	/**
	 * Another test for the IndexOutOfBounds exception for set().
	 */
	@Test
	public void test_SetExceptionNegative() {
		Array<Character> tmp = new Array<Character>(1);
		tmp.add('a');
		mException.expect(IndexOutOfBoundsException.class);
		tmp.set(-1, 'a');
	}

	/**
	 * Test the Array constructor that takes a Java collection.
	 */
	@Test
	public void testConstructorCollection() {
		Array<Long> array = new Array<>(mRandomInput);
		assertEquals(mRandomInput.size(), array.size());

		assertFalse(array.isEmpty());

		for (int i = 0; i < array.size(); i++)
			assertEquals(mRandomInput.get(i),
					array.get(array.indexOf(mRandomInput.get(i))));
	}

	/**
	 * Test the Array implementation of indexOf().
	 */
	@Test
	public void testArrayIndexOf() {
		Array<Long> array = new Array<>(mRandomInput);

		for (int i = 0; i < array.size(); i++)
			assertEquals(mRandomInput.get(i),
					array.get(array.indexOf(mRandomInput.get(i))));

		assertEquals(array.indexOf(null), -1);
	}

	/**
	 * Test the Array implementation of get() and set().
	 */
	@Test
	public void testArrayGetAndSet() {
		Array<Long> array = new Array<>(mRandomInput);

		for (int i = 0; i < mRandomInput.size(); i++)
			array.set(i,
					mRandomInput.get(i) * mRandomInput.get(i));

		for (int i = 0; i < array.size(); i++)
			assertEquals(Long.valueOf(mRandomInput.get(i) * mRandomInput.get(i)),
					array.get(i));
	}

	/**
	 * Test the Array implementation of addAll();
	 */
	@Test
	public void testArrayAddAll() {
		Array<Long> array1 = new Array<>();

		// Add the random input to the end of the queue.
		array1.addAll(mRandomInput);

		assertEquals(mRandomInput.size(), array1.size());

		for (int i = 0; i < array1.size(); i++)
			assertEquals(mRandomInput.get(i),
					array1.get(i));

		Array<Long> array2 = new Array<>();

		array2.addAll(array1);

		assertEquals(array1.size(), array2.size());

		for (int i = 0; i < array1.size(); i++)
			assertEquals(array2.get(i),
					array1.get(i));
	}

	/**
	 * Test the Array implementation of remove().
	 */
	@Test
	public void testRemove() {
		Array<Character> a = new Array<>(3);
		a.add('a');
		a.add('b');
		a.add('c');

		a.remove(1);

		assertEquals(2, a.size());
		assertEquals('a', (char) a.get(0));
		assertEquals('c', (char) a.get(1));

		a.remove(1);
		assertEquals(1, a.size());

		a.remove(0);
		assertEquals(0, a.size());

		mException.expect(IndexOutOfBoundsException.class);
		a.remove(-1);
	}

	/**
	 * Test the Array implementation of iterator().
	 */
	@Test
	public void testIterator() {
		Array<Long> array = new Array<>(mRandomInput);

		Iterator<Long> a1 = array.iterator();
		Iterator<Long> a2 = mRandomInput.iterator();

		while (a1.hasNext() && a2.hasNext())
			assertEquals(a1.next(), a2.next());

		assert !a1.hasNext();
		assert !a2.hasNext();

		Array<Character> a = new Array<>();

		Iterator<Character> it = a.iterator ();

		assertTrue(it.hasNext () == false);

		a.add('a');

		it = a.iterator ();
		assertTrue (it.hasNext ());
		assertTrue (it.next () == 'a');
		assertTrue (it.hasNext () == false);

		a.add('b');

		it = a.iterator ();
		assertTrue (it.hasNext ());
		assertTrue (it.next () == 'a');
		assertTrue (it.hasNext ());
		assertTrue (it.next () == 'b');
		assertTrue (it.hasNext () == false);

		a.set (0, 'b');

		it = a.iterator ();
		assertTrue (it.hasNext ());
		assertTrue (it.next () == 'b');
		assertTrue (it.hasNext ());
		assertTrue (it.next () == 'b');
		assertTrue (!it.hasNext());

		mException.expect(IndexOutOfBoundsException.class);
		it.next ();
	}

	/**
	 * Test the Array implementation of iterator().
	 */
	@Test
	public void testIteratorRemove () {
		Array<Character> a = new Array<>();
		a.add('a');
		a.add('b');

		Iterator<Character> it = a.iterator();

		char c = it.next();
		assertEquals('a', c);

		it.remove();

		assertEquals(1, a.size ());
		assertEquals('b', (char) a.get(0));

		c = it.next ();

		assertEquals('b', c);

		it.remove();

		assertEquals(0, a.size());

		mException.expect(IllegalStateException.class);
		it.remove ();
	}
}
