package app.dev.jokes_system.utilities;

import app.dev.jokes_system.service.JokeService;
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
    private final JokeService jokeService;

    public DemoJokes(JokeRepository jokeRepository, JokeService jokeService) {
        this.jokeRepository = jokeRepository;
        this.jokeService = jokeService;
    }

    @Override
    public void run(String... args) {

        // Show all jokes
        /*
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
*/

        // Create a duplicate Joke
        Joke duplicateJoke = new Joke(
                "Why don't skeletons fight each other?",
                "They don't have the guts.",
                8,
                4,
                JokeCategory.DAD
        );

        try {
            jokeService.addJoke(duplicateJoke);
        }
        catch (IllegalArgumentException e){
            log.info("Duplicate joke rejected: {}", e.getMessage());
        }

        // Create a Joke with an incorrect category
        Joke nullCategoryJoke = new Joke(
                "What's the difference between a piano and a fish",
                "You can tune a piano, but you can't tuna fish.",
                8,
                4,
                null
        );

        // To avoid having application shut down
        try{
            jokeService.addJoke(nullCategoryJoke);
        }
        catch (IllegalArgumentException e){
            log.info("Invalid category rejected: {}", e.getMessage());
        }
        // Create a new Joke
        Joke newJoke = new Joke(
                "What's the difference between a piano and a fish",
                "You can tune a piano, but you can't tuna fish.",
                8,
                4,
                JokeCategory.DAD
        );
        jokeService.addJoke(newJoke);
        /*
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
         */
    }

}
