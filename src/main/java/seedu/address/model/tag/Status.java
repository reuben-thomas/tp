package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Status in the address book.
 * Guarantees: immutable; status is valid as declared in {@link #isValidStatusName(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Status should be one of: none, pending_approval"
            + ", servicing, pending_external, or on_hold";
    public static final String VALIDATION_STATUS = "(?i)^(none|pending_approval|servicing|pending_external|on_hold)$";

    public final String statusName;

    /**
     * Constructs a {@code Status}.
     *
     * @param statusName A valid status.
     */
    public Status(String statusName) {
        if (statusName == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        requireNonNull(statusName);
        checkArgument(isValidStatusName(statusName), MESSAGE_CONSTRAINTS);
        this.statusName = statusName.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid status name.
     */
    public static boolean isValidStatusName(String test) {
        return test.matches(VALIDATION_STATUS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return statusName.equals(otherStatus.statusName);
    }

    @Override
    public int hashCode() {
        return statusName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + statusName + ']';
    }
}
