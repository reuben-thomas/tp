package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FindByCommandParserTest {
    private FindByCommandParser parser = new FindByCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("     "));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("some preamble n/Alice"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_emptyPrefixValue_throwsParseException() {
        for (Prefix prefix : FindByCommandParser.getPrefixes()) {
            ParseException exception = assertThrows(ParseException.class, () -> parser.parse(prefix.getPrefix() + " "));
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByCommand.MESSAGE_USAGE),
                    exception.getMessage());
        }
    }
}