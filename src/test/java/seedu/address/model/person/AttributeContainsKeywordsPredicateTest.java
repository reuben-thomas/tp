package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEVICEINFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.PersonBuilder;

public class AttributeContainsKeywordsPredicateTest {

    private static final String DEFAULT_TAG_A = "Devops";
    private static final String DEFAULT_TAG_B = "Product";
    private final PersonBuilder defaultPerson = new PersonBuilder()
            .withTags(DEFAULT_TAG_A, DEFAULT_TAG_B);

    @Test
    public void equals() {
        ArgumentMultimap firstMap = new ArgumentMultimap();
        firstMap.put(PREFIX_NAME, "first");

        ArgumentMultimap secondMap = new ArgumentMultimap();
        secondMap.put(PREFIX_NAME, "first");
        secondMap.put(PREFIX_NAME, "second");

        AttributeContainsKeywordsPredicate firstPredicate = new AttributeContainsKeywordsPredicate(firstMap);
        AttributeContainsKeywordsPredicate secondPredicate = new AttributeContainsKeywordsPredicate(secondMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AttributeContainsKeywordsPredicate firstPredicateCopy = new AttributeContainsKeywordsPredicate(firstMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different maps -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emptyAttributes_returnsFalse() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        AttributeContainsKeywordsPredicate predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
    }

    @Test
    public void test_attributeContainsKeywords_returnsTrue() {
        // Exact match
        assertAttributeContainsKeywordsTrue(PREFIX_NAME, PersonBuilder.DEFAULT_NAME, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_EMAIL, PersonBuilder.DEFAULT_EMAIL, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_PHONE, PersonBuilder.DEFAULT_PHONE, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_ADDRESS, PersonBuilder.DEFAULT_ADDRESS, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_STATUS, PersonBuilder.DEFAULT_STATUS, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_ORGID, PersonBuilder.DEFAULT_ORG_ID, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_DEVICEINFO, PersonBuilder.DEFAULT_DEVICE_INFO, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_TAG, DEFAULT_TAG_A, p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_TAG, DEFAULT_TAG_B, p -> p);

        // Partial match
        assertAttributeContainsKeywordsTrue(PREFIX_NAME, "Amy", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_EMAIL, "amy@", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_PHONE, "55255", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_ADDRESS, "Jurong We", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_STATUS, "no", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_ORGID, "055", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_DEVICEINFO, "vice1", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_TAG, DEFAULT_TAG_A.substring(3), p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_TAG, DEFAULT_TAG_B.substring(2), p -> p);

        // Multiple words must match
        assertAttributeContainsKeywordsTrue(PREFIX_NAME, "Amy B", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_ADDRESS, "West Ave 6", p -> p);

        // Mixed case
        assertAttributeContainsKeywordsTrue(PREFIX_NAME, "aMy", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_EMAIL, "aMy@", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_PHONE, "55255", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_ADDRESS, "jurong", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_STATUS, "No", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_ORGID, "055", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_DEVICEINFO, "vice1", p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_TAG, DEFAULT_TAG_A.substring(3).toUpperCase(), p -> p);
        assertAttributeContainsKeywordsTrue(PREFIX_TAG, DEFAULT_TAG_B.substring(2).toUpperCase(), p -> p);
    }

    @Test
    public void test_attributeDoesNotContainKeywords_returnsFalse() {
        assertAttributeContainsKeywordsFalse(PREFIX_NAME, "Charlie");
        assertAttributeContainsKeywordsFalse(PREFIX_EMAIL, "hotmail.com");
        assertAttributeContainsKeywordsFalse(PREFIX_PHONE, "98765432");
        assertAttributeContainsKeywordsFalse(PREFIX_ADDRESS, "Avenue");
        assertAttributeContainsKeywordsFalse(PREFIX_STATUS, "INACTIVE");
        assertAttributeContainsKeywordsFalse(PREFIX_ORGID, "XYZ789");
        assertAttributeContainsKeywordsFalse(PREFIX_DEVICEINFO, "Windows");
        assertAttributeContainsKeywordsFalse(PREFIX_TAG, "Family");
    }

    @Test
    public void test_multipleAttributes_returnsTrue() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        AttributeContainsKeywordsPredicate predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);

        assertFalse(predicate.test(defaultPerson.build()),
                "Should return false with empty multimap");

        // Progressively add attributes, where the person matches all attributes
        argumentMultimap.put(PREFIX_NAME, "Amy");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_EMAIL, "mail.com");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_PHONE, "255");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_ADDRESS, "Jurong");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_STATUS, "none");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_ORGID, "000");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_DEVICEINFO, "vice");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_TAG, DEFAULT_TAG_A);
        argumentMultimap.put(PREFIX_TAG, DEFAULT_TAG_B);
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));

        argumentMultimap = new ArgumentMultimap();
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);

        // Progressively modify one atttribute at a time, where the person only matches some attributes
        argumentMultimap.put(PREFIX_NAME, "Amy");
        argumentMultimap.put(PREFIX_EMAIL, "invalid mail");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_PHONE, "255 2012");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_ADDRESS, "Jurong Somewhere Else");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_STATUS, "invalid status");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_ORGID, "00120312093900");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_DEVICEINFO, "invalid devices");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_TAG, "invalid_tag");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertTrue(predicate.test(defaultPerson.build()));
    }

    @Test
    public void test_multipleAttributes_returnsFalse() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        AttributeContainsKeywordsPredicate predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));

        // Progressively add attributes, where the person matches none of the attributes
        argumentMultimap.put(PREFIX_NAME, "Xavier");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_EMAIL, "example.org");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_PHONE, "99999999");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_ADDRESS, "Tampines");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_STATUS, "pending_external");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_ORGID, "120391");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_DEVICEINFO, "Dell XPS 17");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
        argumentMultimap.put(PREFIX_TAG, "CEO");
        argumentMultimap.put(PREFIX_TAG, "CTO");
        predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        assertFalse(predicate.test(defaultPerson.build()));
    }

    @Test
    public void toStringMethod() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_NAME, "keyword1");
        argumentMultimap.put(PREFIX_NAME, "keyword2");

        AttributeContainsKeywordsPredicate predicate = new AttributeContainsKeywordsPredicate(argumentMultimap);
        String expected = AttributeContainsKeywordsPredicate.class.getCanonicalName()
                + "{attributeKeywordMap=" + argumentMultimap + "}";
        assertEquals(expected, predicate.toString());
    }

    private void assertAttributeContainsKeywordsTrue(Prefix prefix, String keyword,
                                                     Function<PersonBuilder, PersonBuilder> personModifier) {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(prefix, keyword);
        AttributeContainsKeywordsPredicate predicate = new AttributeContainsKeywordsPredicate(map);

        Person person = personModifier.apply(defaultPerson).build();
        assertTrue(predicate.test(person),
                String.format("Predicate return true for %s and keyword '%s'",
                        prefix, keyword));
    }

    private void assertAttributeContainsKeywordsFalse(Prefix prefix, String keyword) {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(prefix, keyword);
        AttributeContainsKeywordsPredicate predicate = new AttributeContainsKeywordsPredicate(map);

        assertFalse(predicate.test(defaultPerson.build()),
                String.format("Predicate should return false for prefix %s and keyword '%s'",
                        prefix, keyword));
    }
}
