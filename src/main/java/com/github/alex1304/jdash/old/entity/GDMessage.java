package com.github.alex1304.jdash.old.entity;

import java.util.Objects;
import java.util.function.Supplier;

import reactor.core.publisher.Mono;

public final class GDMessage extends AbstractGDEntity {
	private final long senderID;
	private final String senderName;
	private final String subject;
	private final Supplier<Mono<String>> body;
	private final boolean isRead;
	private final String timestamp;
	
	public GDMessage(long id, long senderID, String senderName, String subject,
			Supplier<Mono<String>> body, boolean isRead, String timestamp) {
		super(id);
		this.senderID = senderID;
		this.senderName = senderName;
		this.subject = subject;
		this.body = Objects.requireNonNull(body);
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
	
	/**
	 * Gets the message body. This has also the side-effect of marking the message
	 * as read server-side. Though it won't change the value returned by
	 * {@link #isRead()}.
	 * 
	 * @return a Mono emitting the message body
	 */
	public Mono<String> getBody() {
		return body.get();
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
