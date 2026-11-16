package app.dev.jokes_system.api;

import app.dev.jokes_system.dto.JokeDto;
import app.dev.jokes_system.service.JokeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    JokeService jokeService;
    @Operation(description = "Finding all the jokes of a person")
    @GetMapping("users/{id}/jokes")
    List<JokeDto> getAllJokesByUserId(@PathVariable Integer id){
        return jokeService.getAllJokesByUserId(id);
    }
}
