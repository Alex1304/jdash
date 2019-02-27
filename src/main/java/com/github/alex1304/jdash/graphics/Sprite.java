package com.github.alex1304.jdash.graphics;

import java.awt.Rectangle;
import java.util.Objects;

class Sprite {
	private final String name;
	private final int offsetX, offsetY;
	private final int sizeX, sizeY;
	private final int sourceSizeX, sourceSizeY;
	private final Rectangle rectangle;
	private final boolean isRotated;
	
	Sprite(String name, int offsetX, int offsetY, int sizeX, int sizeY, int sourceSizeX, int sourceSizeY, Rectangle rectangle,
			boolean isRotated) {
		this.name = Objects.requireNonNull(name);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sourceSizeX = sourceSizeX;
		this.sourceSizeY = sourceSizeY;
		this.rectangle = Objects.requireNonNull(rectangle);
		this.isRotated = isRotated;
	}
	
	Sprite duplicate() {
		return new Sprite(name + "D", offsetX, offsetY, sizeX, sizeY, sourceSizeX, sourceSizeY, rectangle, isRotated);
	}
	
	String getName() {
		return name;
	}

	int getOffsetX() {
		return offsetX;
	}

	int getOffsetY() {
		return offsetY;
	}

	int getSizeX() {
		return sizeX;
	}

	int getSizeY() {
		return sizeY;
	}

	int getSourceSizeX() {
		return sourceSizeX;
	}

	int getSourceSizeY() {
		return sourceSizeY;
	}

	Rectangle getRectangle() {
		return rectangle;
	}

	boolean isRotated() {
		return isRotated;
	}

	@Override
	public String toString() {
		return "Sprite [name=" + name + ", offsetX=" + offsetX + ", offsetY=" + offsetY + ", sizeX=" + sizeX
				+ ", sizeY=" + sizeY + ", sourceSizeX=" + sourceSizeX + ", sourceSizeY=" + sourceSizeY + ", rectangle="
				+ rectangle + ", isRotated=" + isRotated + "]";
	}
}
