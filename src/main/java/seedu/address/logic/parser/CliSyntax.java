package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_STATUS = new Prefix("st/");
    public static final Prefix PREFIX_ORGID = new Prefix("i/");
    public static final Prefix PREFIX_DEVICEINFO = new Prefix("d/");

    /* Prefixes that can only be used once within a command unambiguously */
    public static final Prefix[] UNIQUE_PREFIXES = {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS,
        PREFIX_STATUS,
        PREFIX_ORGID,
        PREFIX_DEVICEINFO
    };

    /* ALl recognized prefixes */
    public static final Prefix[] PREFIXES = {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS,
        PREFIX_TAG,
        PREFIX_STATUS,
        PREFIX_ORGID,
        PREFIX_DEVICEINFO
    };
}
