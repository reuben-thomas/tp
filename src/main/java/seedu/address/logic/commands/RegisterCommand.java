package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Create a register dialog for administrator to add IT staff accounts.
 */
public class RegisterCommand extends Command {
    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_SUCCESS = "Launched Register Dialog";
    public static final String MESSAGE_FAILURE = "You already have an account.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getAccountBook().getAccountList().size() == 0) {
            return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
        } else {
            return new CommandResult(MESSAGE_FAILURE, false, false, false, false);
        }
    }
}
