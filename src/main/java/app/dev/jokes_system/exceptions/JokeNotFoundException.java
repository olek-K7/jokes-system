package app.dev.jokes_system.exceptions;

public class JokeNotFoundException extends RuntimeException {

    public JokeNotFoundException(Integer id) {
        super("Joke with ID " + id + " does not exist");
    }
}