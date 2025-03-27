package seedu.address.logic.commands;

/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.AuthenticateCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.AuthenticateCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;
*/
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
//import seedu.address.logic.commands.exceptions.AuthenticateException;


public class AuthenticateCommandTest {

    private Logic logic = new LogicManager();

    /*
    @Test
    public void execute_validCredentials_success() {
        assertEquals(MESSAGE_SUCCESS, new AuthenticateCommand("Admin", "Admin@123").authenticateUser(logic));
    }

    @Test
    public void invalidUsername_throwsAuthenticationException() {
        String reply;
        try {
            reply = new AuthenticateCommand("Admin22", "Admin@123").authenticateUser(logic);
        } catch (AuthenticateException e) {
            reply = e.getMessage();
        }
        assertEquals(MESSAGE_FAILURE, reply);
    }

    @Test
    public void invalidPassword_throwsAuthenticationException() {
        String reply;
        try {
            reply = new AuthenticateCommand("Admin22", "Admin@1235").authenticateUser(logic);
        } catch (AuthenticateException e) {
            reply = e.getMessage();
        }
        assertEquals(MESSAGE_FAILURE, reply);
    }

    @Test
    public void nullCredentials_throwsAuthenticationException() {
        String reply;
        try {
            reply = new AuthenticateCommand(null, null).authenticateUser(logic);
        } catch (AuthenticateException e) {
            reply = e.getMessage();
        }
        assertEquals(MESSAGE_FAILURE, reply);
    }
     */
}
