package seedu.address.logic.commands;


import seedu.address.model.Model;
import seedu.address.model.tag.Status;

import static java.util.Objects.requireNonNull;

public class FilterStatusCommand extends Command {
    public static final String COMMAND_WORD = "filter_status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters persons by status.\n"
            + "Parameters: STATUS (must be one of: none, pending_approval, servicing, pending_external, on_hold)\n"
            + "Example: " + COMMAND_WORD + " s/servicing";

    private final Status statusToFilter;

    public FilterStatusCommand(Status statusToFilter) {
        this.statusToFilter = statusToFilter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(person -> person.getStatus().equals(statusToFilter));
        return new CommandResult("Filtered persons with status: " + statusToFilter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterStatusCommand
                && statusToFilter.equals(((FilterStatusCommand) other).statusToFilter));
    }
}
