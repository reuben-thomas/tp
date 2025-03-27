package seedu.address.ui;

import static seedu.address.logic.parser.AddressBookParser.BASIC_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_PREFIXED;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_SINGLE_ARG;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_STANDALONE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FilterStatusCommand;
import seedu.address.logic.commands.FindByCommand;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.FilterStatusCommandParser;
import seedu.address.logic.parser.FindByCommandParser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.SetStatusCommandParser;

/**
 * A class to support syntax highlighting for commands.
 */
public class CommandSyntaxHighlighter {

    private static final String[] COMMAND_WORDS_ALL = Stream.of(COMMAND_WORDS_STANDALONE, COMMAND_WORDS_SINGLE_ARG,
            COMMAND_WORDS_PREFIXED).flatMap(Arrays::stream).toArray(String[]::new);

    /**
     * Computes the syntax highlighting for an input text.
     *
     * @param inputText The full input text to apply syntax highlighting.
     * @return StyleSpans of a set of style names to apply to the input text.
     */
    public static StyleSpans<Collection<String>> computeSyntaxHighlighting(String inputText) {

        if (inputText.isEmpty()) {
            StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
            builder.add(Collections.emptySet(), 0);
            return builder.create();
        }

        // Handle a valid command partially typed
        if (isValidPartialCommand(inputText)) {
            return highlightText(inputText, StyleClass.COMMAND).create();
        }

        // Remove newline character, as it intercepts capturing the Enter key press
        String sanitizedText = inputText.replace("\n", " ").trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(sanitizedText);

        // Handle invalid command format
        if (!matcher.matches()) {
            return highlightText(inputText, StyleClass.TEXT_INVALID).create();
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (isSingleArgCommand(commandWord)) {
            return handleSingleArgCommand(commandWord, inputText).create();
        } else if (isStandaloneCommand(commandWord)) {
            return handleStandaloneCommand(commandWord, inputText).create();
        } else if (isPrefixedCommand(commandWord)) {
            return handlePrefixedCommand(commandWord, arguments).create();
        } else {
            return handleUnrecognizedCommand(commandWord, inputText).create();
        }
    }

    /**
     * Gets a style span for a submitted command that is either invalid or has failed.
     *
     * @param inputText The input text to apply syntax highlighting.
     * @return StyleSpans of a set of style names to apply to the input text.
     */
    public static StyleSpans<Collection<String>> getFailureStyleSpan(String inputText) {
        return highlightText(inputText, StyleClass.TEXT_INVALID).create();
    }

    /**
     * Computes the syntax highlighting for an unrecognized command.
     *
     * @param commandWord The command word.
     * @param arguments   The arguments to the command.
     * @return A StyleSpansBuilder for the entire input text.
     */
    private static StyleSpansBuilder<Collection<String>> handlePrefixedCommand(String commandWord, String arguments) {
        String inputText = commandWord + arguments;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(StyleClass.COMMAND.getStyleClass()), commandWord.length());

        Prefix[] prefixes = getPrefixesForCommand(commandWord);

        String remainingText = arguments;
        int idx = commandWord.length();

        while (!remainingText.isEmpty()) {
            // Find next prefix occurence and position
            int nextPrefixIdx = remainingText.length();
            Prefix nextPrefix = null;
            for (Prefix prefix : prefixes) {
                int index = remainingText.indexOf(prefix.getPrefix());
                if (index != -1 && index < nextPrefixIdx) {
                    nextPrefixIdx = index;
                    nextPrefix = prefix;
                }
            }

            if (nextPrefix != null) {
                // Highlight arguments till prefix
                if (nextPrefixIdx > 0) {
                    spansBuilder.add(Collections.singleton(StyleClass.ARGS.getStyleClass()), nextPrefixIdx);
                    idx += nextPrefixIdx;
                }
                // Highlight prefix
                String prefixStr = nextPrefix.getPrefix();
                spansBuilder.add(Collections.singleton(StyleClass.PREFIX.getStyleClass()), prefixStr.length());
                idx += prefixStr.length();

                remainingText = inputText.substring(idx);
            } else {
                // Highlight remainder as arguments
                spansBuilder.add(Collections.singleton(StyleClass.ARGS.getStyleClass()), remainingText.length());
                break;
            }
        }

        return spansBuilder;
    }

