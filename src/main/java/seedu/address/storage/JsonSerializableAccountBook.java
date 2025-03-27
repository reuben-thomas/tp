package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;
import seedu.address.model.AccountBook;
import seedu.address.model.ReadOnlyAccountBook;

/**
 * An account book that is serializable to json format.
 */
@JsonRootName(value="accounts")
public class JsonSerializableAccountBook {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";

    private final List<JsonAccount> accounts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAccountBook} with the given accounts.
     *
     * @param accounts
     */
    @JsonCreator
    public JsonSerializableAccountBook(@JsonProperty("accounts") List<JsonAccount> accounts) {
        this.accounts.addAll(accounts);
    }

    /**
     * Converts a given {@code ReadOnlyAccountBook} into this class for json use.
     *
     * @param source
     */
    public JsonSerializableAccountBook(ReadOnlyAccountBook source) {
        this.accounts.addAll(source.getAccountList().stream().map(JsonAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts this account book into the model's {@code AccountBook} object.
     *
     * @return
     * @throws IllegalValueException
     */
    public AccountBook toModelType() throws IllegalValueException {
        AccountBook accountBook = new AccountBook();
        for (JsonAccount jsonAccount : accounts) {
            Account account = jsonAccount.toModelType();
            if (accountBook.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            accountBook.addAccount(account);
        }
        return accountBook;
    }
}
