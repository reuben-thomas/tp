package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Status;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class SetStatusCommand extends Command {
    public static final String COMMAND_WORD = "set_status";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sets the status of the person identified by the index number\n"
            + "Parameters: INDEX (must be a positive integer), status\n"
            + "Example: " + COMMAND_WORD + " 1 s/pending_approval" ;

    public static final String MESSAGE_SUCCESS = "Status successfully added";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already has the status";
    private final Index index;
    private final Status newStatus;

    public SetStatusCommand(Index index, Status newStatus) {
        requireNonNull(index);
        requireNonNull(newStatus);

        this.index = index;
        this.newStatus = newStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, newStatus);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }
    private static Person createEditedPerson(Person personToEdit, Status newStatus) {
        personToEdit.setStatus(newStatus);
        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getStatus()
        );
    }
}


