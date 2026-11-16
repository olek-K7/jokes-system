package app.dev.jokes_system.service;

import app.dev.jokes_system.dto.AppUserJokeCount;
import app.dev.jokes_system.exceptions.UserNotFoundException;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    // Forgot to add addUser method to check if email already exists

    AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository)
    {
        this.appUserRepository = appUserRepository;
    }

    // --- RETRIEVAL ---
    // Retrieve all users
    public List<AppUser> getAllUsers(){return appUserRepository.findAll();}
    // Retrieve user by id
    public AppUser getUserById(Integer id){return appUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));}
    // Retrieve user along with its number of jokes
    public List<AppUserJokeCount> getUserJokeCounts() {
        return appUserRepository.findAllUserJokeCounts();
    }

    // --- DELETION ---
    // Delete user's account including their jokes
    @Transactional
    public void deleteUserById(Integer id){
        if(!appUserRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        appUserRepository.deleteById(id);
    }
}
