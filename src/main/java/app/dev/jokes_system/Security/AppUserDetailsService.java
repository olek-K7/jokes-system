package app.dev.jokes_system.Security;

import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.repository.AppUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        // Load AppUser by email
        AppUser user = appUserRepository.findByEmail(email).
                            orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert AppUser into Spring Security's UserDetails
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .accountLocked(user.isLocked())
                .build();
    }
}
