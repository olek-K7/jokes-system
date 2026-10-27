package app.dev.jokes_system.utilities;

import app.dev.jokes_system.model.JokeLanguage;
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

        // WEEK 6 TESTS using jokeRepository:
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

        // WEEK 7 TESTS using jokeService:

        // --- ADD JOKE RULE TESTS ---
        log.info("--- ADD JOKE RULE TESTS ---");

        // 1.1. Create a duplicate Joke in English
        Joke duplicateJoke = new Joke(
                "Why don't skeletons fight each other?",
                "They don't have the guts.",
                8,
                4,
                JokeCategory.DAD,
                JokeLanguage.ENGLISH
        );

        try {
            jokeService.addJoke(duplicateJoke);
        }
        catch (IllegalArgumentException e){
            log.info("Duplicate joke rejected: {}", e.getMessage());
        }
        // 1.2. Create a duplicate Joke in Spanish
        Joke duplicateSpanishJoke = new Joke(
                "¿Qué hace una vaca con los ojos cerrados?",
                "Leche concentrada.",
                5,
                5,
                JokeCategory.DAD,
                JokeLanguage.SPANISH
        );

        try {
            jokeService.addJoke(duplicateSpanishJoke);
        }
        catch (IllegalArgumentException e){
            log.info("Duplicate spanish joke rejected: {}", e.getMessage());
        }

        // 2. Create a Joke with an incorrect category
        Joke nullCategoryJoke = new Joke(
                "What's the difference between a piano and a fish",
                "You can tune a piano, but you can't tuna fish.",
                8,
                4,
                null,
                JokeLanguage.ENGLISH
        );

        // To avoid having application shut down -> try/catch
        try{
            jokeService.addJoke(nullCategoryJoke);
        }
        catch (IllegalArgumentException e){
            log.info("Invalid category rejected: {}", e.getMessage());
        }

        // 3. Create a Joke with an incorrect rating
        Joke invalidRating = new Joke(
                "Why was the computer cold?",
                "It left its Windows open.",
                8,
                99,
                JokeCategory.CODING,
                JokeLanguage.ENGLISH
        );

        try{
            jokeService.addJoke(invalidRating);
        }
        catch (IllegalArgumentException e){
            log.info("Invalid rating rejected: {}", e.getMessage());
        }

        // 4. Create a Joke with an incorrect age
        Joke invalidAge = new Joke(
                "Why did the programmer quit?",
                "Because he didn't get arrays.",
                -3,
                4,
                JokeCategory.CODING,
                JokeLanguage.ENGLISH
        );

        try{
            jokeService.addJoke(invalidAge);
        }
        catch (IllegalArgumentException e){
            log.info("Invalid minimumAge rejected: {}", e.getMessage());
        }

        // 5. Create a Joke with an incorrect language
        Joke nullLanguageJoke = new Joke(
                "What's the difference between a piano and a fish",
                "You can tune a piano, but you can't tuna fish.",
                8,
                4,
                JokeCategory.DAD,
                null
        );

        try{
            jokeService.addJoke(nullLanguageJoke);
        }
        catch (IllegalArgumentException e){
            log.info("Invalid language rejected: {}", e.getMessage());
        }
        // --- UPDATE TESTS---
        log.info("--- UPDATE TESTS ---");
        // 1. Valid patch
        int newRating = 2;
        int jokeIdToUpdate = 1;

        Joke jokeToUpdate = jokeService.getJokeById(jokeIdToUpdate);
        log.info("Previous joke {} rating: {}", jokeToUpdate.getId(), jokeToUpdate.getRating());

        jokeService.updateJokeRating(jokeToUpdate.getId(),newRating);
        log.info("Updated joke {} rating: {}", jokeService.getJokeById(jokeIdToUpdate).getId(), jokeService.getJokeById(jokeIdToUpdate).getRating());

        // 2. Invalid patch
        try {
            jokeService.updateJokeRating(jokeIdToUpdate, 8);
        } catch (IllegalArgumentException e) {
            log.info("Invalid rating update rejected: {}", e.getMessage());
        }

        // --- AGGREGATION  TESTS ---
        log.info("--- AGGREGATION TESTS ---");
        // 1. Get average rating of all jokes
        log.info("Average Jokes rating: {}", jokeService.getAverageJokesRating()) ;

        // --- RETRIEVAL TESTS ---
        log.info("--- RETRIEVAL TESTS ---");
        // 1. Get all jokes
        log.info("All jokes: ");
        jokeService.getAllJokes().forEach(joke -> log.info("{}",joke));

        // 2. Finding existing joke
        log.info("Joke number 4: ");
        try {
            log.info("{}",jokeService.getJokeById(4));
        } catch (IllegalArgumentException e){
            log.info("Missing joke: {}", e.getMessage());
        }

        // 3. Finding a joke that doesn't exist
        try {
            jokeService.getJokeById(999);
        } catch (IllegalArgumentException e){
            log.info("Missing joke: {}", e.getMessage());
        }

        // --- PROJECTION TESTS ---
         log.info("--- PROJECTION TESTS ---");
        // 1. Show only setups and punchlines
        log.info("Joke projections:");
        jokeService.getAllJokeProjections()
                .forEach(joke -> log.info("{} ==> {}", joke.setup(), joke.punchline()));

        // --- FILTERING TESTS---
        log.info("--- FILTERING TESTS ---");
        // 1. Filter by category
        log.info("Coding jokes:");
        jokeService.getJokesByCategory(JokeCategory.CODING).forEach(joke -> log.info("{}", joke));

        // 2. Filter by age
        log.info("Jokes suitable for age 13:");
        jokeService.getJokesByMinAge(13).forEach(joke -> log.info("{}", joke));

        // 3. Filter by rating
        log.info("Jokes rated 4 or higher:");
        jokeService.getJokesByRating(4).forEach(joke -> log.info("{}", joke));

        // 4. Filter by language
        log.info("English jokes:");
        jokeService.getJokesByLanguage(JokeLanguage.ENGLISH).forEach(joke -> log.info("Joke in english: {}", joke));

        log.info("Spanish jokes:");
        jokeService.getJokesByLanguage(JokeLanguage.SPANISH).forEach(joke -> log.info("Joke in spanish: {}", joke));

        // 5, Filter jokes in spanish directly through repository
        log.info("Spanish jokes from repository:");
        jokeRepository.findAllByLanguage(JokeLanguage.SPANISH)
                .forEach(joke -> log.info("{}", joke));

        // --- REMOVAL TESTS ---
        log.info("--- REMOVAL TESTS ---");
        // 1. Removing joke by id
        Joke newJoke = new Joke(
                "What's the difference between a piano and a fish",
                "You can tune a piano, but you can't tuna fish.",
                8,
                4,
                JokeCategory.DAD,
                JokeLanguage.ENGLISH
        );
        Joke savedJoke = jokeService.addJoke(newJoke);

        Integer id = savedJoke.getId();
        log.info("Joke before removal: {}", jokeService.getJokeById(id));
        jokeService.deleteJokeById(id);

        try {
            jokeService.getJokeById(id);
        } catch (IllegalArgumentException e) {
            log.info("After removal of the joke: {}", e.getMessage());
        }

        // 2. Removing joke by category
        log.info("Science jokes before deletion:");
        jokeService.getJokesByCategory(JokeCategory.SCIENCE)
                .forEach(joke -> log.info("{}", joke));

        int deleted = jokeService.deleteJokesByCategory(JokeCategory.SCIENCE);

        log.info("Deleted {} science jokes", deleted);

        log.info("Science jokes after deletion:");
        jokeService.getJokesByCategory(JokeCategory.SCIENCE)
                .forEach(joke -> log.info("{}", joke));

    }

}
