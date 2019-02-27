package com.github.alex1304.jdash.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.exception.SpriteLoadException;
import com.github.alex1304.jdash.util.Utils;

public class SpriteFactory {
	public static final Map<Integer, Color> COLORS = colors();
	
	private static BufferedImage spriteImg;
	private static Map<String, List<Sprite>> sprites;
	
	public BufferedImage makeSprite(IconType type, int id, int color1Id, int color2Id, boolean withGlowOutline) {
		List<Sprite> spList = sprites.get(type.name() + id);
		BufferedImage img = new BufferedImage(200, 200, spriteImg.getType());
		Graphics2D g = img.createGraphics();
		orderSprites(spList);
		for (Sprite sp : spList) {
			// Variables
			String name = sp.getName();
			int x = sp.getRectangle().x;
			int y = sp.getRectangle().y;
			int width = sp.getSizeX();
			int height = sp.getSizeY();
			int offX = sp.getOffsetX();
			int offY = sp.getOffsetY();
			int realWidth = sp.isRotated() ? sp.getRectangle().height : sp.getRectangle().width;
			int realHeight = sp.isRotated() ? sp.getRectangle().width : sp.getRectangle().height;
			Image subimg = spriteImg.getSubimage(x, y, realWidth, realHeight);
			int centerX = width / 2;
			int centerY = height / 2;
			// Fix orientation if rotated
			if (sp.isRotated()) {
				subimg = rotate(subimg, -90);
			}
			// Reduce brightness of robot/spider back legs
			if (name.contains("001D") && !name.contains("glow")) {
				subimg = reduceBrightness(subimg);
			}
			// Offset modifiers for robot legs
			if (name.contains("robot")) {
				if (name.contains(id + "_02_")) {
					subimg = rotate(subimg, 45);
					offX -= name.contains("001D") ? 50 : 40;
					offY -= 20;
				} else if (name.contains(id + "_03_")) {
					subimg = rotate(subimg, -45);
					offX -= name.contains("001D") ? 40 : 30;
					offY -= 60;
				} else if (name.contains(id + "_04_")) {
					offX -= name.contains("001D") ? 10 : 00;
					offY -= 70;
				}
			}
			// Offset modifiers for spider legs
			if (name.contains("spider")) {
				if (name.contains("_02_") && name.endsWith("1D")) {
					offX += 18;
					offY -= 38;
				} else if (name.contains("_02_") && name.endsWith("1DD")) {
					offX += 58;
					offY -= 38;
				} else if (name.contains("_02_")) {
					offX -= 16;
					offY -= 38;
				} else if (name.contains("_03_")) {
					offX -= 86;
					offY -= 38;
					if (id == 7) {
						offX += 20;
						offY += 16;
					}
					subimg = rotate(subimg, 45);
				} else if (name.contains("_04_")) {
					offX -= 30;
					offY -= 20;
				}
			}
			
			// Apply player colors
			if (name.contains("_2_00")) {
				subimg = applyColor(subimg, color2Id);
			} else if (!name.contains("extra") && !name.contains("_3_00")) {
				subimg = applyColor(subimg, color1Id);
			}
			// Draw the result
			int drawX = 100 - centerX + offX;
			int drawY = 100 - centerY - offY;
			int drawOffY;
			switch (type) {
				case ROBOT:
					drawOffY = -20;
					break;
				case SPIDER:
					drawOffY = -10;
					break;
				case UFO:
					drawOffY = 30;
					break;
				default:
					drawOffY = 0;
					break;
			}
			g.drawImage(subimg, drawX, drawOffY + drawY, subimg.getWidth(null), subimg.getHeight(null), null);
		}
		g.dispose();
		return img;
	}
	
	private static void orderSprites(List<Sprite> spList) {
		Collections.reverse(spList);
		spList.removeIf(sp -> sp.getName().matches("robot_[0-9]{2,3}_02_2_.*"));
		pullSpriteToFrontIf(spList, sp -> sp.getName().matches("(robot|spider)_[0-9]{2,3}_(02|03|04)_.*"));
		dupeSpriteIf(spList, sp -> sp.getName().matches("robot_[0-9]{2,3}_(02|03|04)_.*"), 1, false);
		dupeSpriteIf(spList, sp -> sp.getName().matches("spider_[0-9]{2,3}_02_.*") && !sp.getName().contains("extra"), 2, false);
		pullSpriteToFrontIf(spList, sp -> sp.getName().matches("robot_[0-9]{2,3}_02_.*") && !sp.getName().endsWith("D"));
		pullSpriteToFrontIf(spList, sp -> sp.getName().matches("robot_[0-9]{2,3}_04_.*") && !sp.getName().endsWith("D"));
		pushSpriteToBackIf(spList, sp -> sp.getName().matches("robot_[0-9]{2,3}_03_.*D"));
		
		pullSpriteToFrontIf(spList, sp -> sp.getName().matches("spider[0-9]{2,3}_04_.*"));
		pullSpriteToFrontIf(spList, sp -> sp.getName().contains("extra"));
	}
	
