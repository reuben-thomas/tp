package seedu.address.logic.commands;

import static seedu.address.commons.util.HasherUtil.hashPassword;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.AuthenticateException;

/**
 * Authenticates the user
 */
public class AuthenticateCommand {
    public static final String MESSAGE_SUCCESS = "Authenticated User";
    public static final String MESSAGE_FAILURE = "Login Failed. Invalid username or password.";

    private final String username = "Admin";
    private final String password = "0cuBNQPBLulTdrCSw2kNe2fvE0lTxHDYv73p07Zy9nc=";
    private final String salt = "CS2103T";

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
        this.passwordInput = hashPassword(password, salt);
    }

    /**
     * Attempt to authenticate user
     *
     * @param logic LogicManager for MainWindow
     * @throws AuthenticateException exception for authenticate failure
     */
    public void authenticateUser(Logic logic) throws AuthenticateException {
        if (this.username.equals(usernameInput) && this.password.equals(passwordInput)) {
            //change logicManager isLoggedIn to true
            logic.logUserIn();
        } else {
            //return error message on ui
            throw new AuthenticateException(MESSAGE_FAILURE);
        }
    }
}
