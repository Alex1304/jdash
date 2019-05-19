package com.github.alex1304.jdash.entity;

abstract class AbstractGDEntity implements GDEntity {

	final long id;

	public AbstractGDEntity(long id) {
		this.id = id;
	}

	@Override
	public final long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDEntity && ((GDEntity) obj).getId() == id;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}
}
