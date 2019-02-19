package com.github.alex1304.jdash.client;

public class GDClientException extends Exception {
	private static final long serialVersionUID = 4838833104661299105L;

	GDClientException() {
		super();
	}

	GDClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	GDClientException(String message, Throwable cause) {
		super(message, cause);
	}

	GDClientException(String message) {
		super(message);
	}

	GDClientException(Throwable cause) {
		super(cause);
	}
}
