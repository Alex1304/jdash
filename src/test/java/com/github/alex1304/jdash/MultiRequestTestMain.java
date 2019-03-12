package com.github.alex1304.jdash;

import java.util.Arrays;

import com.github.alex1304.jdash.client.AnonymousGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MultiRequestTestMain {
	
	static volatile int i = 0;

	public static void main(String[] args) {
		AnonymousGDClient client = GDClientBuilder.create()
				.withMaxConnections(70)
				.buildAnonymous();
		
		Flux.fromIterable(Arrays.asList(args))
			.map(Long::parseLong)
			.flatMap(id -> client.getUserByAccountId(id))
			.doOnNext(__ -> System.out.println(++i))
			.doOnComplete(() -> System.out.println("Done!"))
			.subscribe();
		Mono.never().block();
	}

}
