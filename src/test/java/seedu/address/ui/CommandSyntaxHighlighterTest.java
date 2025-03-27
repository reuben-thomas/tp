package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_PREFIXED;

import java.util.Collection;
import java.util.Collections;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.Test;

public class CommandSyntaxHighlighterTest {

    @Test
    public void computeSyntaxHighlighting_emptyInput() {
        StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
        builder.add(Collections.emptySet(), 0);
        StyleSpans<Collection<String>> emptyStyleSpan = builder.create();

        assertEquals(
                CommandSyntaxHighlighter.computeSyntaxHighlighting(""),
                emptyStyleSpan,
                "An empty input should return an empty StyleSpan to avoid a RuntimeException");
    }

    @Test
    public void computeSyntaxHighlighting_allPrefixedCommandWords() {
        for (String commandWord : COMMAND_WORDS_PREFIXED) {
            StyleSpans<Collection<String>> syntaxHighlights =
                    CommandSyntaxHighlighter.computeSyntaxHighlighting(commandWord);

            assertNotNull(syntaxHighlights, "Syntax highlighting should not be null");

            assertEquals(
                    syntaxHighlights.stream().mapToInt(s -> s.getLength()).sum(),
                    commandWord.length(),
                    "The syntax highlighting should span the entire length of the command word.");
        }
    }
}
