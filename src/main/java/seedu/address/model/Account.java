package seedu.address.model;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Account in the account book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Account {
    private final String username;
    private final String password;

    /**
     * Every field must be present and not null.
     *
     * @param username
     * @param password
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Accessor for account username
     *
     * @return this accounts username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Accessor for account password
     *
     * @return this accounts password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Function to determine duplicate accounts
     *
     * @param otherAccount account be checked against
     * @return boolean flag, true if same account
     */
    public boolean isSameAccount(Account otherAccount) {
        if (otherAccount == this) {
            return true;
        }

        return otherAccount != null
                && otherAccount.getUsername().equals(getUsername());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("username", username)
                .add("password", password)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        Account otherAccount = (Account) other;
        return otherAccount.username.equals(username)
                && otherAccount.password.equals(password);
    }

}
