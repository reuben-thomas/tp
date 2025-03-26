package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AccountBook;
import seedu.address.model.ReadOnlyAccountBook;
import seedu.address.model.ReadOnlyAddressBook;

public interface AccountBookStorage {

    Path getAccountBookFilePath();

    Optional<AccountBook> readAccountBook() throws DataLoadingException;

    Optional<AccountBook> readAccountBook(Path filePath) throws DataLoadingException;

    void saveAccountBook(ReadOnlyAccountBook accountBook) throws IOException;

    void saveAccountBook(ReadOnlyAccountBook accountBook, Path filePath) throws IOException;

}
