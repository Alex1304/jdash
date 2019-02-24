package com.github.alex1304.jdash.entity;

import java.util.Objects;
import java.util.function.Supplier;

import reactor.core.publisher.Mono;

public class GDMessage extends AbstractGDEntity {
	private final long senderID;
	private final String senderName;
	private final String subject;
	private final Supplier<Mono<String>> content;
	private final boolean isRead;
	private final String timestamp;
	
	public GDMessage(long id, long senderID, String senderName, String subject,
			Supplier<Mono<String>> content, boolean isRead, String timestamp) {
		super(id);
		this.senderID = senderID;
		this.senderName = senderName;
		this.subject = subject;
		this.content = Objects.requireNonNull(content);
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

	public Mono<String> getContent() {
		return content.get();
	}

	public boolean isRead() {
		return isRead;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDMessage && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDMessage [senderID=" + senderID + ", senderName=" + senderName + ", subject=" + subject + ", isRead=" + isRead + ", timestamp=" + timestamp + ", id=" + id + "]";
	}
}
