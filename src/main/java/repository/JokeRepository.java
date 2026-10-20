package repository;

import dto.JokeProjection;
import model.Joke;
import model.JokeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository is created from Joke entity
// Generated implementation is configured by Spring into a bean called jokeRepository and creates several CRD operations
// Create Read Delete
public interface JokeRepository extends JpaRepository<Joke, Integer> {

    // --- FILTERING ---
    // Filter all jokes from a given category
    List<Joke> findAllByCategory(JokeCategory category);
    // To search for all jokes suitable for a given age
    List<Joke> findAllByMinAgeLessThanEqual(int age);
    // To filter all jokes for a minimum rating
    List<Joke> findAllByRatingGreaterThanEqual(int rating);

    // --- PROJECTIONS ---
    // Using JPQL
    @Query("SELECT new dto.JokeProjection(j.setup, j.punchline) from Joke j")
    List<JokeProjection> findAllJokeProjections();

    // --- Aggregate ---
    // Using JPQL
    @Query("SELECT AVG(j.rating) from Joke j ")
    Double findAverageRating();
}
