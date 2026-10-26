package app.dev.jokes_system.service;

import app.dev.jokes_system.dto.JokeProjection;
import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.repository.JokeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Joke> getAllJokes(){
        return jokeRepository.findAll();
    }

    public Joke getJokeById(Integer id){
        // findById returns Optional, as joke with that ID might not exist
        // to keep Joke as a return type, I use orElseThrow() to throw an exception in case joke does not exist
        return jokeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Joke not found: " + id));

    }

    // --- PROJECTIONS ---
    public List<JokeProjection> getAllJokeProjections(){
        return jokeRepository.findAllJokeProjections();
    }

    // --- FILTERING ---
    // Filter jokes by category
    public List<Joke> getJokesByCategory(JokeCategory category){
        return jokeRepository.findAllByCategory(category);
    }
    // Filter jokes by minimum age
    public List<Joke> getJokesByMinAge(int age) {
        return jokeRepository.findAllByMinAgeLessThanEqual(age);
    }
    // Filter jokes by minimum rating
    public List<Joke> getJokesByRating(int rating) {
        return jokeRepository.findAllByRatingGreaterThanEqual(rating);
    }

    // Delete Joke by ID
    @Transactional
    public void deleteJokeById(Integer id) {

        if (!jokeRepository.existsById(id)) {
            throw new IllegalArgumentException("Joke does not exit: " + id);
        }

        jokeRepository.deleteById(id);
    }
    // Delete Jokes by Category
    // returns the number of removed jokes
    @Transactional
    public int deleteJokesByCategory(JokeCategory category){
        if(category == null){
            throw new IllegalArgumentException("Category is requiered for the deletion");
        }
        return jokeRepository.deleteAllByCategory(category);
    }
}
