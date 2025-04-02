package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AccountBook;
import seedu.address.model.ReadOnlyAccountBook;

/**
 * Represents a storage for {@link seedu.address.model.AccountBook}.
 */
public interface AccountBookStorage {

    /**
     * Returns the file path of the account book data file.
     */
    Path getAccountBookFilePath();

    /**
     * Returns AddressBook data as a {@link AccountBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<AccountBook> readAccountBook() throws DataLoadingException;

    Optional<AccountBook> readAccountBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link AccountBook} to the storage.
     *
     * @param accountBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountBook(ReadOnlyAccountBook accountBook) throws IOException;

    void saveAccountBook(ReadOnlyAccountBook accountBook, Path filePath) throws IOException;

}
