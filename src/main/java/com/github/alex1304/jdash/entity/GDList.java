package com.github.alex1304.jdash.entity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a list of GD entities. This list is itself implementing {@link GDEntity}, and its ID corresponds to the ID of the first element, or 0 if the list is empty.
 *
 * @param <E> the type of GD entity contained in the list
 */
public class GDList<E extends GDEntity> extends ArrayList<E> implements GDEntity {

	private static final long serialVersionUID = -5205560290729960527L;

	public GDList() {
		super();
	}

	public GDList(Collection<? extends E> c) {
		super(c);
	}

	public GDList(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public long getId() {
		if (isEmpty()) {
			return 0;
		}
		return get(0).getId();
	}
}
