package app.dev.jokes_system.api;

import app.dev.jokes_system.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CentralizedExceptionHandler {

    // --- 1. NOT FOUND EXCEPTION HANDLER ---
    @ExceptionHandler({
            UserNotFoundException.class,
            JokeNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(
            RuntimeException e,
            HttpServletRequest request
    ){
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("status", "404");
        response.put("time", LocalDateTime.now().toString());
        response.put("request", request.getRequestURI());
        response.put("method", request.getMethod());

        return response;
    }
    // --- 2. BUSINESS RULE VIOLATION EXCEPTION HANDLER ---
    @ExceptionHandler(BusinessRuleViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public Map<String, String> handleBusinessRuleViolationException(
            RuntimeException e,
            HttpServletRequest request){

        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("status", "422");
        response.put("time", LocalDateTime.now().toString());
        response.put("request", request.getRequestURI());
        response.put("method", request.getMethod());

        return response;
    }

    // --- 3. DUPLICATE EXCEPTION HANDLER ---
    @ExceptionHandler({
            DuplicateJokeException.class,
            DuplicateUserException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicateException(
            RuntimeException e,
            HttpServletRequest request){

        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("status", "409");
        response.put("time", LocalDateTime.now().toString());
        response.put("request", request.getRequestURI());
        response.put("method", request.getMethod());

        return response;
    }

    // --- 4. MALFORMED JSON HANDLER ---
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(
            HttpMessageNotReadableException e,
            HttpServletRequest request) {

        Map<String, String> response = new HashMap<>();

        response.put("message", "Invalid request body or malformed JSON");
        response.put("status", "400");
        response.put("time", LocalDateTime.now().toString());
        response.put("request", request.getRequestURI());
        response.put("method", request.getMethod());

        return response;
    }
}
