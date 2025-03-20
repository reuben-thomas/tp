package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DeviceInfo;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.OrgID;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Jonathen"), new Phone("87438807"), new Email("jonathen@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new OrgID("123"), new DeviceInfo("MacBook Pro"),
                    getTagSet("Developer"), new Status("pending_approval")),
            new Person(new Name("Li Fei"), new Phone("99272758"), new Email("lifei@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new OrgID("124"),
                    new DeviceInfo("Dell XPS 13"),
                    getTagSet("Consultant"), new Status("none")),
            new Person(new Name("Ga Jin"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new OrgID("125"),
                    new DeviceInfo("ThinkPad X1 Carbon"),
                    getTagSet("Cybersecurity"), new Status("pending_external")),
            new Person(new Name("Reuben"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new OrgID("126"),
                    new DeviceInfo("Asus Zenbook"),
                    getTagSet("Product"), new Status("on_hold")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
