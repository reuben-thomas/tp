package seedu.address.commons.exceptions;

/**
 * Represents an error when trying to hash the password.
 */
public class HashingFunctionException extends RuntimeException {
    public HashingFunctionException(String message) {
        super(message);
    }
}
