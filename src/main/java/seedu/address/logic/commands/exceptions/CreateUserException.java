package seedu.address.logic.commands.exceptions;

public class CreateUserException extends Exception {
    /**
     * Constructs a new {@code CreateUserException} with the specified detail {@code message}.
     */
    public CreateUserException() {
        super("Create User Exception");
    }

    public CreateUserException(String message) {
        super(message);
    }
}