    /**
     * Computes the syntax highlighting for an unrecognized command.
     *
     * @param commandWord The command word.
     * @param inputText   The entire input text including the command word.
     * @return A StyleSpansBuilder for the entire input text.
     */
    private static StyleSpansBuilder<Collection<String>> handleUnrecognizedCommand(String commandWord,
                                                                                   String inputText) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(StyleClass.COMMAND_INVALID.getStyleClass()), commandWord.length());
        spansBuilder.add(Collections.singleton(StyleClass.ARGS.getStyleClass()),
                inputText.length() - commandWord.length());
        return spansBuilder;
    }

    /**
     * Computes the syntax highlighting for a command that is standalone, meaning without prefixes nor arguments.
     *
     * @param commandWord The command word.
     * @param inputText   The entire input text including the command word.
     * @return A StyleSpansBuilder for the entire input text.
     */
    private static StyleSpansBuilder<Collection<String>> handleStandaloneCommand(String commandWord, String inputText) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(StyleClass.COMMAND.getStyleClass()), commandWord.length());
        spansBuilder.add(Collections.singleton(StyleClass.ARGS_INVALID.getStyleClass()),
                inputText.length() - commandWord.length());
        return spansBuilder;
    }

    /**
     * Computes the syntax highlighting for a command with a single argument.
     *
     * @param commandWord The command word.
     * @param inputText   The entire input text including the command word and arguments.
     * @returns A StyleSpansBuilder for the entire input text.
     */
    private static StyleSpansBuilder<Collection<String>> handleSingleArgCommand(String commandWord, String inputText) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(StyleClass.COMMAND.getStyleClass()), commandWord.length());
        spansBuilder.add(Collections.singleton(StyleClass.ARGS.getStyleClass()),
                inputText.length() - commandWord.length());
        return spansBuilder;
    }

    /**
     * Get an array of prefixes associated with the given command word.
     *
     * @param commandWord The command word to get prefixes for.
     * @return An array of prefixes associated with the command word.
     */
    private static Prefix[] getPrefixesForCommand(String commandWord) {
        Prefix[] prefixes = new Prefix[0];
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            prefixes = AddCommandParser.getPrefixes();
            break;
        case EditCommand.COMMAND_WORD:
            prefixes = EditCommandParser.getPrefixes();
            break;
        case FindByCommand.COMMAND_WORD:
            prefixes = FindByCommandParser.getPrefixes();
            break;
        case SetStatusCommand.COMMAND_WORD:
            prefixes = SetStatusCommandParser.getPrefixes();
            break;
        case FilterStatusCommand.COMMAND_WORD:
            prefixes = FilterStatusCommandParser.getPrefixes();
            break;
        default:
            assert false : String.format("Command word %s has no associated prefixes for syntax highlighting",
                    commandWord);
        }
        return prefixes;
    }

    /**
     * Gets a StyleSpansBuilder that will highlight the entirety of the given text with the given style class.
     *
     * @param text       The text to highlight.
     * @param styleClass The style class to apply to the text.
     * @return A StyleSpansBuilder for the entire text.
     */
    private static StyleSpansBuilder<Collection<String>> highlightText(String text, StyleClass styleClass) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(styleClass.getStyleClass()), text.length());
        return spansBuilder;
    }

    /**
     * Checks if the given text is a valid partial command.
     *
     * @param commandWord The text to check.
     * @return True if the text is a valid partial command, false otherwise.
     */
    private static boolean isValidPartialCommand(String commandWord) {
        return Arrays.stream(COMMAND_WORDS_ALL).anyMatch(command -> command.startsWith(commandWord));
    }

    /**
     * Checks if the given text is a valid command word
     *
     * @param commandWord The command word to check.
     * @return True if the command word is valid, false otherwise.
     */
    private static boolean isValidCommand(String commandWord) {
        return Arrays.stream(COMMAND_WORDS_ALL).anyMatch(command -> command.equals(commandWord));
    }

    /**
     * Checks if the given command word is associated with a standalone command without prefixes nor arguments.
     *
     * @param commandWord The command word to check.
     * @return True if the command word is a standalone command, false otherwise.
     */
    private static boolean isStandaloneCommand(String commandWord) {
        return Arrays.stream(COMMAND_WORDS_STANDALONE).anyMatch(commandWord::equals);
    }

    /**
     * Checks if the given command word is associated with a command only accepting a single subsequent argument
     * without prefixes.
     *
     * @param commandWord The command word to check.
     * @return True if the command word is a single argument command, false otherwise.
     */
    private static boolean isSingleArgCommand(String commandWord) {
        return Arrays.stream(COMMAND_WORDS_SINGLE_ARG).anyMatch(commandWord::equals);
    }

    /**
     * Checks if the given command word is associated with a command that requires prefixed arguments.
     *
     * @param commandWord The command word to check.
     * @return True if the command word is a prefixed command, false otherwise.
     */
    private static boolean isPrefixedCommand(String commandWord) {
        return Arrays.stream(COMMAND_WORDS_PREFIXED).anyMatch(commandWord::equals);
    }

    /**
     * The style class to apply to the text.
     */
    private enum StyleClass {
        // Command words
        COMMAND("command-text"),
        COMMAND_INVALID("command-text-invalid"),
        // Prefixes
        PREFIX("prefix-text"),
        PREFIX_INVALID("prefix-text-invalid"),
        // Arguments that are either associated with a prefix, or the command directly
        ARGS("args-text"),
        ARGS_INVALID("args-text-invalid"),
        // Other text
        TEXT("text"),
        TEXT_INVALID("text-invalid");

        private final String styleClass;

        StyleClass(String styleClass) {
            this.styleClass = styleClass;
        }

        public String getStyleClass() {
            return styleClass;
        }
    }
}
