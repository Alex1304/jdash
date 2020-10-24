package com.github.alex1304.jdash.old.exception;

public class GDClientException extends RuntimeException {
	private static final long serialVersionUID = 4838833104661299105L;

	public GDClientException() {
		super();
	}

	public GDClientException(String message) {
		super(message);
	}

	public GDClientException(Throwable cause) {
		super(cause);
	}
}
