package seedu.address.logic;

import static seedu.address.logic.commands.CreateUserCommand.MESSAGE_DUPLICATE_USER;
import static seedu.address.logic.commands.CreateUserCommand.MESSAGE_SUCCESS;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CreateUserException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccountBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private Model model;
    private Storage storage;
    private AddressBookParser addressBookParser;

    private String isLoggedIn = "false";

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    public LogicManager() {

    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        if (isLoggedIn.equals("false") && !commandText.equals("login")) {
            throw new CommandException("Please Login First.");
        }

        if (!isLoggedIn.equals("false") && commandText.equals("logout")) {
            isLoggedIn = "false";
        }

        CommandResult commandResult;
        Command command;
        if (isLoggedIn.equals("Admin")) {
            command = addressBookParser.parseCommand(commandText);
        } else {
            command = addressBookParser.parseCommandIT(commandText);
        }
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void logUserIn(String accountType) {
        isLoggedIn = accountType;
    }

    //================== Accounts ================
    @Override
    public ReadOnlyAccountBook getAccountBook() {
        return model.getAccountBook();
    }

    @Override
    public ArrayList<Account> getFilteredAccountList() {
        return model.getFilteredAccountList();
    }

    @Override
    public Path getAccountBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public String addNewUser(Account toAdd) throws CreateUserException, IOException {
        logger.info("logic manager attempting to add new user");
        if (model.hasAccount(toAdd)) {
            throw new CreateUserException(MESSAGE_DUPLICATE_USER);
        } else {
            model.addAccount(toAdd);
            storage.saveAccountBook(model.getAccountBook());
            return MESSAGE_SUCCESS;
        }
    }

    @Override
    public ArrayList<Account> getAccountList() {
        return model.getAccountBook().getAccountList();
    }
}
