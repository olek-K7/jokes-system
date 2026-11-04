package app.dev.jokes_system.dto;

public record AppUserJokeCount(
    String email,
    String firstname,
    String lastname,
    // JPQL's COUNT() returns a Long
    Long jokeCount
) { }
