package app.dev.jokes_system.dto;

import app.dev.jokes_system.model.Joke;

public record JokeProjection(String setup, String punchline) {
    public static JokeProjection from(Joke joke){
        return new JokeProjection(joke.getSetup(), joke.getPunchline());
    }
}
