package com.github.alex1304.jdash;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.exception.SpriteLoadException;
import com.github.alex1304.jdash.graphics.SpriteFactory;

public class GraphicsTestMain {

	public static void main(String[] args) throws SpriteLoadException {
		SpriteFactory sf = SpriteFactory.create();
		Random r = new Random(System.currentTimeMillis());
		
		saveTmp(sf, r, IconType.CUBE, 1);
		saveTmp(sf, r, IconType.SHIP, 1);
		saveTmp(sf, r, IconType.BALL, 1);
		saveTmp(sf, r, IconType.UFO, 1);
		saveTmp(sf, r, IconType.WAVE, 1);
		saveTmp(sf, r, IconType.ROBOT, 1);
		saveTmp(sf, r, IconType.SPIDER, 1);
	}
	
	private static void saveTmp(SpriteFactory sf, Random r, IconType type, int id) {
		BufferedImage img = sf.makeSprite(type, id, r.nextInt(SpriteFactory.COLORS.size()), r.nextInt(SpriteFactory.COLORS.size()), true);
		SpriteFactory.write(img, type.name() + id);
	}
}
