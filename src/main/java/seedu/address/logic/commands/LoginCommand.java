package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Create a login dialog for users to login.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_SUCCESS = "Launched Login Dialog";
    public static final String MESSAGE_FAILURE = "Please register an account using the 'register' command";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getAccountBook().getAccountList().size() == 0) {
            return new CommandResult(MESSAGE_FAILURE, false, false, false);
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
