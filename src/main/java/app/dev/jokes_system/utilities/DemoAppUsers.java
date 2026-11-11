package app.dev.jokes_system.utilities;

import app.dev.jokes_system.model.Joke;
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

        appUserService.getAllUsers().forEach(user -> log.info(
                "Retrieve all users(excluding their jokes): {} which is called {} {} with email {}",
                user.getUserId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
                ));
        log.info("Retrieve by id: User {} which is called {} {}", appUserService.getUserById(1).getUserId(), appUserService.getUserById(1).getFirstname(), appUserService.getUserById(1).getLastname());

        appUserService.getUserJokeCounts()
                .forEach(user -> log.info(
                        "User {} {} with email {} has {} jokes",
                        user.firstname(),
                        user.lastname(),
                        user.email(),
                        user.jokeCount()
                ));

        log.info("Jokes belonging to user 1: ");
        jokeService.getAllJokesByUserId(1)
                .forEach(joke -> log.info(
                        "Joke {}: {} ==> {}",
                        joke.getId(), joke.getSetup(), joke.getPunchline()));

        try {
            log.info("Removing user with id: {}", 1);
            appUserService.deleteUserById(1);
            log.info("Accessing jokes of the user: {}", 1);
            List<Joke> user1Jokes = jokeService.getAllJokesByUserId(1);
            log.info("The number of jokes: {}", user1Jokes.toArray().length);

        } catch (IllegalArgumentException e) {
            log.info("After removal of the user: {}", e.getMessage());
        }


    }
}
