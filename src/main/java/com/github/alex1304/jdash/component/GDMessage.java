package com.github.alex1304.jdash.component;

/**
 * Represents a private message in Geometry Dash
 * 
 * @author Alex1304
 */
public class GDMessage implements GDComponent {
	
	private long messageID;
	private long senderID;
	private String senderName;
	private String subject;
	private String body;
	private boolean isRead;
	private String timestamp;
	
	public GDMessage() {
	}
	
	/**
	 * @param messageID
	 *            - the unique ID of the message
	 * @param senderID
	 *            - the ID of the user who sent the message
	 * @param senderName
	 *            - the name of the user who sent the message
	 * @param subject
	 *            - the subject of the message
	 * @param body
	 *            - the subject of the message
	 * @param isRead
	 *            - whether the message is marked as read
	 * @param timestamp
	 *            - the timestamp of the message
	 */
	public GDMessage(long messageID, long senderID, String senderName, String subject, String body, boolean isRead,
			String timestamp) {
		this.messageID = messageID;
		this.senderID = senderID;
		this.senderName = senderName;
		this.subject = subject;
		this.body = body;
		this.isRead = isRead;
		this.timestamp = timestamp;
	}
	
	/**
	 * Gets the unique ID of the message
	 * 
	 * @return long
	 */
	public long getMessageID() {
		return messageID;
	}

	/**
	 * Gets the ID of the user who sent the message
	 * 
	 * @return long
	 */
	public long getSenderID() {
		return senderID;
	}

	/**
	 * Gets the ID of the user who sent the message
	 * 
	 * @return long
	 */
	public String getSenderName() {
		return senderName;
	}
	
	/**
	 * Gets the subject of the message
	 * 
	 * @return String
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Gets the body of the message
	 * 
	 * @return String
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * Gets whether the message is marked as read
	 * 
	 * @return boolean
	 */
	public boolean isRead() {
		return isRead;
	}
	
	/**
	 * Gets the timestamp of the message
	 * 
	 * @return String
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the messageID
	 *
	 * @param messageID - long
	 */
	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	/**
	 * Sets the senderID
	 *
	 * @param senderID - long
	 */
	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}

	/**
	 * Sets the senderName
	 *
	 * @param senderName - String
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * Sets the subject
	 *
	 * @param subject - String
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Sets the body
	 *
	 * @param body - String
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Sets the isRead
	 *
	 * @param isRead - boolean
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * Sets the timestamp
	 *
	 * @param timestamp - String
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "GDMessage [messageID=" + messageID + ", senderID=" + senderID + ", senderName=" + senderName
				+ ", subject=" + subject + ", body=" + body + ", isRead=" + isRead + ", timestamp=" + timestamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (messageID ^ (messageID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GDMessage))
			return false;
		GDMessage other = (GDMessage) obj;
		if (messageID != other.messageID)
			return false;
		return true;
	}
	
}
