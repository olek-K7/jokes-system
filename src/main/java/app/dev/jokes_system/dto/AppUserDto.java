package app.dev.jokes_system.dto;

import app.dev.jokes_system.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    private Integer userId;
    private String email;
    private String firstname;
    private String lastname;

    // Convert AppUser entity to AppUserDto
    public static AppUserDto fromEntity(AppUser user) {
        return new AppUserDto(
                user.getUserId(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname()
        );
    }
}