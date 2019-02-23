package com.github.alex1304.jdash.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import com.github.alex1304.jdash.entity.GDEntity;

import reactor.core.publisher.Mono;

public class LazyProperty<E extends GDEntity> {
	
	private final Supplier<Mono<E>> getter;
	private Optional<Mono<E>> value;
	
	public LazyProperty(Supplier<Mono<E>> getter) {
		this.getter = Objects.requireNonNull(getter);
		this.value = Optional.empty();
	}
	
	public Mono<E> getValue() {
		if (!value.isPresent()) {
			value = Optional.of(getter.get());
		}
		return value.get();
	}
}
