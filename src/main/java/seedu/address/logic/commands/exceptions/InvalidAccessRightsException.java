package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command} due to invalid access rights.
 */
public class InvalidAccessRightsException extends RuntimeException {
    /**
     * Constructs a new {@code InvalidAccessRightsException} with the specified detail {@code message}.
     */
    public InvalidAccessRightsException(String message) {
        super(message);
    }
}
