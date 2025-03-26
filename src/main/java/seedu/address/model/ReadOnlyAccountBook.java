package seedu.address.model;

import java.util.ArrayList;
import seedu.address.model.Account;

/**
 * Unmodifiable view of the account book
 */
public interface ReadOnlyAccountBook {
    /**
     * Returns an unmodifiable view of the accounts list.
     * This list will not contain any duplicate accounts.
     */
    ArrayList<Account> getAccountList();

}
