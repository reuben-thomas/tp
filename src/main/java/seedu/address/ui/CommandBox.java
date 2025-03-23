package seedu.address.ui;

import static seedu.address.logic.parser.AddressBookParser.BASIC_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_PREFIXED;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_SINGLE_ARG;
import static seedu.address.logic.parser.AddressBookParser.COMMAND_WORDS_STANDALONE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FilterStatusCommand;
import seedu.address.logic.commands.FindByCommand;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.FilterStatusCommandParser;
import seedu.address.logic.parser.FindByCommandParser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.SetStatusCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextFieldOld;

    @FXML
    private CodeArea commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // Listen and consume only the enter key, other keys are handled by the CodeArea
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleCommandEntered();
                event.consume();
            }
        });

        // Compute syntax highlighting when command text changed
        commandTextField.textProperty().addListener((observable, oldValue, newValue) ->
                commandTextField.setStyleSpans(0, computeSyntaxHighlighting(commandTextField.getText())));
    }

    /**
     * Computes the syntax highlighting for the given text.
     */
    private static StyleSpans<Collection<String>> getInvalidCommandStyleSpan(String text) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton("text-invalid"), text.length())
                .create();
        return spansBuilder.create();
    }


    /**
     * Computes the syntax highlighting for the given text.
     */
    private static StyleSpans<Collection<String>> computeSyntaxHighlighting(String text) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        // Command partially typed
        if (Arrays.stream(COMMAND_WORDS_STANDALONE).anyMatch(cmd -> cmd.startsWith(text))
                || Arrays.stream(COMMAND_WORDS_SINGLE_ARG).anyMatch(cmd -> cmd.startsWith(text))
                || Arrays.stream(COMMAND_WORDS_PREFIXED).anyMatch(cmd -> cmd.startsWith(text))
        ) {
            spansBuilder.add(Collections.singleton("command-text"), text.length());
            return spansBuilder.create();
        }

        // Invalid command format
        // Remove newline character, as it intercepts capturing the Enter key press
        String sanitizedText = text.replace("\n", " ").trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(sanitizedText);
        if (!matcher.matches()) {
            spansBuilder.add(Collections.singleton("text-invalid"), text.length())
                    .create();
            return spansBuilder.create();
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Standalone commands: highlight all remaining text as invalid
        if (Arrays.stream(COMMAND_WORDS_STANDALONE).anyMatch(commandWord::equals)) {
            spansBuilder.add(Collections.singleton("command-text"), commandWord.length());
            spansBuilder.add(Collections.singleton("args-text-invalid"), text.length() - commandWord.length());
            return spansBuilder.create();
        }

        // Single arg commands: highlight all remaining text as args
        if (Arrays.stream(COMMAND_WORDS_SINGLE_ARG).anyMatch(commandWord::equals)) {
            spansBuilder.add(Collections.singleton("command-text"), commandWord.length());
            spansBuilder.add(Collections.singleton("args-text"), text.length() - commandWord.length());
            return spansBuilder.create();
        }

        // Prefixed args commands: highlight command word and args separately
        if (Arrays.stream(COMMAND_WORDS_PREFIXED).anyMatch(commandWord::equals)) {
            spansBuilder.add(Collections.singleton("command-text"), commandWord.length());

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
                        spansBuilder.add(Collections.singleton("args-text"), nextPrefixIdx);
                        idx += nextPrefixIdx;
                    }
                    // Highlight prefix
                    String prefixStr = nextPrefix.getPrefix();
                    spansBuilder.add(Collections.singleton("prefix-text"), prefixStr.length());
                    idx += prefixStr.length();

                    remainingText = text.substring(idx);
                } else {
                    // Highlight remainder as arguments
                    spansBuilder.add(Collections.singleton("args-text"), remainingText.length());
                    break;
                }
            }
        }

        // Invalid command word
        spansBuilder.add(Collections.singleton("command-text-invalid"), commandWord.length());
        spansBuilder.add(Collections.singleton("args-text"), text.length() - commandWord.length());
        return spansBuilder.create();
    }

    /**
     * Handles the Enter button pressed event.
     */
    private void handleCommandEntered() {
        String commandText = commandTextField.getText().trim();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.clear();
        } catch (CommandException | ParseException e) {
            return;
        }
    }


    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}

