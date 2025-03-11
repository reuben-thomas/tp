package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's deviceInfo in the system.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidDeviceInfo(String)}
 */
public class DeviceInfo {
    public static final String MESSAGE_CONSTRAINTS = "Device info should only contain "
            + "alphanumeric characters and spaces, "
            + "and it should not be blank or exceed 50 characters.";
    public static final String VALIDATION_REGEX = "[a-zA-Z0-9\\s]+";
    public final String deviceInfo;

    /**
     * Constructs a {@code DeviceInfo}.
     *
     * @param deviceInfo A valid device info.
     */
    public DeviceInfo(String deviceInfo) {
        requireNonNull(deviceInfo);
        checkArgument(isValidDeviceInfo(deviceInfo), MESSAGE_CONSTRAINTS);
        this.deviceInfo = deviceInfo;
    }

    /**
     * Returns true if a given string is a valid device info.
     */
    public static boolean isValidDeviceInfo(String test) {
        return test != null && test.matches(VALIDATION_REGEX) && test.length() <= 50;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof DeviceInfo otherDeviceInfo) {
            return Objects.equals(deviceInfo, otherDeviceInfo.deviceInfo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceInfo);
    }

    @Override
    public String toString() {
        return deviceInfo;
    }
}
