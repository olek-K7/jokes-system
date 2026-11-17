package app.dev.jokes_system.api;

import app.dev.jokes_system.dto.JokeDto;
import app.dev.jokes_system.dto.JokeProjection;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import app.dev.jokes_system.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jokes")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService jokeService;

    @GetMapping
    public List<JokeDto> getAllJokes() {
        return jokeService.getAllJokes();
    }

    @GetMapping("/{id}")
    public JokeDto getJokeById(@PathVariable Integer id) {
        return jokeService.getJokeDtoById(id);
    }

    @GetMapping("/summary")
    public List<JokeProjection> getAllJokeSummaries() {
        return jokeService.getJokeProjections();
    }

    @GetMapping("/average-rating")
    public Double getAverageJokeRating() {
        return jokeService.getAverageJokesRating();
    }

    // GET /api/jokes?category=DAD --> params=category
    @GetMapping(params = "category")
    public List<JokeDto> getJokesByCategory(
            @RequestParam JokeCategory category) {
        return jokeService.getJokesByCategory(category);
    }

    @GetMapping(params = "ageRating")
    public List<JokeProjection> getJokesSuitableForAge(
            @RequestParam int ageRating) {
        return jokeService.getJokesSuitableForAge(ageRating);
    }

    @GetMapping(params = "minimumRating")
    public List<JokeProjection> getJokesWithMinimumRating(
            @RequestParam int minimumRating) {
        return jokeService.getJokesByGreaterRating(minimumRating);
    }

    @GetMapping(params = "language")
    public List<JokeProjection> getJokesByLanguage(
            @RequestParam JokeLanguage language) {
        return jokeService.getJokesByLanguage(language);
    }
}
