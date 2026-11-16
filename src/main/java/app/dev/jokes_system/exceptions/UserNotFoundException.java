package app.dev.jokes_system.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer id) {
        super("User with ID " + id + " does not exist");
    }
}