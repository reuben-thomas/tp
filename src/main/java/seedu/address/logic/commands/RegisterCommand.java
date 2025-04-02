package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Create a register dialog for administrator to add IT staff accounts.
 */
public class RegisterCommand extends Command {
    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_SUCCESS = "Launched Register Dialog";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
    }
}
