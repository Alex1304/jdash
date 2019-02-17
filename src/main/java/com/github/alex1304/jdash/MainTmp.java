package com.github.alex1304.jdash;

import com.github.alex1304.jdash.client.GeometryDashClient;
import com.github.alex1304.jdash.request.GDUserRequest;

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
		GeometryDashClient c = new GeometryDashClient();
		System.out.println(c.fetch(new GDUserRequest(98006)).block());
	}
}
