package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIXES;
import static seedu.address.logic.parser.CliSyntax.UNIQUE_PREFIXES;

import java.util.Collection;
import java.util.stream.Stream;

import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.commands.FindByCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AttributeContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindByCommand object
 */
public class FindByCommandParser implements Parser<FindByCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    public static Prefix[] getPrefixes() {
        return PREFIXES;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindByCommand
     * and returns a FindByCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindByCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIXES);
        argMultimap.verifyNoDuplicatePrefixesFor(UNIQUE_PREFIXES);

        if (!areAnyPrefixesPresent(argMultimap, PREFIXES)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByCommand.MESSAGE_USAGE));
        }

        for (Prefix prefix : PREFIXES) {
            if (argMultimap.getValue(prefix).isPresent() && argMultimap.getValue(prefix).get().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindByCommand.MESSAGE_USAGE));
            }
        }

        validateArgs(argMultimap);

        return new FindByCommand(new AttributeContainsKeywordsPredicate(argMultimap));
    }

    /**
     * Checks if the arguments in the given {@code ArgumentMultimap} are valid.
     *
     * @param argMultimap the ArgumentMultimap to check
     * @throws ParseException if any of the arguments are invalid
     */
    private void validateArgs(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);

        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_EMAIL).isPresent()) {
            ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).isPresent()) {
            ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_STATUS).isPresent()) {
            ParserUtil.parseStatus(argMultimap.getValue(CliSyntax.PREFIX_STATUS).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_ORGID).isPresent()) {
            ParserUtil.parseOrgID(argMultimap.getValue(CliSyntax.PREFIX_ORGID).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_DEVICEINFO).isPresent()) {
            ParserUtil.parseDeviceInfo(argMultimap.getValue(CliSyntax.PREFIX_DEVICEINFO).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_TAG).isPresent()) {
            for (String val : argMultimap.getAllValues(CliSyntax.PREFIX_TAG)) {
                ParserUtil.parseTag(val);
            }
        }
    }
}
