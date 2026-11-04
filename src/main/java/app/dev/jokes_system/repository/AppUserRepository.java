package app.dev.jokes_system.repository;

import app.dev.jokes_system.dto.AppUserJokeCount;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    // --- RETRIEVAL ---
    // Retrieve all users (excluding their jokes) -> JpaRepository already provides findAll()
    // Retrieve a user by their ID (excluding their jokes) -> JpaRepository already provides findById(userId)

    // --- PROJECTIONS ---
    // Creating DTO directly from the query result
    // LEFT JOIN - to get all users, including those with 0 jokes
    @Query("SELECT new app.dev.jokes_system.dto.AppUserJokeCount(u.email, u.firstname, u.lastname, COUNT(j)) FROM AppUser u LEFT JOIN u.jokes j" +
            " GROUP BY u.userId, u.email, u.firstname, u.lastname")
    List<AppUserJokeCount> findAllUserJokeCounts();

}
