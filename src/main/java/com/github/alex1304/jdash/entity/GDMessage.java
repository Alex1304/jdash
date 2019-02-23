package com.github.alex1304.jdash.entity;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.alex1304.jdash.util.LazyProperty;

import reactor.core.publisher.Mono;

public class GDMessage extends AbstractGDEntity {
	public static class Content extends AbstractGDEntity {
		private final String body;
		public Content(long id, String body) {
			super(id);
			this.body = Objects.requireNonNull(body);
		}
		public String getBody() {
			return body;
		}
		@Override
		public String toString() {
			return "Content [body=" + body + ", id=" + id + "]";
		}
	}
	private final long senderID;
	private final String senderName;
	private final String subject;
	private final LazyProperty<Content> content;
	private final boolean isRead;
	private final String timestamp;
	
	public GDMessage(long id, long senderID, String senderName, String subject,
			Supplier<Mono<Content>> content, boolean isRead, String timestamp) {
		super(id);
		this.senderID = senderID;
		this.senderName = senderName;
		this.subject = subject;
		this.content = new LazyProperty<>(content);
		this.isRead = isRead;
		this.timestamp = timestamp;
	}

	public long getSenderID() {
		return senderID;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getSubject() {
		return subject;
	}

	public Mono<Content> getContent() {
		return content.getValue();
	}

	public boolean isRead() {
		return isRead;
	}

	public String getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "GDMessage [senderID=" + senderID + ", senderName=" + senderName + ", subject=" + subject + ", isRead=" + isRead + ", timestamp=" + timestamp + ", id=" + id + "]";
	}
}
