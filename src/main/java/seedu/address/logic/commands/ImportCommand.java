package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

/**
 * Imports contacts from an external json file.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Import contacts from an external json file. \n (Note that imported file content will replace original "
            + "data stored in addressbook.json\n"
            + "Parameters: Full File path\n"
            + "Example: " + COMMAND_WORD + " /user/jin/downloads/new.json";

    public static final String MESSAGE_IMPORT_SUCCESS = "Imported contacts from %1$s";
    public static final String MESSAGE_IMPORT_FAILURE = "Invalid Command Format or data in JSON file in invalid format\n "
            + "Failed to import contacts from %1$s.\n"
            + MESSAGE_USAGE;

    private final Path filePath;


    public ImportCommand(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Storage newStorage = new StorageManager(model.getAddressBookFilePath(), Config.DEFAULT_CONFIG_FILE);
        return execute(model, newStorage);
        // throw new CommandException("Unable to import without existing storage.");
    }


    private CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);

        if (!FileUtil.isFileExists(filePath) && !FileUtil.isValidPath(filePath.toString())) {
            throw new CommandException(String.format(MESSAGE_IMPORT_FAILURE, filePath));
        }

        try {

            Optional<ReadOnlyAddressBook> addressBookOptional = storage.readAddressBook(filePath);
            if (!addressBookOptional.isPresent()) {
                throw new CommandException(String.format(MESSAGE_IMPORT_FAILURE, filePath.toString()));
            } else {
                model.setAddressBook(addressBookOptional.get());
            }
            return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, filePath.toString()));
        } catch (Exception e) {
            throw new CommandException(String.format(MESSAGE_IMPORT_FAILURE, filePath.toString()), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand otherImportCommand)) {
            return false;
        }

        return filePath.equals(otherImportCommand.filePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filePath", filePath)
                .toString();
    }
}
