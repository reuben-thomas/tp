package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIXES;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.AttributeContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book, whereby specific attributes contains any of the corresponding search
 * keywords. Keyword matching is case-insensitive.
 */
public class FindByCommand extends Command {

    public static final String COMMAND_WORD = "findby";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons by specifying attributes that"
            + " should contain a corresponding input (case-insensitive) and displays them as a list with index " +
            "numbers.\n"
            + "Supported prefixes: "
            + Arrays.stream(PREFIXES)
            .map(Prefix::toString)
            .collect(Collectors.joining(", "))
            + "\nParameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " e/ alice";

    private final AttributeContainsKeywordsPredicate predicate;

    public FindByCommand(AttributeContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindByCommand)) {
            return false;
        }

        FindByCommand otherFindCommand = (FindByCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("predicate", predicate).toString();
    }
}