	private static void pullSpriteToFrontIf(List<Sprite> spList, Predicate<Sprite> cond) {
		int offset = 0;
		for (int i = 0 ; i < spList.size() ; i++) {
			Sprite sp = spList.get(i - offset);
			if (cond.test(sp)) {
				spList.remove(i - offset);
				spList.add(sp);
				offset++;
			}
		}
	}
	
	private static void pushSpriteToBackIf(List<Sprite> spList, Predicate<Sprite> cond) {
		for (int i = 0 ; i < spList.size() ; i++) {
			Sprite sp = spList.get(i);
			if (cond.test(sp)) {
				spList.remove(i);
				spList.add(0, sp);
			}
		}
	}
	
	private static void dupeSpriteIf(List<Sprite> spList, Predicate<Sprite> cond, int nbDup, boolean front) {
		final int initialSize = spList.size();
		int offset = 0;
		for (int i = 0 ; i < initialSize ; i++) {
			Sprite sp = spList.get(i + offset);
			if (cond.test(sp)) {
				Sprite dupe = sp.duplicate();
				for (int d = 0 ; d < nbDup ; d++) {
					if (front) {
						spList.add(dupe);
					} else {
						spList.add(0, dupe);
						offset++;
					}
					dupe = dupe.duplicate();
				}
			}
		}
	}

	private static Image rotate(Image img, double deg) {
		deg = deg % 360 + (Math.abs(deg) > 180 ? -Math.signum(deg) * 360 : 0);
		double rad = Math.toRadians(deg);
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		// surrounding rectangle
		Rectangle2D newImgRect = AffineTransform.getRotateInstance(rad, 0, 0)
				.createTransformedShape(new Rectangle(width, height)).getBounds2D();
		int newWidth = (int) newImgRect.getWidth();
		int newHeight = (int) newImgRect.getHeight();
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, spriteImg.getType());
		Graphics2D g2 = newImage.createGraphics();
		int tx, ty;
		if (deg < 0 && deg >= -90) {
			tx = 0;
			ty = (int) (width * Math.abs(Math.sin(rad)));
		} else if (deg < 90 && deg >= 0) {
			tx = (int) (height * Math.abs(Math.sin(rad)));
			ty = 0;
		} else if (deg < -90 && deg >= -180) {
			tx = (int) (width * Math.abs(Math.cos(rad)));
			ty = newHeight;
		} else {
			tx = newWidth;
			ty = (int) (height * Math.abs(Math.cos(rad)));
		}
		g2.translate(tx, ty);
		g2.rotate(rad, 0, 0);
		g2.drawImage(img, 0, 0, width, height, null);
//		write(toBufferedImage(img), "rotate_before_" + deg);
//		write(toBufferedImage(newImage), "rotate_after_" + deg);
		return newImage;
	}
	
//	private static Image merge(Image img1, Image img2) {
//		int w1 = img1.getWidth(null);
//		int h1 = img1.getHeight(null);
//		int w2 = img2.getWidth(null);
//		int h2 = img2.getHeight(null);
//		BufferedImage newImg = new BufferedImage(Math.max(w1, w2), Math.max(h1, h2), spriteImg.getType());
//		Graphics2D g = newImg.createGraphics();
//		g.drawImage(img1, 0, 0, w1, h1, null);
//		g.drawImage(img2, 0, 0, w2, h2, null);
//		g.dispose();
//		return newImg;
//	}
	
	private static Image applyColor(Image img, int colorId) {
		Color color = COLORS.get(colorId);
		return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), new RGBImageFilter() {
			@Override
			public int filterRGB(int x, int y, int rgb) {
				return rgb & color.getRGB();
			}
		}));
	}
	
	private static Image reduceBrightness(Image img) {
		return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), new RGBImageFilter() {
			@Override
			public int filterRGB(int x, int y, int rgb) {
				return rgb & 0xFF808080;
			}
		}));
	}
	
