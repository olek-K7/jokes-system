package app.dev.jokes_system.dto;

import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JokeDto {

    private Integer jokeId;
    private String jokeSetup;
    private String jokePunchline;
    private int minimumAge;
    private int jokeRating;
    private JokeCategory jokeCategory;
    private JokeLanguage jokeLanguage;
    private AppUserDto jokeAuthor;

    // We create JokeDto and UserDto to avoid having a loop when Jackson maps Java into JSON
    // Convert Joke entity to JokeDto
    public static JokeDto fromEntity(Joke joke) {
        return new JokeDto(
                joke.getId(),
                joke.getSetup(),
                joke.getPunchline(),
                joke.getMinAge(),
                joke.getRating(),
                joke.getCategory(),
                joke.getLanguage(),
                AppUserDto.fromEntity(joke.getAppUser())
        );
    }
}