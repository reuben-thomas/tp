package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.AuthenticateException;
import seedu.address.model.Account;

/**
 * Authenticates the user
 */
public class AuthenticateCommand {

    public static final String MESSAGE_SUCCESS = "Authenticated User";
    public static final String MESSAGE_FAILURE = "Login Failed. Invalid username or password.";

    private final Logger logger = LogsCenter.getLogger(AuthenticateCommand.class);

    private final String username = "Admin";
    private final String password = "Admin@123";

    private final String usernameInput;
    private final String passwordInput;

    /**
     * Constructor that takes in username and password from LoginDialog stage
     *
     * @param username username field
     * @param password password field
     */
    public AuthenticateCommand(String username, String password) {
        this.usernameInput = username;
        this.passwordInput = password;
    }

    /**
     * Attempt to authenticate user
     *
     * @param logic LogicManager for MainWindow
     * @throws AuthenticateException exception for authenticate failure
     */
    public String authenticateUser(Logic logic) throws AuthenticateException {
        ArrayList<Account> accountsIT = retrieveAccount(logic);
        if (!accountsIT.isEmpty()) {
            for (Account account : accountsIT) {
                if (account.getUsername().equals(usernameInput) && account.getPassword().equals(passwordInput)) {
                    logger.info(account.getUsername() + " " + account.getPassword());
                    logic.logUserIn();
                    return MESSAGE_SUCCESS + ": IT staff login";
                }
            }
        }
        if (this.username.equals(usernameInput) && this.password.equals(passwordInput)) {
            //change logicManager isLoggedIn to true
            logic.logUserIn();
            return MESSAGE_SUCCESS;
        } else {
            //return error message on ui
            throw new AuthenticateException(MESSAGE_FAILURE);
        }
    }

    /**
     * Retrieves account list from logic
     */
    public ArrayList<Account> retrieveAccount(Logic logic) {
        logger.info("Retrieving accounts from the logic");
        ArrayList<Account> accounts = logic.getAccountList();
        logger.info(accounts.get(0).getUsername() + " " + accounts.get(0).getPassword());
        return logic.getAccountList();
    }
}
