package com.github.alex1304.jdash.old.entity;

public enum Difficulty {
	NA(-1),
	AUTO(-2),
	EASY(1),
	NORMAL(2),
	HARD(3),
	HARDER(4),
	INSANE(5),
	DEMON(-3);
	
	private final int val;
	
	private Difficulty(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}