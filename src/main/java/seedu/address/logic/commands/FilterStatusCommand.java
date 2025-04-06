package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.model.Model;
import seedu.address.model.tag.Status;

/**
 * Filters status of people on the address book.
 */
public class FilterStatusCommand extends Command {
    public static final String COMMAND_WORD = "filter-status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters persons by status.\n"
            + "Parameters: " + PREFIX_STATUS + "STATUS\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_STATUS + "servicing";

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
