package app.dev.jokes_system.service;

import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.repository.JokeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JokeService {
    private final JokeRepository jokeRepository;

    // Constructor injection
    public JokeService(JokeRepository jokeRepository){
        this.jokeRepository = jokeRepository;
    }

    // Used for transactions that modify data
    @Transactional
    public Joke addJoke(Joke joke){

        // Check category
        // The only wrong value that might be is null, as we defined this attribute as enum, so Java restricts to these defined value
        if(joke.getCategory() == null){
            throw new IllegalArgumentException("The category cannot be empty");
        }

        // Check joke's rating
        if(joke.getRating() < 1 || joke.getRating() > 5){
            throw new IllegalArgumentException("The joke's rating must be between 1 and 5");
        }

        // Check joke's minimumAge
        if(joke.getMinAge() < 0){
            throw new IllegalArgumentException("Minimum age cannot be negative");
        }

        // Check duplicate joke
        if(jokeRepository.existsBySetupAndPunchline(joke.getSetup(),joke.getPunchline())){
            throw new IllegalArgumentException("This joke already exists");
        }

        return jokeRepository.save(joke);
    }

    @Transactional
    public List<Joke> getAllJokes(){
        return jokeRepository.findAll();
    }


}
