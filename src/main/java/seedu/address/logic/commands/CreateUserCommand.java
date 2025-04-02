package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CreateUserException;
import seedu.address.model.Account;


/**
 * Creates a new account that you can log in with
 */
public class CreateUserCommand {
    public static final String MESSAGE_SUCCESS = "New user added.";
    public static final String MESSAGE_DUPLICATE_USER = "This account already exists!";

    private Logic logic;

    private final String username;
    private final String password;

    /**
     * Creates a CreateUserCommand to add the specified account
     * @param userName
     * @param password
     */
    public CreateUserCommand(String userName, String password, Logic logic) {
        this.username = userName;
        this.password = password;
        this.logic = logic;
    }

    /**
     * Creates a new account to be added and passes it to logic
     *
     * @return result message to be display on UI
     * @throws CreateUserException an Exception for failing to create account
     * @throws IOException an Exception for failing to save the account to json file
     */
    public String createNewUser() throws CreateUserException, IOException {
        toAdd = new Account(username, password);
        return logic.addNewUser(toAdd);
    }
}
