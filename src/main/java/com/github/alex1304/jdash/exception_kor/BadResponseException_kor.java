package com.github.alex1304.jdash.exception;


import java.util.Objects;


import reactor.netty.http.client.HttpClientResponse;


/**
 * Thrown when the Geometry Dash API returns an HTTP error
 
 (Geometry Dash API가 HTTP 오류를 반환할 때 사용됨) */
public final class BadResponseException extends GDClientException
 {

private static final long serialVersionUID = -8098451293881609350L;

private final HttpClientResponse response;

public BadResponseException(HttpClientResponse response) 
{

this.response = Objects.requireNonNull(response);

}

public HttpClientResponse getResponse()
{

return response;

}

@Override
	
public String toString()
 {

return response.status().codeAsText() + " " + response.status().reasonPhrase();

}

}
