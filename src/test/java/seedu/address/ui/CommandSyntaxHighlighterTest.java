package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_PREFIXED;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_SINGLE_ARG;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_STANDALONE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

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
    public void computeSyntaxHighlighting_partialCommands() {
        String[] allCommandWords = Stream.of(COMMAND_WORDS_STANDALONE, COMMAND_WORDS_SINGLE_ARG,
                COMMAND_WORDS_PREFIXED).flatMap(Arrays::stream).toArray(String[]::new);

        for (String commandWord : allCommandWords) {
            for (int i = 0; i < commandWord.length(); i++) {
                String substring = commandWord.substring(0, i + 1);

                StyleSpans<Collection<String>> syntaxHighlights =
                        CommandSyntaxHighlighter.computeSyntaxHighlighting(substring);

                assertEquals(
                        syntaxHighlights.stream().mapToInt(s -> s.getLength()).sum(),
                        substring.length(),
                        "The syntax highlighting should span the same length as the partially typed command.");

                assertEquals(String.format("[%s]", CommandSyntaxHighlighter.StyleClass.COMMAND.getStyleClass()),
                        String.format("%s",
                                syntaxHighlights.stream().findFirst().get().getStyle().toString()),
                        String.format("Partially typed command '%s' should be highlighted as a command.", commandWord));
            }
        }
    }

    @Test
    public void computeSyntaxHighlighting_allCommands() {
        String[] allCommandWords = Stream.of(COMMAND_WORDS_STANDALONE, COMMAND_WORDS_SINGLE_ARG,
                COMMAND_WORDS_PREFIXED).flatMap(Arrays::stream).toArray(String[]::new);

        for (String commandWord : allCommandWords) {
            // Adding a sample argument prevents the inputText from being parsed as a partially typed command.
            String inputText = commandWord + " sample argument";

            StyleSpans<Collection<String>> syntaxHighlights =
                    CommandSyntaxHighlighter.computeSyntaxHighlighting(inputText);

            assertEquals(
                    syntaxHighlights.stream().mapToInt(s -> s.getLength()).sum(),
                    inputText.length(),
                    "The syntax highlighting should span the same length as the input text.");
        }
    }
}
