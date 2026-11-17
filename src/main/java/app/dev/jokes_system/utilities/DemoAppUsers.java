package app.dev.jokes_system.utilities;

import app.dev.jokes_system.dto.JokeDto;
import app.dev.jokes_system.exceptions.UserNotFoundException;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import app.dev.jokes_system.service.AppUserService;
import app.dev.jokes_system.service.JokeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("demoAppUsers")
public class DemoAppUsers implements CommandLineRunner {
    private final AppUserService appUserService;
    private final JokeService jokeService;

    public DemoAppUsers(AppUserService appUserService, JokeService jokeService){
        this.appUserService = appUserService;
        this.jokeService = jokeService;
    }

    @Override
    public void run(String... args){
        log.info("--- WEEK 8: APP USER PROJECTIONS ---");

        // --- RETRIEVAL TESTS ---
        // --- 1. GET ALL USERS (EXCLUDING THEIR JOKES) ---
        appUserService.getAllUsers().forEach(user -> log.info(
                "Retrieve all users(excluding their jokes): {} which is called {} {} with email {}",
                user.getUserId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
                ));

        // --- 2. GET USER BY ID ---
        log.info("Retrieve by id: User {} which is called {} {}", appUserService.getUserById(1).getUserId(), appUserService.getUserById(1).getFirstname(), appUserService.getUserById(1).getLastname());

        // --- 3. GET NUMBER OF JOKES PER USER (PROJECTION) ---
        appUserService.getUsersJokeCounts()
                .forEach(user -> log.info(
                        "User {} {} with email {} has {} jokes",
                        user.firstname(),
                        user.lastname(),
                        user.email(),
                        user.jokeCount()
                ));
        // --- 4. GET ALL JOKES BELONGING TO USER 1 (DTO) ---
        log.info("Jokes belonging to user 1: ");
        jokeService.getJokesByUserId(1)
                .forEach(joke -> log.info(
                        "Joke {}: {} ==> {}",
                        joke.getJokeId(), joke.getJokeSetup(), joke.getJokePunchline()));

        // --- 5. GET ALL JOKES WITH RATING GREATER OR EQUAL THEN 5
        int minRating = 5;
        log.info("TEST 5: GET ALL JOKES WITH RATING GREATER OR EQUAL THEN {}",minRating);
        jokeService.getJokesByGreaterRating(minRating).forEach(jokeProjection -> log.info("Joke: {} -> {}", jokeProjection.setup(), jokeProjection.punchline()));

        // --- 6. GET ALL JOKES SUITABLE FOR AGE 10
        int minAge = 10;
        log.info("TEST 6: GET ALL JOKES SUITABLE FOR AGE {}", minAge);
        jokeService.getJokesSuitableForAge(minAge).forEach(jokeProjection -> log.info("Joke: {} -> {}", jokeProjection.setup(), jokeProjection.punchline()));

        // --- REMOVAL TESTS ---
        // --- REMOVING USER BY ID AND CHECK THEIR JOKES ---
        try {
            log.info("Removing user with id: {}", 1);
            appUserService.deleteUserById(1);

            log.info("Accessing jokes of the user: {}", 1);
            List<JokeDto> user1Jokes = jokeService.getJokesByUserId(1);
            log.info("The number of jokes: {}", user1Jokes.size());

        } catch (UserNotFoundException e) {
            log.info("After removal of the user: {}", e.getMessage());
        }

        // --- ADD JOKE TESTS ---
        // --- 1. ADD JOKE WITH NONEXISTENT USER ---
        AppUser fakeUser = new AppUser();
        fakeUser.setUserId(999);

        Joke nonExistingUserJoke = new Joke("¿Qué le dice un techo a otro?",
                "Techo de menos.",
                1,
                5,
                JokeCategory.DAD,
                JokeLanguage.SPANISH,
                fakeUser
                );
        try {
            log.info("TEST: ADDING JOKE WITH NONEXISTING USER");
            jokeService.addJoke(nonExistingUserJoke);
        }
        catch (UserNotFoundException e){
            log.info("TEST PASSED: {}", e.getMessage());
        }
        // --- 2. ADD JOKE WITH EXISTING USER ---
        AppUser realUser = appUserService.getUserById(2);
        Joke existingUserJoke = new Joke("¿Qué le dice un techo a otro?",
                "Techo de menos.",
                1,
                5,
                JokeCategory.DAD,
                JokeLanguage.SPANISH,
                realUser
        );
        try {
            log.info("TEST: ADDING JOKE TO AN EXISTING USER");
            jokeService.addJoke(existingUserJoke);
        }
        catch (UserNotFoundException e){
            log.info("TEST DIDN'T PASS: {}", e.getMessage());
        }
    }
}
