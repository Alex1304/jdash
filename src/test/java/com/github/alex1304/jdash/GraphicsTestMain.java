package com.github.alex1304.jdash;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.exception.SpriteLoadException;
import com.github.alex1304.jdash.graphics.SpriteFactory;

public class GraphicsTestMain {

	public static void main(String[] args) throws SpriteLoadException, IOException {
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
	
	private static void saveTmp(SpriteFactory sf, Random r, IconType type, int id) throws IOException {
		BufferedImage img = sf.makeSprite(type, id, r.nextInt(SpriteFactory.COLORS.size()), r.nextInt(SpriteFactory.COLORS.size()), false);
		String path = System.getProperty("java.io.tmpdir") + File.separator + type.name() + id + ".png";
		ImageIO.write(img, "png", new File(path));
		System.out.println("Written " + path);
	}
}
