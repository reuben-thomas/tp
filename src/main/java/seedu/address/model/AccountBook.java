package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

/**
 * Wraps all data at the account book level
 * Duplicates are not allowed (by .isSameAccount comparison)
 */
public class AccountBook implements ReadOnlyAccountBook {

    private final UniqueAccountsList accounts;

    public AccountBook() {
        this.accounts = new UniqueAccountsList();
    }

    /**
     * Replaces the contents of the account list with {@code accounts}.
     * {@code accounts} must not contain duplicate accounts.
     */
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }

    //// account-level operations

    /**
     * Returns true if a account with the same identity as {@code account} exists in the account book.
     */
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return accounts.contains(account);
    }

    /**
     * Adds a account to the Account book.
     * The account must not already exist in the account book.
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void resetAccounts(AccountBook newAccountBook) {
        requireNonNull(newAccountBook);
        setAccounts(newAccountBook.getAccountList());
    }

    public ArrayList<Account> getAccountList() {
        return accounts.asUnmodifiableArrayList();
    }

}
