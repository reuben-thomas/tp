package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Status;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

public class FilterStatusCommandParser implements Parser<FilterStatusCommand> {
    public FilterStatusCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STATUS);

        if (!argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterStatusCommand.MESSAGE_USAGE));
        }

        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        return new FilterStatusCommand(status);
    }
}
