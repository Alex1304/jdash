package com.github.alex1304.jdash;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.alex1304.jdash.client.AnonymousGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.exception.SpriteLoadException;
import com.github.alex1304.jdash.graphics.SpriteFactory;
import com.github.alex1304.jdash.util.GDUserIconSet;

public class GraphicsTestMain {

	public static void main(String[] args) throws SpriteLoadException {
		// Build the client
		AnonymousGDClient client = GDClientBuilder.create().buildAnonymous();
		// Fetch RobTop's profile
		GDUser user = client.searchUser("neverRare").block();
		// Instanciate the SpriteFactory
		SpriteFactory sf = SpriteFactory.create();
		// Read the user's icon set
		GDUserIconSet iconSet = new GDUserIconSet(user, sf);
		// For each existing icon type (cube, ship, UFO, etc), generate the image
		BufferedImage mainIcon = iconSet.generateIcon(user.getMainIconType());
		BufferedImage cube = iconSet.generateIcon(IconType.CUBE);
		BufferedImage ship = iconSet.generateIcon(IconType.SHIP);
		BufferedImage ball = iconSet.generateIcon(IconType.BALL);
		BufferedImage ufo = iconSet.generateIcon(IconType.UFO);
		BufferedImage wave = iconSet.generateIcon(IconType.WAVE);
		BufferedImage robot = iconSet.generateIcon(IconType.ROBOT);
		BufferedImage spider = iconSet.generateIcon(IconType.SPIDER);
		// Save the images
		savePNG(mainIcon, "RobTop-Main");
		savePNG(cube, "RobTop-Cube");
		savePNG(ship, "RobTop-Ship");
		savePNG(ball, "RobTop-Ball");
		savePNG(ufo, "RobTop-Ufo");
		savePNG(wave, "RobTop-Wave");
		savePNG(robot, "RobTop-Robot");
		savePNG(spider, "RobTop-Spider");
	}
	
	/**
	 * Utility method to save an image into a PNG format in the system's temporary directory.
	 */
	private static void savePNG(BufferedImage img, String name) {
		try {
			String path = System.getProperty("java.io.tmpdir") + File.separator + name + ".png";
			ImageIO.write(img, "png", new File(path));
			System.out.println("Saved " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
