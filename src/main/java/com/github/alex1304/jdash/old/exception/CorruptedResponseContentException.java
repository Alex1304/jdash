package com.github.alex1304.jdash.old.exception;

import java.util.Map;
import java.util.Objects;

public final class CorruptedResponseContentException extends GDClientException {
	private static final long serialVersionUID = -5413594370168432534L;
	
	private final String requestPath;
	private final Map<String, String> requestParams;
	private String responseContent;
	
	public CorruptedResponseContentException(Throwable cause, String requestPath, Map<String, String> requestParams, String responseContent) {
		super(cause);
		this.requestPath = Objects.requireNonNull(requestPath);
		this.requestParams = Objects.requireNonNull(requestParams);
		this.responseContent = Objects.requireNonNull(responseContent);
	}

	public String getRequestPath() {
		return requestPath;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public String getResponseContent() {
		return responseContent;
	}
}
