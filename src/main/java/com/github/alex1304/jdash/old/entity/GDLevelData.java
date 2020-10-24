package com.github.alex1304.jdash.old.entity;

import java.util.Objects;

public final class GDLevelData extends AbstractGDEntity {

	private final int pass;
	private final String uploadTimestamp;
	private final String lastUpdatedTimestamp;
	private final byte[] data;

	public GDLevelData(long id, int pass, String uploadTimestamp, String lastUpdatedTimestamp, byte[] data) {
		super(id);
		this.pass = pass;
		this.uploadTimestamp = Objects.requireNonNull(uploadTimestamp);
		this.lastUpdatedTimestamp = Objects.requireNonNull(lastUpdatedTimestamp);
		this.data = Objects.requireNonNull(data);
	}

	public int getPass() {
		return pass;
	}

	public String getUploadTimestamp() {
		return uploadTimestamp;
	}

	public String getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	public byte[] getData() {
		return data;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDLevelData && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDLevelData [pass=" + pass + ", uploadTimestamp=" + uploadTimestamp + ", lastUpdatedTimestamp="
				+ lastUpdatedTimestamp + ", data={" + data.length + " Bytes}]";
	}
}
