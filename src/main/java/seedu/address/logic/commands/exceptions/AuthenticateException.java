package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during authentication.
 */
public class AuthenticateException extends Exception {
    /**
     * Constructs a new {@code AuthenticateException} with the specified detail {@code message}.
     */
    public AuthenticateException(String message) {
        super(message);
    }
}
