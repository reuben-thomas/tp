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

@JsonRootName(value="accounts")
public class JsonSerializableAccountBook {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";

    private final List<JsonAccount> accounts = new ArrayList<>();

    @JsonCreator
    public JsonSerializableAccountBook(@JsonProperty("accounts") List<JsonAccount> accounts) {
        this.accounts.addAll(accounts);
    }

    public JsonSerializableAccountBook(ReadOnlyAccountBook source) {
        this.accounts.addAll(source.getAccountList().stream().map(JsonAccount::new).collect(Collectors.toList()));
    }

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
