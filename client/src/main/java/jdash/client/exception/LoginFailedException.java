package jdash.client.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(Throwable cause) {
        super(cause);
    }
}