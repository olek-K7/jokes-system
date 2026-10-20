package app.dev.jokes_system.utilities;

import lombok.extern.slf4j.Slf4j;
import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import app.dev.jokes_system.repository.JokeRepository;

@Slf4j
@Component
@Profile("demo")
public class DemoJokes implements CommandLineRunner {
    private final JokeRepository jokeRepository;

    public DemoJokes(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public void run(String... args) {

        // Show all jokes
        log.info("All jokes:");
        jokeRepository.findAll().forEach(joke -> log.info("{}", joke));
        log.info("Number of jokes: {}", jokeRepository.count());

        // Filter by category
        log.info("Coding jokes:");
        jokeRepository.findAllByCategory(JokeCategory.CODING).forEach(joke -> log.info("{}", joke));

        // Filter by age
        log.info("Jokes suitable for age 13:");
        jokeRepository.findAllByMinAgeLessThanEqual(13).forEach(joke -> log.info("{}", joke));

        // Filter by rating
        log.info("Jokes rated 4 or higner:");
        jokeRepository.findAllByRatingGreaterThanEqual(4).forEach(joke -> log.info("{}", joke));

        // Projection
        log.info("Setup and punchline only:");
        jokeRepository.findAllJokeProjections().forEach(joke -> log.info("{}", joke));

        // Average rating
        log.info("Average rating: {}", jokeRepository.findAverageRating());

        // Create a new Joke
        Joke newJoke = new Joke(
                "What's the difference between a piano and a fish",
                "You can tune a piano, but you can't tuna fish.",
                8,
                4,
                JokeCategory.DAD
        );
        Joke savedJoke = jokeRepository.save(newJoke);
        Integer id = savedJoke.getId();

        log.info("Created joke with ID: {}", id);
        // Find by ID
        log.info("Found joke: {}", jokeRepository.findById(id));

        // Delete a new joke
//        jokeRepository.deleteById(id);
//        log.info("Joke found after deletion: {}",
//                jokeRepository.findById(id));
    }
}
