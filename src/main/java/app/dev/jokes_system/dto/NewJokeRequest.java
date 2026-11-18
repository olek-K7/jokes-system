package app.dev.jokes_system.dto;

import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewJokeRequest(

        @NotBlank(message = "Joke setup must not be blank")
        String setup,

        @NotBlank(message = "Joke punchline must not be blank")
        String punchline,

        @NotNull(message = "Minimum age must be provided")
        @Min(value = 0, message = "Minimum age cannot be negative")
        Integer minAge,

        @NotNull(message = "Rating must be provided")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot exceed 5")
        Integer rating,

        @NotNull(message = "Category must be provided")
        JokeCategory category,

        @NotNull(message = "Language must be provided")
        JokeLanguage language,

        @NotNull(message = "User ID must be provided")
        @Positive(message = "User ID must be positive")
        Integer userId,

        @NotNull(message = "Date added must be provided")
        @PastOrPresent(message = "Date added cannot be in the future")
        LocalDate dateAdded

) {

    public static Joke toJoke(NewJokeRequest newJoke, AppUser appUser) {

        return new Joke(
                newJoke.setup(),
                newJoke.punchline(),
                newJoke.minAge(),
                newJoke.rating(),
                newJoke.category(),
                newJoke.language(),
                appUser,
                newJoke.dateAdded()
        );
    }
}