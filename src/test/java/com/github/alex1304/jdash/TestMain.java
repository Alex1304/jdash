package com.github.alex1304.jdash;

import java.util.EnumSet;
import java.util.Scanner;

import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.client.GeometryDashClient;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.util.LevelSearchFilters;

public class TestMain {
	
	public static void main(String[] args) {
		GeometryDashClient client = GDClientBuilder.create().build();
		
		client.getUserByAccountId(98006)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(System.out::println)
			.subscribe();
		
		client.searchUsers("RobTop", 0)
			.doOnError(Throwable::printStackTrace)
			.collectList()
			.doOnSuccess(System.out::println)
			.subscribe();
		
		client.getLevelByID(52637920)
			.doOnError(Throwable::printStackTrace)
			.doOnSuccess(System.out::println)
			.subscribe();
		
		client.searchLevels("bloodbath", LevelSearchFilters.create(), 0)
			.doOnError(Throwable::printStackTrace)
			.collectList()
			.doOnSuccess(System.out::println)
			.subscribe();
		
		client.searchLevels("sonic wave", LevelSearchFilters.create().withDifficulties(EnumSet.of(Difficulty.HARD)), 0)
			.doOnError(Throwable::printStackTrace)
			.collectList()
			.doOnSuccess(System.out::println)
			.subscribe();
		
		System.out.println("Press Enter to end the program...");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println("Bye");
		sc.close();
	}
}
