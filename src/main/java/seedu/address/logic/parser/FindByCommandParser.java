package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

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

    /**
     * Parses the given {@code String} of arguments in the context of the FindByCommand
     * and returns a FindByCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindByCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, FindByCommand.FINDBY_PREFIXES);
        argMultimap.verifyNoDuplicatePrefixesFor(FindByCommand.FINDBY_PREFIXES);


        if (!areAnyPrefixesPresent(argMultimap, FindByCommand.FINDBY_PREFIXES)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByCommand.MESSAGE_USAGE));
        }

        return new FindByCommand(new AttributeContainsKeywordsPredicate(argMultimap));
    }

}
