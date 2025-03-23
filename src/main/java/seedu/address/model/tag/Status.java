package seedu.address.model.tag;

/**
 * Represents a Status in the address book.
 * Guarantees: immutable; status is valid as declared in {@link #isValidStatusName(String)}
 */
public enum Status {
    NONE,
    PENDING_APPROVAL,
    SERVICING,
    PENDING_EXTERNAL,
    ON_HOLD;

    public static final String MESSAGE_CONSTRAINTS = "Status should be one of: none, pending_approval, servicing"
            + ", pending_external, or on_hold";

    /**
     * Returns true if a given string is a valid status name (case insensitive).
     */
    public static boolean isValidStatusName(String test) {
        if (test == null) {
            return false;
        }
        try {
            fromString(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Converts a string to a corresponding enum value (case insensitive).
     * Throws an exception if the input is invalid.
     */
    public static Status fromString(String statusName) {
        if (statusName == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        try {
            return Status.valueOf(statusName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + name().toLowerCase() + ']';
    }
}
