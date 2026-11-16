package app.dev.jokes_system.exceptions;

public class HttpMessageNotReadableException extends RuntimeException {
    public HttpMessageNotReadableException(String message) {
        super("Invalid request body or malformed JSON");
    }
}
