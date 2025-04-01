package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.UNIQUE_PREFIXES;

import seedu.address.logic.commands.FilterStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Status;

/**
 * Parses input arguments and creates a new FilterStatusCommand object
 */
public class FilterStatusCommandParser implements Parser<FilterStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterStatusCommand
     * and returns an FilterStatusCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterStatusCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STATUS);

        if (!argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterStatusCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(UNIQUE_PREFIXES);
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        return new FilterStatusCommand(status);
    }

    public static Prefix[] getPrefixes() {
        return new Prefix[]{PREFIX_STATUS};
    }
}
