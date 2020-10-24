package com.github.alex1304.jdash;

import com.github.alex1304.jdash.old.client.AnonymousGDClient;
import com.github.alex1304.jdash.old.client.GDClientBuilder;
import com.github.alex1304.jdash.old.entity.GDLevel;
import com.github.alex1304.jdash.old.entity.GDLevelData;
import com.github.alex1304.jdash.old.util.Gzip;

public class ZipTestMain {

	public static void main(String[] args) {
		AnonymousGDClient client = GDClientBuilder.create().buildAnonymous();
		GDLevelData bloodbath = client.getLevelById(10565740).flatMap(GDLevel::download).block();
		String data = Gzip.unzip(bloodbath.getData());
		System.out.println(data);
	}
}
