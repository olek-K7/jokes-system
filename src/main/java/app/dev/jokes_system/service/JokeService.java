package app.dev.jokes_system.service;

import app.dev.jokes_system.dto.JokeDto;
import app.dev.jokes_system.dto.JokeProjection;
import app.dev.jokes_system.dto.NewJokeRequest;
import app.dev.jokes_system.exceptions.BusinessRuleViolationException;
import app.dev.jokes_system.exceptions.DuplicateJokeException;
import app.dev.jokes_system.exceptions.JokeNotFoundException;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import app.dev.jokes_system.repository.JokeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
        // Check category
        // The only wrong value that might be is null, as we defined this attribute as enum, so Java restricts to these defined value

        // A joke must have a date, and it cannot be added with the future date
        if (joke.getDateAdded() == null ||
                joke.getDateAdded().isAfter(LocalDate.now())) {
            throw new BusinessRuleViolationException("The date must be provided and cannot be in the future");
        }
        if(joke.getCategory() == null){
            throw new BusinessRuleViolationException("The category field cannot be empty");
        }
        // Check language
        if(joke.getLanguage() == null){
            throw new BusinessRuleViolationException("The language field cannot be empty");
        }

        // Check joke's rating
        if(joke.getRating() < 1 || joke.getRating() > 5){
            throw new BusinessRuleViolationException("The joke's rating must be between 1 and 5");
        }

        // Check joke's minimumAge
        if(joke.getMinAge() < 0){
            throw new BusinessRuleViolationException("Minimum age cannot be negative");
        }

        // Check if user was provided
        if(joke.getAppUser() == null || joke.getAppUser().getUserId() == null){
            throw new BusinessRuleViolationException("The user ID must be provided");
        }

        // Check duplicate joke
        if(jokeRepository.existsBySetupAndPunchline(joke.getSetup(),joke.getPunchline())){
            throw new DuplicateJokeException();
        }

        // AppUser retrieved from DB through JPA
        AppUser appUser = appUserService.getUserById(joke.getAppUser().getUserId());
        joke.setAppUser(appUser);

        return jokeRepository.save(joke);
    }

    // --- CREATE JOKE ---
    // Create a joke from the incoming request DTO
    public JokeDto createJoke(NewJokeRequest request) {
        AppUser appUser = appUserService.getUserById(request.userId());

        // Convert the NewJokeRequest into Joke
        Joke joke = new Joke(
                request.setup(),
                request.punchline(),
                request.minAge(),
                request.rating(),
                request.category(),
                request.language(),
                appUser,
                request.dateAdded()
        );

        Joke savedJoke = addJoke(joke);

        // Return a JokeDTO instead of the full Joke entity
        return JokeDto.fromEntity(savedJoke);
    }
    // --- UPDATE JOKE ---
    // 1. UPDATE JOKE'S RATING
    public JokeDto updateJokeRating(Integer id, Integer newRating) {
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new JokeNotFoundException(id));


        joke.setRating(newRating);
        Joke updatedJoke = jokeRepository.save(joke);

        // Return JokeDto instead of a complete Joke object
        return JokeDto.fromEntity(updatedJoke);
    }

    // --- RETRIEVAL ---
    // GET ALL JOKES
    public List<JokeDto> getAllJokesByFilters() {
        return jokeRepository.findAll()
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }
    // GET ALL JOKES MATCHING OPTIONAL FILTERS
    public List<JokeDto> getAllJokesByFilters(
            JokeCategory category,
            JokeLanguage language,
            Integer minimumAge,
            Integer minimumRating) {

        return jokeRepository.findAllByFilters(
                        category,
                        language,
                        minimumAge,
                        minimumRating
                )
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
                .orElseThrow(() -> new JokeNotFoundException(id));
    }
    // 2. Returns JokeDto for API responses
    public JokeDto getJokeDtoById(Integer id){
        Joke joke = getJokeById(id);
        return JokeDto.fromEntity(joke);
    }

    // GET JOKE BY USER
    // Previously I had List<Joke> -> Each Joke has AppUser and each AppUser has a list of jokes -> Potencial Jackson Serialization Exception
    // So instead we return List<JokeDto>
    public List<JokeDto> getJokesByUserId(Integer id){
        return jokeRepository.findAllByAppUser_UserId(id)
                .stream()
                .map(JokeDto::fromEntity)
                .toList();
    }

    // GET ADDED JOKES AFTER DATE
    public List<JokeDto> getJokesAfterDate(LocalDate date){
        return jokeRepository.findAllByDateAddedGreaterThanEqual(date).stream().map(JokeDto::fromEntity).toList();
    }

    // --- PROJECTIONS ---
    public List<JokeProjection> getJokeProjections(){
        return jokeRepository.findAllProjections();
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
    public List<JokeProjection> getJokesSuitableForAge(int age) {
        return jokeRepository.findAllByMinAgeLessThanEqual(age)
                .stream()
                .map(JokeProjection::from)
                .toList();
    }
    // Filter jokes by minimum rating
    public List<JokeProjection> getJokesByGreaterRating(int rating) {
        return jokeRepository.findAllByRatingGreaterThanEqual(rating)
                .stream()
                .map(JokeProjection::from)
                .toList();
    }
    // Filter jokes by language
    public List<JokeProjection> getJokesByLanguage(JokeLanguage language) {
        return jokeRepository.findAllByLanguage(language)
                .stream()
                .map(JokeProjection::from)
                .toList();
    }

    // --- REMOVAL ---
    // Delete joke by ID
    @Transactional
    public void deleteJokeById(Integer id) {

        if (!jokeRepository.existsById(id)) {
            throw new JokeNotFoundException(id);
        }

        jokeRepository.deleteById(id);
    }
    // Delete jokes by Category
    // returns the number of removed jokes
    @Transactional
    public int deleteJokesByCategory(JokeCategory category){
        if(category == null){
            throw new BusinessRuleViolationException("Category is required for the removal");
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
