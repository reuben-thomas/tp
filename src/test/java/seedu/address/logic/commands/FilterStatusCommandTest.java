package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Status;

public class FilterStatusCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_validStatus_filtersCorrectly() {
        Status targetStatus = Status.SERVICING;
        FilterStatusCommand command = new FilterStatusCommand(targetStatus);

        expectedModel.updateFilteredPersonList(person -> person.getStatus().equals(targetStatus));

        CommandResult result = command.execute(model);

        assertEquals("Filtered persons with status: servicing", result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        Status statusA = Status.SERVICING;
        Status statusB = Status.ON_HOLD;

        FilterStatusCommand commandA = new FilterStatusCommand(statusA);
        FilterStatusCommand commandACopy = new FilterStatusCommand(statusA);
        FilterStatusCommand commandB = new FilterStatusCommand(statusB);

        // same object -> returns true
        assertEquals(commandA, commandA);

        // same values -> returns true
        assertEquals(commandA, commandACopy);

        // different status -> returns false
        assert !commandA.equals(commandB);

        // null -> returns false
        assert !commandA.equals(null);

        // different type -> returns false
        assert !commandA.equals("Some String");
    }
}
