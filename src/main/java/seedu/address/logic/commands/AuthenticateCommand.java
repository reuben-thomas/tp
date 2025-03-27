package seedu.address.logic.commands;

import static seedu.address.commons.util.HasherUtil.hashPassword;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.AuthenticateException;

/**
 * Authenticates the user
 */
public class AuthenticateCommand {
    public static final String MESSAGE_SUCCESS_ADMIN = "Authenticated User: Admin Logged In.";
    public static final String MESSAGE_SUCCESS_IT = "Authenticated User: IT Staff Logged In.";
    public static final String MESSAGE_FAILURE = "Login Failed. Invalid username or password.";

    private static final String username = "Admin";
    private static final String password = "0cuBNQPBLulTdrCSw2kNe2fvE0lTxHDYv73p07Zy9nc=";
    private static final String salt = "CS2103T";

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
        if (username.equals(usernameInput)
                && password.equals(hashPassword(passwordInput, salt))) {
            //change logicManager isLoggedIn to true
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
