package com.github.alex1304.jdash.client;

public class GDClientException extends Exception {
	private static final long serialVersionUID = 4838833104661299105L;
	
	private final int code;
	
	public GDClientException(int code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	
}
