package com.github.alex1304.jdash.entity;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.alex1304.jdash.util.Indexes;

import reactor.core.publisher.Mono;

public class GDTimelyLevel extends AbstractGDEntity {
	public static enum TimelyType {
		DAILY(Indexes.DAILY_LEVEL_ID), WEEKLY(Indexes.WEEKLY_DEMON_ID);
		private final int downloadId;
		TimelyType(int downloadId) {
			this.downloadId = downloadId;
		}
		public int getDownloadId() {
			return downloadId;
		}
	}
	private final long cooldown;
	private final Supplier<Mono<GDLevel>> levelLoader;
	private final TimelyType type;

	public GDTimelyLevel(long id, long cooldown, Supplier<Mono<GDLevel>> levelLoader, TimelyType type) {
		super(id);
		this.cooldown = cooldown;
		this.levelLoader = Objects.requireNonNull(levelLoader);
		this.type = Objects.requireNonNull(type);
	}

	public long getCooldown() {
		return cooldown;
	}

	public Mono<GDLevel> getLevel() {
		return levelLoader.get();
	}
	
	public TimelyType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDTimelyLevel && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDTimelyLevel [cooldown=" + cooldown + ", type=" + type + ", id=" + id + "]";
	}
}
