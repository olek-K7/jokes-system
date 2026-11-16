package app.dev.jokes_system.exceptions;

public class BusinessRuleViolationException extends RuntimeException {

    public BusinessRuleViolationException(String reason) {
        super(reason);
    }
}