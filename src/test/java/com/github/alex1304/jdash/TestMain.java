package com.github.alex1304.jdash;

import java.time.Duration;
import java.util.EnumSet;

import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.client.GeometryDashClient;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.util.LevelSearchFilters;

import reactor.core.publisher.Mono;

public class TestMain {
	
	public static void main(String[] args) {
		GeometryDashClient client = GDClientBuilder.create().build();
		
		client.getUserByAccountId(98006)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get user 98006", o))
			.subscribe();
		
		client.searchUsers("RobTop", 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Search user RobTop", o))
			.subscribe();
		
		client.getLevelById(52637920)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get level 52637920", o))
			.subscribe();
		
		client.searchLevels("bloodbath", LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Search levels Bloodbath", o))
			.subscribe();
		
		client.searchLevels("sonic wave", LevelSearchFilters.create().withDifficulties(EnumSet.of(Difficulty.HARD)), 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Search levels Sonic wave, filter: Difficulty.HARD", o))
			.subscribe();
		
		client.getLevelById(52637920)
			.doOnError(TestMain::printError)
			.flatMap(GDLevel::download)
			.doOnSuccess(o -> printResult("Download level 52637920", o))
			.subscribe();
		
		client.getDailyLevel()
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get Daily level", o))
			.subscribe();
		
		client.getWeeklyDemon()
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get Weekly demon", o))
			.subscribe();
		
		client.getDailyLevel()
			.doOnError(TestMain::printError)
			.flatMap(GDLevel::getSong)
			.doOnSuccess(o -> printResult("Get Daily level's song info", o))
			.subscribe();
		
		client.getWeeklyDemon()
			.doOnError(TestMain::printError)
			.flatMap(GDLevel::getSong)
			.doOnSuccess(o -> printResult("Get Weekly demon's song info", o))
			.subscribe();
		
		client.browseAwardedLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Browse Awarded section", o))
			.subscribe();
		
		client.browseRecentLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Browse Recent section", o))
			.subscribe();
		
		client.browseMagicLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Browse Magic section", o))
			.subscribe();
		
		client.browseTrendingLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Browse Trending section", o))
			.subscribe();
		
		
		client.browseFeaturedLevels(0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Browse Featured section", o))
			.subscribe();
		
		client.browseHallOfFameLevels(0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Browse Hall of Fame", o))
			.subscribe();
		
		client.getLevelsByUser(client.getUserByAccountId(98006).block(), 0)
			.doOnError(TestMain::printError)
			.collectList()
			.doOnSuccess(o -> printResult("Get levels from Alex1304", o))
			.subscribe();
		
		Mono.delay(Duration.ofSeconds(8)).block();
		System.out.println("End program");
	}
	
	private static void printResult(String title, Object obj) {
		System.out.println("------- " + title + " -------\n\t" + obj + "\n");
	}
	
	private static void printError(Throwable t) {
		System.err.println("Error Response: " + t.toString());
		t.printStackTrace();
	}
}
