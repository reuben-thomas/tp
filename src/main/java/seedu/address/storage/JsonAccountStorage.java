package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AccountBook;
import seedu.address.model.ReadOnlyAccountBook;

/**
 * A class to access AccountBook data stored as a json file on the hard disk.
 */
public class JsonAccountStorage implements AccountBookStorage{
    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAccountStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAccountBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<AccountBook> readAccountBook() throws DataLoadingException {
        return readAccountBook(filePath);
    }

    /**
     * Reads the data stored as a json file on the hard disk.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<AccountBook> readAccountBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAccountBook> jsonAccountBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAccountBook.class);
        if (!jsonAccountBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAccountBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAccountBook(ReadOnlyAccountBook accountBook) throws IOException {
        saveAccountBook(accountBook, filePath);
    }

    /**
     * Saves the current account book to the account book json file
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveAccountBook(ReadOnlyAccountBook accountBook, Path filePath) throws IOException {
        requireNonNull(accountBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAccountBook(accountBook), filePath);
    }

}
