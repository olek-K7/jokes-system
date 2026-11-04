package app.dev.jokes_system.service;

import app.dev.jokes_system.dto.AppUserJokeCount;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository)
    {
        this.appUserRepository = appUserRepository;
    }

    // --- RETRIEVAL ---
    // Retrieve all users
    public List<AppUser> getAllUsers(){return appUserRepository.findAll();}
    // Retrieve user by id
    public AppUser getUserById(Integer id){return appUserRepository.findById(id).orElseThrow();}
    public List<AppUserJokeCount> getUserJokeCounts() {
        return appUserRepository.findAllUserJokeCounts();
    }
}
