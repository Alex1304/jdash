package com.github.alex1304.jdash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.github.alex1304.jdash.client.AnonymousGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.exception.MissingAccessException;
import com.github.alex1304.jdash.util.LevelSearchFilters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SaveAllDemonsIDsMain {

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			throw new IllegalArgumentException("Needs 2 args");
		}
		AnonymousGDClient client = GDClientBuilder.create().buildAnonymous();
		List<String> demons = Flux.range(0, Integer.parseInt(args[0]))
				.flatMap(page -> client.browseAwardedLevels(LevelSearchFilters.create() , page++)
						.onErrorResume(MissingAccessException.class, e -> Mono.empty()), 1000, 1000)
				.flatMap(Flux::fromIterable)
				.filter(level -> level.isDemon())
				.map(level -> "" + level.getId())
				.collectList()
				.block();
		Files.write(Paths.get(args[1]), demons);
	}
}
