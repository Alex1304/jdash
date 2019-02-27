package com.github.alex1304.jdash;

import com.github.alex1304.jdash.client.AnonymousGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDLevelData;
import com.github.alex1304.jdash.util.Gzip;

public class ZipTestMain {

	public static void main(String[] args) {
		AnonymousGDClient client = GDClientBuilder.create().buildAnonymous();
		GDLevelData bloodbath = client.getLevelById(10565740).flatMap(GDLevel::download).block();
		String data = Gzip.unzip(bloodbath.getData());
		System.out.println(data);
	}
}
