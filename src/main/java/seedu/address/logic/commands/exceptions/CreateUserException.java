package seedu.address.logic.commands.exceptions;

/**
 * Exception that represents an error that occurred while registering a new account
 */
public class CreateUserException extends Exception {
    /**
     * Constructs a new {@code CreateUserException} with the default detail {@code message}.
     */
    public CreateUserException() {
        super("Create User Exception");
    }
    /**
     * Constructs a new {@code CreateUserException} with the specified detail {@code message}.
     */
    public CreateUserException(String message) {
        super(message);
    }
}
