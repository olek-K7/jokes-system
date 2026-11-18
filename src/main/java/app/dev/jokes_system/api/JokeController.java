package app.dev.jokes_system.api;

import app.dev.jokes_system.dto.JokeDto;
import app.dev.jokes_system.dto.JokeProjection;
import app.dev.jokes_system.dto.NewJokeRequest;
import app.dev.jokes_system.dto.UpdateJokeRatingRequest;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import app.dev.jokes_system.service.JokeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/added-after/{date}")
    public List<JokeDto> getJokesAfterDate(@PathVariable LocalDate date){
        return jokeService.getJokesAfterDate(date);
    }

    @DeleteMapping("/{id}")
    public void deleteJokeById(@PathVariable Integer id){
        jokeService.deleteJokeById(id);
    }

    @DeleteMapping(params = "category")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJokesByCategory(JokeCategory category){
        jokeService.deleteJokesByCategory(category);
    }

    // POST /api/jokes - CREATE A NEW JOKE
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public JokeDto createJoke(@Valid @RequestBody NewJokeRequest request) {
        return jokeService.createJoke(request);
    }

    // PATCH /api/jokes/{id}/rating - CHANGE JOKE'S RATING
    @PatchMapping("/{id}/rating")
    public JokeDto updateJokeRating(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateJokeRatingRequest request) {

        return jokeService.updateJokeRating(id, request.rating());
    }
}
