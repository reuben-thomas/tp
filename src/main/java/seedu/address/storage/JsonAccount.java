package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;

public class JsonAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    private final String username;
    private final String password;

    @JsonCreator
    public JsonAccount(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public JsonAccount(Account source) {
        this.username = source.getUsername();
        this.password = source.getPassword();
    }

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
