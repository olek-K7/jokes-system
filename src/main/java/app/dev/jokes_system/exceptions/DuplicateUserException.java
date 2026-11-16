package app.dev.jokes_system.exceptions;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
        super("This user already exists");
    }
}