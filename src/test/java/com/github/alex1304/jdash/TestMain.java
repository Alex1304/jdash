package com.github.alex1304.jdash;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.client.GeometryDashClient;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDMessage;
import com.github.alex1304.jdash.entity.GDTimelyLevel;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.LevelSearchFilters;

import reactor.core.publisher.Mono;

public class TestMain {
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Please give account password in arguments");
			return;
		}
		GeometryDashClient client = GDClientBuilder.create().withAuthenticationDetails(7753855, args[0]).build();
		
		client.getUserByAccountId(98006)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get user 98006", o))
			.subscribe();
		
		client.searchUser("RobTop")
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Search user RobTop", o))
			.subscribe();
		
		client.getLevelById(52637920)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get level 52637920", o))
			.subscribe();
		
		client.searchLevels("bloodbath", LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Search levels Bloodbath", o))
			.subscribe();
		
		client.searchLevels("sonic wave", LevelSearchFilters.create().withDifficulties(EnumSet.of(Difficulty.HARD)), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Search levels Sonic wave, filter: Difficulty.HARD", o))
			.subscribe();
		
		client.getLevelById(52637920)
			.doOnError(TestMain::printError)
			.flatMap(GDLevel::download)
			.doOnSuccess(o -> printResult("Download level 52637920", o))
			.subscribe();
		
		client.getDailyLevel()
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get Daily level info", o))
			.subscribe();
		
		client.getWeeklyDemon()
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get Weekly demon info", o))
			.subscribe();
		
		client.getDailyLevel()
			.flatMap(GDTimelyLevel::getLevel)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get Daily level download", o))
			.subscribe();
		
		client.getWeeklyDemon()
			.flatMap(GDTimelyLevel::getLevel)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get Weekly demon download", o))
			.subscribe();
		
		client.browseAwardedLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Browse Awarded section", o))
			.subscribe();
		
		client.browseRecentLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Browse Recent section", o))
			.subscribe();
		
		client.browseMagicLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Browse Magic section", o))
			.subscribe();
		
		client.browseTrendingLevels(LevelSearchFilters.create(), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Browse Trending section", o))
			.subscribe();
		
		
		client.browseFeaturedLevels(0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Browse Featured section", o))
			.subscribe();
		
		client.browseHallOfFameLevels(0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Browse Hall of Fame", o))
			.subscribe();
		
		client.getLevelsByUser(client.getUserByAccountId(98006).block(), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Get levels from Alex1304", o))
			.subscribe();
		
		client.browseAwardedLevels(LevelSearchFilters.create(), 0)
			.flatMap(GDPaginator::goToNextPage)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Browse second page of Awarded section", o))
			.subscribe();
		
		client.browseFollowedLevels(LevelSearchFilters.create(), new ArrayList<>(Arrays.asList(client.searchUser("Alex1304").block(), client.searchUser("RobTop").block())), 0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Following Alex1304 and RobTop", o))
			.subscribe();
		
		client.getPrivateMessages(0)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("Private messages", o))
			.subscribe();
		
		client.getPrivateMessages(0)
			.map(paginator -> paginator.asList().get(0))
			.flatMap(GDMessage::getContent)
			.doOnError(TestMain::printError)
			.doOnSuccess(o -> printResult("First private message content", o))
			.subscribe();
		
		client.sendPrivateMessage(client.searchUser("Alex1304").block(), "Test", "Hello world!")
			.doAfterTerminate(() -> printResult("Send message", "Message sent!"))
			.subscribe();
		
		Mono.delay(Duration.ofSeconds(8)).block();
		System.out.println("Requests made: " + client.getTotalNumberOfRequestsMade());
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
