package seedu.address.logic.commands;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.AuthenticateException;

/**
 * Authenticates the user
 */
public class AuthenticateCommand {
    public static final String MESSAGE_SUCCESS_ADMIN = "Authenticated User: Admin Logged In.";
    public static final String MESSAGE_SUCCESS_IT = "Authenticated User: IT Staff Logged In.";
    public static final String MESSAGE_FAILURE = "Login Failed. Invalid username or password.";

    private final String username = "Admin";
    private final String password = "Admin@123";

    private final String usernameIT = "ITstaff";
    private final String passwordIT = "ITstaff@123";

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
        if (this.username.equals(usernameInput) && this.password.equals(passwordInput)) {
            //change logicManager isLoggedIn to admin
            logic.logUserIn("Admin");
            return MESSAGE_SUCCESS_ADMIN;
        } else if (this.usernameIT.equals(usernameInput) && this.passwordIT.equals(passwordInput)) {
            //change logicManager isLoggedIn to IT staff
            logic.logUserIn("IT");
            return MESSAGE_SUCCESS_IT;
        } else {
            //return error message on ui
            throw new AuthenticateException(MESSAGE_FAILURE);
        }
    }
}
