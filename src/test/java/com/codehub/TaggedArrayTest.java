package com.codehub;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static com.codehub.TaggedArray.parEach;
import static org.junit.Assert.*;

public class TaggedArrayTest {

	@Test
	public void testPall(){
		String[] data = {"1","2","3","4","5"};
		Object[] tags = new Object[]{1,2,3,4,5};

		TaggedArray<String> ta = new TaggedArray(data,tags);

		parEach(ta, validation(List.of("5","4","3","2","1").iterator()));
	}


	private Consumer<String> validation(Iterator y){

		return l -> assertEquals(l, y.next());
	}
}