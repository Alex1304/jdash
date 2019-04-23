package com.github.alex1304.jdash.entity;

import java.util.Objects;
import java.util.function.Supplier;

import reactor.core.publisher.Mono;

public final class GDTimelyLevel extends AbstractGDEntity {
	public static enum TimelyType {
		DAILY(-1), WEEKLY(-2);
		private final int downloadId;
		TimelyType(int downloadId) {
			this.downloadId = downloadId;
		}
		public int getDownloadId() {
			return downloadId;
		}
	}
	private final long cooldown;
	private final Mono<GDLevel> level;
	private final TimelyType type;

	public GDTimelyLevel(long id, long cooldown, Supplier<Mono<GDLevel>> levelLoader, TimelyType type) {
		super(id);
		this.cooldown = cooldown;
		this.level = Objects.requireNonNull(levelLoader).get().cache();
		this.type = Objects.requireNonNull(type);
	}

	public long getCooldown() {
		return cooldown;
	}

	public Mono<GDLevel> getLevel() {
		return level;
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
