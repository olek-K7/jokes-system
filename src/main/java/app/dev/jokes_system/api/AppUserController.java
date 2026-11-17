package app.dev.jokes_system.api;

import app.dev.jokes_system.dto.AppUserDto;
import app.dev.jokes_system.dto.AppUserJokeCount;
import app.dev.jokes_system.dto.JokeDto;
import app.dev.jokes_system.model.AppUser;
import app.dev.jokes_system.service.AppUserService;
import app.dev.jokes_system.service.JokeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {
    JokeService jokeService;
    AppUserService appUserService;

    @GetMapping()
    public List<AppUserDto> getAllUsers(){
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUserDto getUserById(@PathVariable Integer id){
        return appUserService.getUserDtoById(id);
    }

    @GetMapping("/summary")
    public List<AppUserJokeCount> getUsersJokeCounts(){
        return appUserService.getUsersJokeCounts();
    }

    @Operation(description = "Finding all the jokes of a person")
    @GetMapping("/{id}/jokes")
    public List<JokeDto> getAllJokesByUserId(@PathVariable Integer id){
        return jokeService.getJokesByUserId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Here we return NO CONTENT 204 for the deleted record.
    public void deleteUserById(@PathVariable Integer id){
        appUserService.deleteUserById(id);
    }
}
