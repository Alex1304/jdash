package jdash.client.exception;

public class ResponseDeserializationException extends GDResponseException {

    public ResponseDeserializationException(String response, Throwable cause) {
        super(response, "Error when deserializing response", cause);
    }
}