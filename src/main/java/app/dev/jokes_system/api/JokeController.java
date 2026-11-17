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

    // GET /api/jokes --> GET ALL JOKES
    // GET /api/jokes?category=DAD
    // GET /api/jokes?category=DAD&minimumAge=12
    @GetMapping()
    public List<JokeDto> getJokes(
            @RequestParam(required = false) JokeCategory category,
            @RequestParam(required = false) JokeLanguage language,
            @RequestParam(required = false) Integer minimumAge,
            @RequestParam(required = false) Integer minRating
    ) {
        return jokeService.getAllJokesByFilters(category, language, minimumAge, minRating);
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

    @DeleteMapping("/{id}")
    public void deleteJokeById(@PathVariable Integer id){
        jokeService.deleteJokeById(id);
    }

    @DeleteMapping(params = "category")
    public void deleteJokesByCategory(JokeCategory category){
        jokeService.deleteJokesByCategory(category);
    }
}
