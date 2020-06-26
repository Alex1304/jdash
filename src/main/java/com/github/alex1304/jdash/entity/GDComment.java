package com.github.alex1304.jdash.entity;

import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Supplier;

public final class GDComment extends AbstractGDEntity {

    private final long senderID;
    private final String senderName;
    private final String body;
    private final int likes;
    private final String uploadedTimestamp;
    private final int percentage;
    private final int senderRole;
    private final Supplier<Mono<GDUserProfileData>> senderData;

    public GDComment(long id, long senderID, String senderName, String body, int likes, String uploadedTimestamp, int percentage, int senderRole, Supplier<Mono<GDUserProfileData>> senderData) {
        super(id);
        this.senderID = senderID;
        this.senderName = senderName;
        this.body = Objects.requireNonNull(body);
        this.likes = likes;
        this.uploadedTimestamp = Objects.requireNonNull(uploadedTimestamp);
        this.percentage = percentage;
        this.senderRole = senderRole;
        this.senderData = Objects.requireNonNull(senderData);
    }

    public long getSenderID() {
        return senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getBody() {
        return body;
    }

    public int getLikes() {
        return likes;
    }

    public String getUploadedTimestamp() {
        return uploadedTimestamp;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getSenderRole() {
        return senderRole;
    }

    public Mono<GDUserProfileData> getSenderData() {
        return senderData.get();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GDComment && super.equals(obj);
    }

    @Override
    public String toString() {
        return "GDUserSearchData [senderID=" + senderID + ", senderName=" + senderName + ", body="
                + body + ", likes=" + likes + ", uploadedTimestamp=" + uploadedTimestamp + ", percentage=" + percentage
                + ", senderRole=" + senderRole + ", senderData=" + senderData.toString() + ", id=" + id + "]";
    }
}

