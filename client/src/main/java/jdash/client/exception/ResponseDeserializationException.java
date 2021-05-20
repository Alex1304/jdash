package jdash.client.exception;

/**
 * Emitted when something goes wrong while deserializing the raw response into an object.
 */
public class ResponseDeserializationException extends GDResponseException {

    public ResponseDeserializationException(String response, Throwable cause) {
        super(response, "Error when deserializing response", cause);
    }
}