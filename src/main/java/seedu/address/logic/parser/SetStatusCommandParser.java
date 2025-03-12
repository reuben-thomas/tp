package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Status;

/**
 * Parses input arguments and creates a new SetStatusCommand object
 */
public class SetStatusCommandParser implements Parser<SetStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetStatusCommand
     * and returns an SetStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetStatusCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STATUS);

        // Ensure the index exists and is valid
        if (argMultimap.getPreamble().isBlank()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStatusCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStatusCommand.MESSAGE_USAGE), pe);
        }

        // Ensure status is present
        if (!argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            throw new ParseException("Status must be provided.");
        }

        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());

        return new SetStatusCommand(index, status);
    }
}
