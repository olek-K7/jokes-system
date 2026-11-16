package app.dev.jokes_system.exceptions;

public class DuplicateJokeException extends RuntimeException {

    public DuplicateJokeException() {
        super("This joke already exists");
    }
}