package app.dev.jokes_system.service;

import app.dev.jokes_system.dto.JokeDto;
import app.dev.jokes_system.dto.JokeProjection;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import app.dev.jokes_system.repository.AppUserRepository;
import app.dev.jokes_system.repository.JokeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JokeService {
    private final JokeRepository jokeRepository;
    private final AppUserService appUserService;
    // Constructor injection
    public JokeService(JokeRepository jokeRepository, AppUserService appUserService){
        this.jokeRepository = jokeRepository;
        this.appUserService = appUserService;
    }

    // --- ADD JOKE ---
    // Used for transactions that modify data
    @Transactional
    public Joke addJoke(Joke joke){

        // Check if user was provided
        if(joke.getAppUser() == null || joke.getAppUser().getUserId() == null){
            throw new IllegalArgumentException("The user ID must be provided");
        }
        // AppUser retrieved from DB through JPA
        AppUser appUser = appUserService.getUserById(joke.getAppUser().getUserId());
        joke.setAppUser(appUser);

        // Check category
        // The only wrong value that might be is null, as we defined this attribute as enum, so Java restricts to these defined value
        if(joke.getCategory() == null){
            throw new IllegalArgumentException("The category field cannot be empty");
        }
        // Check language
        if(joke.getLanguage() == null){
            throw new IllegalArgumentException("The language field cannot be empty");
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

    // --- RETRIEVAL ---
    // Get all jokes
    public List<JokeDto> getAllJokes() {
        return jokeRepository.findAll()
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }

    // GET JOKE BY ID
    // 1. Returns a JPA entity managed by Hibernate for internal operations such as update
    public Joke getJokeById(Integer id){
        // findById returns Optional, as joke with that ID might not exist
        // to keep Joke as a return type, I use orElseThrow() to throw an exception in case joke does not exist
        return jokeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Joke not found: " + id));
    }
    // 2. Returns JokeDto for API responses
    public JokeDto getJokeDtoById(Integer id){
        Joke joke = getJokeById(id);
        return JokeDto.fromEntity(joke);
    }

    // Previously I had List<Joke> -> Each Joke has AppUser and each AppUser has a list of jokes -> Potencial Jackson Serialization Exception
    // So instead we return List<JokeDto>
    public List<JokeDto> getAllJokesByUserId(Integer id){
        return jokeRepository.findAllJokesByAppUser_UserId(id)
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }

    // --- PROJECTIONS ---
    public List<JokeProjection> getAllJokeProjections(){
        return jokeRepository.findAllJokeProjections();
    }

    // --- FILTERING ---
    // Filter jokes by category
    public List<JokeDto> getJokesByCategory(JokeCategory category){
        return jokeRepository.findAllByCategory(category)
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }
    // Filter jokes by minimum age
    public List<JokeDto> getJokesByMinAge(int age) {
        return jokeRepository.findAllByMinAgeLessThanEqual(age)
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }
    // Filter jokes by minimum rating
    public List<JokeDto> getJokesByRating(int rating) {
        return jokeRepository.findAllByRatingGreaterThanEqual(rating)
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }
    // Filter jokes by language
    public List<JokeDto> getJokesByLanguage(JokeLanguage language) {
        return jokeRepository.findAllByLanguage(language)
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }
    // --- DELETION ---
    // Delete joke by ID
    @Transactional
    public void deleteJokeById(Integer id) {

        if (!jokeRepository.existsById(id)) {
            throw new IllegalArgumentException("Joke does not exit: " + id);
        }

        jokeRepository.deleteById(id);
    }
    // Delete jokes by Category
    // returns the number of removed jokes
    @Transactional
    public int deleteJokesByCategory(JokeCategory category){
        if(category == null){
            throw new IllegalArgumentException("Category is requiered for the deletion");
        }
        return jokeRepository.deleteAllByCategory(category);
    }

    // --- PATCH/UPDATE ---
    // Update rating
    // Retrieving an entity inside Transaction, Hibernate manages it
    // When managable entity is changed, Hibernate detects it and updates DB
    @Transactional
    public void updateJokeRating(Integer id, int newRating){

        if(newRating < 1 || newRating > 5){
            throw new IllegalArgumentException("The joke's rating must be between 1 and 5");
        }

        Joke joke = getJokeById(id);
        joke.setRating(newRating);

    }

    // --- AGGREGATION ---
    // Service calls repository for the calculations, because it's doing it efficiently using JPQL's AVG()
    public Double getAverageJokesRating(){
        return jokeRepository.findAverageRating();
    }
}
