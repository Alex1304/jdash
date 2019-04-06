package com.github.alex1304.jdash;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

import com.github.alex1304.jdash.client.AuthenticatedGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDMessage;
import com.github.alex1304.jdash.entity.GDTimelyLevel;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.LevelSearchFilters;

import reactor.core.publisher.Mono;

public class TestMain {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Please provide GD account login details in arguments");
			return;
		}
		AuthenticatedGDClient client = GDClientBuilder.create().buildAuthenticated(args[0], args[1]);
		
		client.getUserByAccountId(98006)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Get user 98006", o))
			.subscribe();
		
		client.searchUser("RobTop")
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Search user RobTop", o))
			.subscribe();
		
		client.getLevelById(10565740)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Get level 10565740", o))
			.subscribe();
		
		client.searchLevels("bloodbath", LevelSearchFilters.create(), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Search levels Bloodbath", o))
			.subscribe();
		
		client.searchLevels("sonic wave", LevelSearchFilters.create().withDifficulties(EnumSet.of(Difficulty.HARD)), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Search levels Sonic wave, filter: Difficulty.HARD", o))
			.subscribe();
		
		client.getLevelById(52637920)
			.doOnError(Throwable::printStackTrace)
			.flatMap(GDLevel::download)
			.doOnSuccess(o -> printResult("Download level 52637920", o))
			.subscribe();
		
		client.getDailyLevel()
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Get Daily level info", o))
			.subscribe();
		
		client.getWeeklyDemon()
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Get Weekly demon info", o))
			.subscribe();
		
		client.getDailyLevel()
			.flatMap(GDTimelyLevel::getLevel)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Get Daily level download", o))
			.subscribe();
		
		client.getWeeklyDemon()
			.flatMap(GDTimelyLevel::getLevel)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Get Weekly demon download", o))
			.subscribe();
		
		client.browseAwardedLevels(LevelSearchFilters.create(), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Browse Awarded section", o))
			.subscribe();
		
		client.browseRecentLevels(LevelSearchFilters.create(), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Browse Recent section", o))
			.subscribe();
		
		client.browseMagicLevels(LevelSearchFilters.create(), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Browse Magic section", o))
			.subscribe();
		
		client.browseTrendingLevels(LevelSearchFilters.create(), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Browse Trending section", o))
			.subscribe();
		
		
		client.browseFeaturedLevels(0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Browse Featured section", o))
			.subscribe();
		
		client.browseHallOfFameLevels(0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Browse Hall of Fame", o))
			.subscribe();
		
		client.getLevelsByUser(client.getUserByAccountId(98006).block(), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Get levels from Alex1304", o))
			.subscribe();
		
		client.browseAwardedLevels(LevelSearchFilters.create(), 0)
			.flatMap(GDPaginator::goToNextPage)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Browse second page of Awarded section", o))
			.subscribe();
		
		client.browseFollowedIds(LevelSearchFilters.create(), new ArrayList<>(Arrays.asList(98006L, 71L)), 0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Following Alex1304 and RobTop", o))
			.subscribe();
		
		client.getPrivateMessages(0)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("Private messages", o))
			.subscribe();
		
		client.getPrivateMessages(0)
			.map(paginator -> paginator.asList().get(0))
			.flatMap(GDMessage::getBody)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(o -> printResult("First private message content", o))
			.subscribe();
		
		client.sendPrivateMessage(client.searchUser("Alex1304").block(), "Test", "Hello world!")
			.doAfterSuccessOrError((success, error) -> {
				if (error == null) printResult("Send message", "Message sent!");
				else error.printStackTrace();
			})
			.subscribe();
		
		Mono.delay(Duration.ofSeconds(8)).block();
		System.out.println("End program");
	}
	
	private static void printResult(String title, Object obj) {
		System.out.println("------- " + title + " -------\n\t" + obj + "\n");
	}
}
