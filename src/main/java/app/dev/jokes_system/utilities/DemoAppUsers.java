package app.dev.jokes_system.utilities;

import app.dev.jokes_system.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("demoAppUsers")
public class DemoAppUsers implements CommandLineRunner {
    private final AppUserService appUserService;

    public DemoAppUsers(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @Override
    public void run(String... args){
        log.info("--- WEEK 8: APP USER PROJECTIONS ---");

        appUserService.getAllUsers().forEach(user -> log.info(
                "User {} which is called {} {} with email {}",
                user.getUserId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
                ));
        log.info("User {} which is called", appUserService.getUserById(1).getUserId(), appUserService.getUserById(1).getFirstname(), appUserService.getUserById(1).getLastname());

        appUserService.getUserJokeCounts()
                .forEach(user -> log.info(
                        "User {} {} with email {} has {} jokes",
                        user.firstname(),
                        user.lastname(),
                        user.email(),
                        user.jokeCount()
                ));

    }
}
