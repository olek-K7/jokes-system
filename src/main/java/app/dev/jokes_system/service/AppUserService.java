package app.dev.jokes_system.service;

import app.dev.jokes_system.dto.AppUserDto;
import app.dev.jokes_system.dto.AppUserJokeCount;
import app.dev.jokes_system.exceptions.UserNotFoundException;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableMethodSecurity
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<AppUserDto> getAllUsers(){return appUserRepository.findAll().stream().map(AppUserDto::fromEntity).toList();}
    // Retrieve user by id
    // I don't put ADMIN only security, as JokeService.createJoke() uses that method
    // I don't want to allow WRITER to use .createJoke and then block access to this method
    public AppUser getUserById(Integer id){
        return appUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    // Convert into AppUserDto for API retrieval
    @PreAuthorize("hasRole('ADMIN')")
    public AppUserDto getUserDtoById(Integer id) {
        AppUser user = getUserById(id);
        return AppUserDto.fromEntity(user);
    }
    // Retrieve user along with its number of jokes
    @PreAuthorize("hasRole('ADMIN')")
    public List<AppUserJokeCount> getUsersJokeCounts() {
        return appUserRepository.findAllUserJokeCounts();
    }

    // --- DELETION ---
    // Delete user's account including their jokes
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteUserById(Integer id){
        if(!appUserRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        appUserRepository.deleteById(id);
    }
}
