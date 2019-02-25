package com.github.alex1304.jdash.client;

import java.util.Objects;

abstract class AbstractAuthenticatedGDRequest<E> extends AbstractGDRequest<E> {

	final AuthenticatedGDClient client;
	
	AbstractAuthenticatedGDRequest(AuthenticatedGDClient client) {
		super(client);
		this.client = Objects.requireNonNull(client);
	}
}
