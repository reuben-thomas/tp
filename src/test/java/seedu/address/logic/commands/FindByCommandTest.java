package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.FindByCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindByCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        try {
            FindByCommand findFirstCommand = new FindByCommandParser().parse(" n/first");
            FindByCommand findSecondCommand = new FindByCommandParser().parse(" n/second");

            // same object -> returns true
            findFirstCommand.equals(findFirstCommand);

            // same values -> returns true
            FindByCommand findFirstCommandCopy = new FindByCommandParser().parse(" n/first");
            findFirstCommand.equals(findFirstCommandCopy);

            // different types -> returns false
            findFirstCommand.equals(1);

            // null -> returns false
            findFirstCommand.equals(null);

            // different person -> returns false
            findFirstCommand.equals(findSecondCommand);

        } catch (ParseException e) {
            fail("ParseException was thrown: " + e.getMessage());
        }
    }
}
