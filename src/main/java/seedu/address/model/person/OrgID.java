package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Organization's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrgID(String)}
 */
public class OrgID {
    private static final int MAX_DIGITS = 10;

    public static final String MESSAGE_CONSTRAINTS = String.format(
            "OrgIDs should only be unique numeric values, should be less than or equal to %d digits"
                    + ", and it should not be blank.", MAX_DIGITS);

    /*
     * The first character of the OrgID must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\d{1," + MAX_DIGITS + "}";

    public final String value;

    /**
     * Constructs a {@code OrgID}.
     *
     * @param orgID A valid OrgID.
     */
    public OrgID(String orgID) {
        requireNonNull(orgID);
        checkArgument(isValidOrgID(orgID), MESSAGE_CONSTRAINTS);
        value = String.format("%10s", orgID).replace(" ", "0");
    }

    /**
     * Returns true if a given string is a valid OrgID.
     */
    public static boolean isValidOrgID(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrgID otherOrgID)) {
            return false;
        }

        return value.equals(otherOrgID.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
