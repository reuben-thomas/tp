package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CreateUserException;
import seedu.address.model.Account;


/**
 * Creates a new account that you can log in with
 */
public class CreateUserCommand {
    public static final String MESSAGE_SUCCESS = "New user added: %1$s";
    public static final String MESSAGE_DUPLICATE_USER = "This account already exists!";
    public Account toAdd;
    public Logic logic;

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

    public String createNewUser() throws CreateUserException, IOException {
        toAdd = new Account(username, password);
        return logic.addNewUser(toAdd);
    }
}
