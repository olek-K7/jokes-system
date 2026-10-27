package app.dev.jokes_system.repository;

import app.dev.jokes_system.dto.JokeProjection;
import app.dev.jokes_system.model.Joke;
import app.dev.jokes_system.model.JokeCategory;
import app.dev.jokes_system.model.JokeLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository is created from Joke entity
// Generated implementation is configured by Spring into a bean called jokeRepository and creates several CRD operations
// Create Read Delete
public interface JokeRepository extends JpaRepository<Joke, Integer> {

    // --- BUSINESS LOGIC ---
    // Check if the same setup/punchline already exists
    boolean existsBySetupAndPunchline(String setup, String punchline);

    // --- FILTERING ---
    // Filter all jokes from a given category
    List<Joke> findAllByCategory(JokeCategory category);
    // To search for all jokes suitable for a given age
    List<Joke> findAllByMinAgeLessThanEqual(int age);
    // To filter all jokes for a minimum rating
    List<Joke> findAllByRatingGreaterThanEqual(int rating);
    // To filter all jokes by language
    List<Joke> findAllByLanguage(JokeLanguage language);

    // --- PROJECTIONS ---
    // Using JPQL
    @Query("SELECT new app.dev.jokes_system.dto.JokeProjection(j.setup, j.punchline) from Joke j")
    List<JokeProjection> findAllJokeProjections();

    // --- AGGREGATE ---
    // Using JPQL
    @Query("SELECT AVG(j.rating) from Joke j ")
    Double findAverageRating();

    // --- DELETION ---
    // Using my own delete, to use maximum efficiency
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Joke j WHERE j.category = :category")
    int deleteAllByCategory(@Param("category") JokeCategory category);
}
