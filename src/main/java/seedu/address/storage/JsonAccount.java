package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;

/**
 * Json-friendly version of {@link Account}
 */
public class JsonAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    private final String username;
    private final String password;

    /**
     * Construct a {@code JsonAccount} with the given account credentials
     *
     * @param username
     * @param password
     */
    @JsonCreator
    public JsonAccount(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given {@code Account} into this class for Json use.
     *
     * @param source
     */
    public JsonAccount(Account source) {
        this.username = source.getUsername();
        this.password = source.getPassword();
    }

    /**
     * Converts this Json-friendly adapted account object into the model's {@code Account} object.
     *
     * @return a validated Account
     * @throws IllegalValueException
     */
    public Account toModelType() throws IllegalValueException {
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "username"));
        }
        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "password"));
        }
        // can add password validation here if needed
        return new Account(username, password);
    }
}