//	private static BufferedImage toBufferedImage(Image img) {
//		if (img instanceof BufferedImage) {
//			return (BufferedImage) img;
//		}
//		BufferedImage buffered = new BufferedImage(img.getWidth(null), img.getHeight(null), spriteImg.getType());
//		Graphics2D g = buffered.createGraphics();
//		g.drawImage(img, 0, 0, null);
//		g.dispose();
//		return buffered;
//	}
	
	@Override
	public String toString() {
		return sprites.toString();
	}
	
	public static void write(BufferedImage img, String name) {
		try {
			String path = System.getProperty("java.io.tmpdir") + File.separator + name + ".png";
			ImageIO.write(img, "png", new File(path));
			System.out.println("Written " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadSprites(XMLPropertyListConfiguration spritePlist) {
		sprites = new HashMap<>();
		final Map<String, IconType> prefixes = new LinkedHashMap<>();
		prefixes.put("frames.player_ball_", IconType.BALL);
		prefixes.put("frames.player_", IconType.CUBE);
		prefixes.put("frames.ship_", IconType.SHIP);
		prefixes.put("frames.bird_", IconType.UFO);
		prefixes.put("frames.dart_", IconType.WAVE);
		prefixes.put("frames.robot_", IconType.ROBOT);
		prefixes.put("frames.spider_", IconType.SPIDER);
		final Map<String, Map<String, double[]>> offsets = new HashMap<>();
		final Map<String, Map<String, double[]>> sizes = new HashMap<>();
		final Map<String, Map<String, double[]>> sourceSizes = new HashMap<>();
		final Map<String, Map<String, double[]>> rectangles = new HashMap<>();
		final Map<String, Map<String, Boolean>> rotatedStates = new HashMap<>();
		Iterator<String> it = spritePlist.getKeys();
		for (String key = (it.hasNext() ? it.next() : null) ; it.hasNext() ; key = it.next()) {
			for (Entry<String, IconType> prefix : prefixes.entrySet()) {
				if (key.startsWith(prefix.getKey())) {
					String partKey = prefix.getValue().name() + Utils.partialParseInt(key.substring(prefix.getKey().length()));
					final String f_key = key;
					if (key.endsWith("spriteOffset")) {
						offsets.compute(partKey, (k, v) -> {
							if (v == null) {
								v = new LinkedHashMap<>();
							}
							v.put(f_key, parseSimpleTuple(spritePlist.getString(f_key)));
							return v;
						});
					} else if (key.endsWith("spriteSize")) {
						sizes.compute(partKey, (k, v) -> {
							if (v == null) {
								v = new LinkedHashMap<>();
							}
							v.put(f_key, parseSimpleTuple(spritePlist.getString(f_key)));
							return v;
						});
					} else if (key.endsWith("spriteSourceSize")) {
						sourceSizes.compute(partKey, (k, v) -> {
							if (v == null) {
								v = new LinkedHashMap<>();
							}
							v.put(f_key, parseSimpleTuple(spritePlist.getString(f_key)));
							return v;
						});
					} else if (key.endsWith("textureRect")) {
						rectangles.compute(partKey, (k, v) -> {
							if (v == null) {
								v = new LinkedHashMap<>();
							}
							v.put(f_key, parseDoubleTuple(spritePlist.getString(f_key)));
							return v;
						});
					} else if (key.endsWith("textureRotated")) {
						rotatedStates.compute(partKey, (k, v) -> {
							if (v == null) {
								v = new LinkedHashMap<>();
							}
							v.put(f_key, spritePlist.getBoolean(f_key));
							return v;
						});
					}
					break;
				}
			}
		}
		for (String key : offsets.keySet()) {
			List<Sprite> spList = new ArrayList<>();
			Iterator<Entry<String, double[]>> offsetIt = offsets.get(key).entrySet().iterator();
			Iterator<double[]> sizeIt = sizes.get(key).values().iterator();
			Iterator<double[]> sourceSizeIt = sourceSizes.get(key).values().iterator();
			Iterator<double[]> rectangleIt = rectangles.get(key).values().iterator();
			Iterator<Boolean> rotatedIt = rotatedStates.get(key).values().iterator();
			while (offsetIt.hasNext()) {
				Entry<String, double[]> entry = offsetIt.next();
				String spriteName = entry.getKey().split("\\.")[1];
				double[] offset = entry.getValue();
				double[] size = sizeIt.next();
				double[] sourceSize = sourceSizeIt.next();
				double[] rectangle = rectangleIt.next();
				boolean isRotated = rotatedIt.next();
				spList.add(new Sprite(spriteName, (int) offset[0], (int) offset[1], (int) size[0], (int) size[1], (int) sourceSize[0], (int) sourceSize[1],
						new Rectangle((int) rectangle[0], (int) rectangle[1], (int) rectangle[2], (int) rectangle[3]), isRotated));
			}
			sprites.put(key, spList);
		}
	}
	
	private static double[] parseSimpleTuple(String tupleStr) {
		String[] split = tupleStr.substring(1, tupleStr.length() - 1).split(",");
		return new double[] { Double.parseDouble(split[0]), Double.parseDouble(split[1]) };
	}
	
	private static double[] parseDoubleTuple(String tupleStr) {
		String[] split = tupleStr.substring(2, tupleStr.length() - 2).split("\\}?,\\{?");
		return new double[] { Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]) };
	}
	
	private static Map<Integer, Color> colors() {
		Map<Integer, Color> colors = new HashMap<>();
		colors.put(0, new Color(125, 255, 0));
		colors.put(1, new Color(0, 255, 0));
		colors.put(2, new Color(0, 255, 125));
		colors.put(3, new Color(0, 255, 255));
		colors.put(4, new Color(0, 125, 255));
		colors.put(5, new Color(0, 0, 255));
		colors.put(6, new Color(125, 0, 255));
		colors.put(7, new Color(255, 0, 255));
		colors.put(8, new Color(255, 0, 125));
		colors.put(9, new Color(255, 0, 0));
		colors.put(10, new Color(255, 125, 0));
		colors.put(11, new Color(255, 255, 0));
		colors.put(12, new Color(255, 255, 255));
		colors.put(13, new Color(185, 0, 255));
		colors.put(14, new Color(255, 185, 0));
		colors.put(15, new Color(0, 0, 0));
		colors.put(16, new Color(0, 200, 255));
		colors.put(17, new Color(175, 175, 175));
		colors.put(18, new Color(90, 90, 90));
		colors.put(19, new Color(255, 125, 125));
		colors.put(20, new Color(0, 175, 75));
		colors.put(21, new Color(0, 125, 125));
		colors.put(22, new Color(0, 75, 175));
		colors.put(23, new Color(75, 0, 175));
		colors.put(24, new Color(125, 0, 125));
		colors.put(25, new Color(175, 0, 75));
		colors.put(26, new Color(175, 75, 0));
		colors.put(27, new Color(125, 125, 0));
		colors.put(28, new Color(75, 175, 0));
		colors.put(29, new Color(255, 75, 0));
		colors.put(30, new Color(150, 50, 0));
		colors.put(31, new Color(150, 100, 0));
		colors.put(32, new Color(100, 150, 0));
		colors.put(33, new Color(0, 150, 100));
		colors.put(34, new Color(0, 100, 150));
		colors.put(35, new Color(100, 0, 150));
		colors.put(36, new Color(150, 0, 100));
		colors.put(37, new Color(150, 0, 0));
		colors.put(38, new Color(0, 150, 0));
		colors.put(39, new Color(0, 0, 150));
		colors.put(40, new Color(125, 255, 175));
		colors.put(41, new Color(125, 125, 255));
		return Collections.unmodifiableMap(colors);
	}

	/**
	 * Creates a new sprite factory. This method will load the sprite images in
	 * memory, that's why an {@link IOException} needs to be checked when calling
	 * this method.
	 * 
	 * @return the sprite factory
	 * @throws SpriteLoadException if something goes wrong when loading the sprites.
	 */
	public static SpriteFactory create() throws SpriteLoadException {
		try {
			if (sprites == null) {
				spriteImg = ImageIO.read(SpriteFactory.class.getResource("/GJ_GameSheet02-uhd.png"));
				loadSprites(new Configurations()
						.fileBased(XMLPropertyListConfiguration.class, SpriteFactory.class
								.getResource("/GJ_GameSheet02-uhd.plist")));
			}
			return new SpriteFactory();
		} catch (IOException | ConfigurationException e) {
			throw new SpriteLoadException(e);
		}
	}
}
