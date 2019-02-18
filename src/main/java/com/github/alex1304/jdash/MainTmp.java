package com.github.alex1304.jdash;

import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.client.GeometryDashClient;

public class MainTmp {
	
	public static void main(String[] args) {
//		HttpClient client = HttpClient.create().baseUrl("www.boomlings.com/database").headers(h -> h.add("Content-Type", "application/x-www-form-urlencoded"));
//		String res = client.post()
//				.uri("/getGJUserInfo20.php")
//				.send(ByteBufFlux.fromString(Flux.just("gameVersion=21&binaryVersion=34&gdw=0&secret=Wmfd2893gb7&targetAccountID=98006")))
//				.responseContent()
//				.aggregate()
//				.asString()
//				.block();
//		System.out.println(res);
		GeometryDashClient c = GDClientBuilder.create().build();
		System.out.println(System.currentTimeMillis());
		System.out.println(c.getUserByAccountId(98006).block());
		System.out.println(System.currentTimeMillis());
		System.out.println(c.getUserByAccountId(98006).block());
		System.out.println(System.currentTimeMillis());
		System.out.println(c.getUserByAccountId(98006).block());
		System.out.println(System.currentTimeMillis());
		System.out.println(c.getUserByAccountId(98006).block());
		System.out.println(System.currentTimeMillis());
		System.out.println(c.getUserByAccountId(98006).block());
		System.out.println(System.currentTimeMillis());
		System.out.println(c.getUserByAccountId(98006).block());
		System.out.println(System.currentTimeMillis());
	}
}
