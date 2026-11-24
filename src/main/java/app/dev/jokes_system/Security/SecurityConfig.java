package app.dev.jokes_system.Security;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //  BCrypt to verify passwords against their stored hashes
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure which HTTP requests require authentication.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Allow Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // Allow H2 web console access for local development
                        .requestMatchers(
                                PathRequest.toH2Console()
                        ).permitAll()

                        // Anyone is allowed to read jokes
                        .requestMatchers(
                                HttpMethod.GET, "/api/jokes/**", "/api/jokes"
                        ).permitAll()

                        // Only ADMIN can access user endpoints
                        .requestMatchers(
                                "/api/users", "/api/users/**"
                        ).hasRole("ADMIN")

                        // Actuator requires authentication
                        .requestMatchers(
                                "/actuator/**"
                        ).authenticated()

                        // POST, PATCH, DELETE require any type of authentication (doesn't matter the role yet)
                        .anyRequest().authenticated()
                )
                //  Frames should be shown without authentication but only if they come from the same origin
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                // Authenticate using HTTP Basic
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}