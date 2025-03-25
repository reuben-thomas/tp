package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Create a logout dialog for users to logout.
 */
public class LogOutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_SUCCESS = "You are logged out.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
