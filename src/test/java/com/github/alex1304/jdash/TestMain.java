package com.github.alex1304.jdash;

import java.util.Scanner;

import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.client.GeometryDashClient;

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
		
		System.out.println("Press Enter to end the program...");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println("Bye");
		sc.close();
	}
}
